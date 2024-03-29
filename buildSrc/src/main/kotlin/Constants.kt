import org.gradle.api.JavaVersion

object Constants {
    const val compileSdkVersion = 34
    const val minSdkVersion = 23
    const val targetSdkVersion = 34
    const val instrumentationRunnerClass = "com.dockyard.liveviewtest.liveview.runner.LiveViewTestRunner"
    const val testApplicationId = "org.phoenixframework.liveview.android.test"
    const val jvmTargetVersion = "17"
    const val kotlinCompilerExtVersion = "1.5.7"
    val sourceCompatibilityVersion = JavaVersion.VERSION_17
    val targetCompatibilityVersion = JavaVersion.VERSION_17
}