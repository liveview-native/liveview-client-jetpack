plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.roborazzi)
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
}

dependencies {
    api(project(Constants.moduleFoundation))
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

    // These dependencies are exported to consumers, that is to say found on their compile classpath.
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.navigation.compose)

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