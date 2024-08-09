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
}