plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}