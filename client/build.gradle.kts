plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.roborazzi)
}

android {
    compileSdk = Constants.compileSdkVersion

    defaultConfig {
        minSdk = Constants.minSdkVersion

        testInstrumentationRunner = Constants.instrumentationRunnerClass
        testApplicationId = Constants.testApplicationId

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
    testOptions.unitTests.isIncludeAndroidResources = true
    namespace = "org.phoenixframework.liveview"
}

dependencies {
    api(project(":foundation"))
    // These dependencies are used internally, and not exposed to consumers on their own compile classpath.
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.com.google.code.gson)

    implementation(libs.io.coil.kt)
    implementation(libs.io.coil.kt.coil.compose)
    implementation(libs.io.coil.kt.coil.svg)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // These dependencies are exported to consumers, that is to say found on their compile classpath.
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.navigation.compose)

    // Test dependencies
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.androidx.compose.ui.test.junit4.android)

    testImplementation(libs.io.github.takahirom.roborazzi)
    testImplementation(libs.io.github.takahirom.roborazzi.compose)
    testImplementation(libs.io.github.takahirom.roborazzi.junit.rule)
    testImplementation(libs.io.coil.kt.coil.test)
    testImplementation(libs.org.robolectric)
    testImplementation(libs.koin.test)
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

// TODO The Core-Jetpack project only generates the native platform library files for the
//  following architectures (arm, arm64, x86, and x86_64). In order to run the tests in the local
//  machine, we need the library for the host machine. For now, this file is being generated by
//  adding the "darwin-aarch64" (or "darwin-x86-64" for Intel Macbooks) target, and running the
//  following command in the Core-Jetpack project: `./gradlew assembleRelease`.
//  The library files are generated at "core/build/rustJniLibs/desktop" directory. The ideal
//  solution is release a dependency just for unit tests and declare at the `dependencies` section
//  above like:
//  `testImplementation "com.github.liveview-native:liveview-native-core-jetpack-host:<version>"`
// Configuring Java Lib Path in order to find the native library before running the Unit Tests
tasks.withType<Test>().configureEach {
    doFirst {
        val jniLibsForDesktopDir = File("${projectDir}/src/test/jniLibs")
        val archTypesSubdirs = jniLibsForDesktopDir.listFiles() ?: emptyArray()
        for (dir in archTypesSubdirs) {
            // Selecting the proper JNI lib file for run the unit tests
            // in according to the architecture. e.g.: darwin-aarch64, darwin-x86-64
            val arch = System.getProperty("os.arch").replace("_", "-")
            if (dir.isDirectory && dir.name.contains(arch)) {
                systemProperty("java.library.path", dir.absolutePath)
                break
            }
        }
    }
}