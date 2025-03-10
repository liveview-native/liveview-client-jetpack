import com.android.build.gradle.tasks.SourceJarTask
import com.strumenta.antlrkotlin.gradle.AntlrKotlinTask

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.com.strumenta.antlr.kotlin)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose.compiler)
    id("maven-publish")
}

val moduleId = "org.phoenixframework.liveview"
val antlrGeneratedDir = "generatedAntlr"

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
        isCoreLibraryDesugaringEnabled = true
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
    // Adding ANTLR source dir to the source file dir
    sourceSets.getByName("main") {
        kotlin {
            srcDir(layout.buildDirectory.dir(antlrGeneratedDir))
        }
    }
}

dependencies {
    // These dependencies are used internally, and not exposed to consumers on their own compile classpath.
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.antlr4.runtime)
    implementation(libs.com.google.code.gson)
    implementation(libs.com.google.code.gson)
    implementation(libs.com.github.dsrees.javaphoenixclient)
    implementation(libs.com.github.liveview.native.core.jetpack)
    implementation(libs.com.strumenta.antlr.kotlin.runtime)
    // TODO Is there a better way to include JNA *.so files to the AAR file? (instead of put them in jniLibs dir)
    //   Download the JAR from https://github.com/java-native-access/jna/tree/master/lib/native
    //   Extract the libjnidispatch.so file for each architecture using `jar xf android-aarch64.jar`
    implementation(libs.net.java.dev.jna)
    implementation(libs.org.jsoup)

    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)

    // These dependencies are exported to consumers, that is to say found on their compile classpath.
    api(libs.org.jetbrains.kotlinx.collections.immutable)

    // Test dependencies
    testImplementation(project(Constants.moduleClientTestBase))
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.androidx.compose.ui.test.junit4)
    testImplementation(libs.io.github.takahirom.roborazzi)
    testImplementation(libs.io.github.takahirom.roborazzi.compose)
    testImplementation(libs.io.github.takahirom.roborazzi.junit.rule)
    testImplementation(libs.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.org.robolectric)

    coreLibraryDesugaring(libs.desugar.jdk.libs)
}

// ./gradlew assembleRelease -PcomposeCompilerReports=true
// Run the command above in order to generate the compose stability diagnose report.
// https://developer.android.com/jetpack/compose/performance/stability/diagnose
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
    compilerOptions {
        val prefix = "plugin:androidx.compose.compiler.plugins.kotlin"
        val output = layout.buildDirectory.dir("compose_compiler")
        if (project.findProperty("composeCompilerReports") == "true") {
            freeCompilerArgs.add("-P")
            freeCompilerArgs.add("$prefix:reportsDestination=$output")
        }
        if (project.findProperty("composeCompilerMetrics") == "true") {
            freeCompilerArgs.add("-P")
            freeCompilerArgs.add("$prefix:metricsDestination=$output")
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

    // ANTLR .g4 files are under antlr directory
    // Only include *.g4 files. This allows tools (e.g., IDE plugins)
    // to generate temporary files inside the base path
    source = fileTree(layout.projectDirectory.dir("antlr")) {
        include("**/*.g4")
    }

    // We want the generated source files to have this package name
    val pkgName = "$moduleId.stylesheet"
    packageName = pkgName

    // We don't want visitors and listeners.
    arguments = listOf("-no-visitor", "-no-listener")

    // Generated files are outputted inside build/generatedAntlr/{package-name}
    val outDir = "$antlrGeneratedDir/${pkgName.replace(".", "/")}"
    outputDirectory = layout.buildDirectory.dir(outDir).get().asFile
}

tasks.withType<SourceJarTask> {
    dependsOn(generateKotlinGrammarSource)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = Constants.publishGroupId
            artifactId = Constants.publishArtifactClientId
            version = Constants.publishVersion

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}