plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "org.phoenixframework.liveview.foundation"
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
    api(project(":stylesheet-parser"))
    api(libs.org.jetbrains.kotlinx.collections.immutable)
    api(libs.org.jsoup)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.com.google.code.gson)
    implementation(libs.com.github.liveview.native.core.jetpack)
    implementation(libs.com.github.dsrees.javaphoenixclient)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}