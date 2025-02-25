import org.gradle.api.JavaVersion

object Constants {
    const val compileSdkVersion = 35
    const val minSdkVersion = 23
    const val targetSdkVersion = 35
    const val instrumentationRunnerClass = "androidx.test.runner.AndroidJUnitRunner"
    const val jvmTargetVersion = "1.8"
    const val kotlinCompilerExtVersion = "1.5.7"
    val sourceCompatibilityVersion = JavaVersion.VERSION_1_8
    val targetCompatibilityVersion = JavaVersion.VERSION_1_8

    const val moduleClient = ":client"
    const val moduleClientAddons = ":client-addons"
    const val moduleClientTestBase = ":client-test-base"

    const val publishGroupId = "com.github.liveview-native"
    const val publishVersion = "0.0.0-dev001"
    const val publishArtifactClientId = "liveview-client-jetpack"
    const val publishArtifactClientAddonsId = "liveview-client-jetpack-addons"
    const val publishArtifactClientTestId = "liveview-client-jetpack-test"
}