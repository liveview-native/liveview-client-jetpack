import org.gradle.api.tasks.testing.Test
import org.gradle.api.GradleException
import org.gradle.kotlin.dsl.withGroovyBuilder
import java.io.File

fun copyDesktopJniLibs(rootDir: File, test: Test, coreVersion: String) {
    val currentArch = when {
        org.gradle.internal.os.OperatingSystem.current().isMacOsX -> "darwin-aarch64"
        org.gradle.internal.os.OperatingSystem.current().isLinux -> "linux-x86-64"
        else -> throw GradleException("unsupported OS")
    }

    val libsDir = File("${rootDir}/build/nativeLibs/$currentArch")
    val outputFile = File("${libsDir}/liveview-native-core-$currentArch.jar")

    libsDir.mkdirs()

    val url = "https://github.com/liveview-native/liveview-native-core/releases/download/$coreVersion/liveview-native-core-$currentArch.jar"

    test.ant.withGroovyBuilder {
        "get"(
            "src" to url,
            "dest" to outputFile.absolutePath,
            "verbose" to true
        )
    }

    println(outputFile.absolutePath)

    if (outputFile.exists()) {
        test.classpath += test.project.files(outputFile)

        val jniLibsDir = File("${rootDir}/core-jetpack-desktop-libs/jniLibs/$currentArch")
        jniLibsDir.mkdirs()

        test.project.copy {
            from(test.project.zipTree(outputFile))
            into(jniLibsDir)
            include("*.so", "*.dylib", "*.dll")
        }

        test.systemProperty("java.library.path", jniLibsDir.absolutePath)
        test.systemProperty("jna.library.path", jniLibsDir.absolutePath)
    } else {
        println("native library JAR not found at ${outputFile.absolutePath}")
    }
}
