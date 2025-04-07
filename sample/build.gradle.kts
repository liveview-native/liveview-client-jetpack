plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = Constants.compileSdkVersion
    defaultConfig {
        minSdk = Constants.minSdkVersion
        targetSdk = Constants.targetSdkVersion

        applicationId = "com.dockyard.android.liveviewsample"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = Constants.instrumentationRunnerClass
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Constants.kotlinCompilerExtVersion
    }
    namespace = "com.dockyard.android.liveviewsample"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material)
    implementation(project(Constants.moduleClient))
    implementation(project(Constants.moduleClientAddons))
    implementation(libs.live.form) {
        isChanging = true
        exclude(group = Constants.publishGroupId, module = Constants.publishArtifactClientId)
    }

    coreLibraryDesugaring(libs.desugar.jdk.libs)
}