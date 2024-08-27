plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.roborazzi)
}

val moduleId = "org.phoenixframework.liveview.addons"
android {
    namespace = moduleId
    compileSdk = Constants.compileSdkVersion

    defaultConfig {
        minSdk = Constants.minSdkVersion

        testApplicationId = "$moduleId.test"
        testInstrumentationRunner = Constants.instrumentationRunnerClass
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Constants.kotlinCompilerExtVersion
    }

    testNamespace = "$moduleId.test"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(project(Constants.moduleClient))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.io.coil.kt)
    implementation(libs.io.coil.kt.coil.compose)
    implementation(libs.io.coil.kt.coil.svg)

    // Test dependencies
    testImplementation(project(Constants.moduleClientTestBase))
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.androidx.compose.ui.test.junit4.android)

    testImplementation(libs.io.coil.kt.coil.test)
    testImplementation(libs.io.github.takahirom.roborazzi)
    testImplementation(libs.io.github.takahirom.roborazzi.compose)
    testImplementation(libs.io.github.takahirom.roborazzi.junit.rule)
    testImplementation(libs.koin.test)
    testImplementation(libs.org.robolectric)
}

// Configuring Java Lib Path in order to find the native library before running the Unit Tests
tasks.withType<Test>().configureEach {
    doFirst {
        copyDesktopJniLibs(rootDir, this@configureEach)
    }
}