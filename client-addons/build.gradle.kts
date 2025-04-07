plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.roborazzi)
    id("maven-publish")
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

    testNamespace = "$moduleId.test"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.0")

    implementation(project(Constants.moduleClient))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)

    implementation(libs.io.coil.kt)
    implementation(libs.io.coil.kt.coil.compose)
    implementation(libs.io.coil.kt.coil.svg)

    // Test dependencies
    testImplementation(project(Constants.moduleClientTestBase))
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.androidx.compose.ui.test.junit4)
    testImplementation(libs.io.coil.kt.coil.test)
    testImplementation(libs.junit)
}

// Configuring Java Lib Path in order to find the native library before running the Unit Tests
tasks.withType<Test>().configureEach {
    doFirst {
        val coreVersion = project.extensions.getByType<VersionCatalogsExtension>()
            .named("libs")
            .findVersion("liveview-native-core-jetpack")
            .get()
            .toString()
        copyDesktopJniLibs(rootDir, this@configureEach, coreVersion)
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = Constants.publishGroupId
            artifactId = Constants.publishArtifactClientAddonsId
            version = Constants.publishVersion

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
