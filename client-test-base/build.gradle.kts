plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
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
    implementation(project(Constants.moduleClient))
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.test.junit4.android)
    implementation(libs.io.github.takahirom.roborazzi)
    implementation(libs.io.github.takahirom.roborazzi.compose)
    implementation(libs.io.github.takahirom.roborazzi.junit.rule)
    implementation(libs.junit)
    implementation(libs.koin.test)
    implementation(libs.org.robolectric)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = Constants.publishGroupId
            artifactId = Constants.publishArtifactClientTestId
            version = Constants.publishVersion

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}