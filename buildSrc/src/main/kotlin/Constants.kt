import org.gradle.api.JavaVersion

object Constants {
    const val compileSdkVersion = 34
    const val minSdkVersion = 23
    const val targetSdkVersion = 34
    const val instrumentationRunnerClass = "androidx.test.runner.AndroidJUnitRunner"
    const val jvmTargetVersion = "17"
    const val kotlinCompilerExtVersion = "1.5.7"
    val sourceCompatibilityVersion = JavaVersion.VERSION_17
    val targetCompatibilityVersion = JavaVersion.VERSION_17

    const val moduleClient = ":client"
    const val moduleClientTestBase = ":client-test-base"
    const val moduleFoundation = ":foundation"
    const val moduleStylesheetParser = ":stylesheet-parser"

    const val publishGroupId = "org.phoenixframework.liveview-native"
    const val publishVersion = "0.0.0-dev001"
    const val publishArtifactId = "liveview-client-jetpack"
}