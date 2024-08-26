import com.android.build.gradle.tasks.SourceJarTask
import com.strumenta.antlrkotlin.gradle.AntlrKotlinTask
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.com.strumenta.antlr.kotlin)
    id("maven-publish")
}

val moduleId = "org.phoenixframework.liveview"
android {
    namespace = moduleId

    compileSdk = Constants.compileSdkVersion

    defaultConfig {
        minSdk = Constants.minSdkVersion

        testApplicationId = "$moduleId.test"
        testInstrumentationRunner = Constants.instrumentationRunnerClass

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    // Running screenshot tests
    // Recording the image references
    // ./gradlew recordRoborazziDebug -PisRecordingShotTest=true
    // Testing
    // ./gradlew verifyRoborazziDebug
    // This param must be passed via command line in order to generated the reference images for
    // screen shot tests.
    val isRecordingShotTest: String by project
    buildTypes.forEach {
        it.buildConfigField("boolean", "IS_RECORDING_SHOT_TEST", isRecordingShotTest)
    }

    compileOptions {
        sourceCompatibility = Constants.sourceCompatibilityVersion
        targetCompatibility = Constants.targetCompatibilityVersion
    }
    kotlinOptions {
        jvmTarget = Constants.jvmTargetVersion
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Constants.kotlinCompilerExtVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testNamespace = "$moduleId.test"
    testOptions.unitTests.isIncludeAndroidResources = true

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    sourceSets.getByName("main") {
        kotlin {
            srcDir(layout.buildDirectory.dir("generatedAntlr"))
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // These dependencies are used internally, and not exposed to consumers on their own compile classpath.
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.com.google.code.gson)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Foundation
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.com.google.code.gson)
    implementation(libs.com.github.dsrees.javaphoenixclient)

    // These dependencies are exported to consumers, that is to say found on their compile classpath.
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.navigation.compose)

    // Foundation
    api(libs.com.github.liveview.native.core.jetpack)
    api(libs.net.java.dev.jna) {
        artifact {
            type = "aar"
        }
    }
    //api(project(Constants.moduleStylesheetParser))
    api(libs.org.jetbrains.kotlinx.collections.immutable)
    api(libs.org.jsoup)

    // Stylesheet Parser
    implementation(libs.com.strumenta.antlr.kotlin.runtime)
    implementation(libs.antlr4.runtime)

    // Test dependencies
    testApi(project(Constants.moduleClientTestBase))
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.androidx.compose.ui.test.junit4.android)
    testImplementation(libs.io.github.takahirom.roborazzi)
    testImplementation(libs.io.github.takahirom.roborazzi.compose)
    testImplementation(libs.io.github.takahirom.roborazzi.junit.rule)
    testImplementation(libs.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.org.robolectric)
}

// ./gradlew assembleRelease -PcomposeCompilerReports=true
// Run the command above in order to generate the compose stability diagnose report.
// https://developer.android.com/jetpack/compose/performance/stability/diagnose
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        val prefix = "plugin:androidx.compose.compiler.plugins.kotlin"
        val output = "${project.buildDir.absolutePath}/compose_compiler"
        if (project.findProperty("composeCompilerReports") == "true") {
            freeCompilerArgs += listOf(
                "-P",
                "$prefix:reportsDestination=$output"
            )
        }
        if (project.findProperty("composeCompilerMetrics") == "true") {
            freeCompilerArgs += listOf(
                "-P",
                "$prefix:metricsDestination=$output"
            )
        }
    }
}

// Configuring Java Lib Path in order to find the native library before running the Unit Tests
tasks.withType<Test>().configureEach {
    doFirst {
        copyDesktopJniLibs(rootDir, this@configureEach)
    }
}

val generateKotlinGrammarSource = tasks.register<AntlrKotlinTask>("generateKotlinGrammarSource") {
    dependsOn("cleanGenerateKotlinGrammarSource")

    // ANTLR .g4 files are under {example-project}/antlr
    // Only include *.g4 files. This allows tools (e.g., IDE plugins)
    // to generate temporary files inside the base path
    source = fileTree(layout.projectDirectory.dir("antlr")) {
        include("**/*.g4")
    }

    // We want the generated source files to have this package name
    val pkgName = "org.phoenixframework.liveview.stylesheet"
    packageName = pkgName

    // We want visitors alongside listeners.
    // The Kotlin target language is implicit, as is the file encoding (UTF-8)
    arguments = listOf("-no-visitor", "-no-listener")

    // Generated files are outputted inside build/generatedAntlr/{package-name}
    val outDir = "generatedAntlr/${pkgName.replace(".", "/")}"
    outputDirectory = layout.buildDirectory.dir(outDir).get().asFile
}

tasks.withType<KotlinCompile<*>> {
    dependsOn(generateKotlinGrammarSource)
}

tasks.withType<SourceJarTask> {
    dependsOn(generateKotlinGrammarSource)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = Constants.publishGroupId
            artifactId = Constants.publishArtifactId
            version = Constants.publishVersion

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}