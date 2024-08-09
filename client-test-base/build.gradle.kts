plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "org.phoenixframework.liveview.test.base"
    compileSdk = Constants.compileSdkVersion

    defaultConfig {
        minSdk = Constants.minSdkVersion

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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Constants.kotlinCompilerExtVersion
    }
}

dependencies {
    implementation(project(":client"))
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.androidx.compose.ui.test.junit4.android)
    implementation(libs.io.github.takahirom.roborazzi)
    implementation(libs.io.github.takahirom.roborazzi.compose)
    implementation(libs.io.github.takahirom.roborazzi.junit.rule)
    implementation(libs.junit)
    implementation(libs.koin.test)
    implementation(libs.org.robolectric)
}