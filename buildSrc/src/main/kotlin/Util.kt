import org.gradle.api.tasks.testing.Test
import java.io.File
import java.util.zip.ZipFile

fun copyDesktopJniLibs(rootDir: File, test: Test) {
    val classpath = test.classpath.files
    val hostJar = classpath.find { it.name.contains("liveview-native-core-host") }

    val jniLibsForDesktopDir = File("$rootDir/core-jetpack-desktop-libs/jniLibs")
    test.systemProperty("java.library.path", jniLibsForDesktopDir.absolutePath)
    test.systemProperty("jna.library.path", jniLibsForDesktopDir.absolutePath)
    test.classpath = test.classpath.plus(test.project.files(jniLibsForDesktopDir.absolutePath))

    if (hostJar != null && hostJar.exists()) {
        println("found host jar: ${hostJar.absolutePath}")

        // Create destination directories
        val darwinDir = File("${rootDir}/core-jetpack-desktop-libs/jniLibs/darwin-aarch64")
        val linuxDir = File("${rootDir}/core-jetpack-desktop-libs/jniLibs/linux-x86-64")
        val windowsDir = File("${rootDir}/core-jetpack-desktop-libs/jniLibs/windows-x86-64")

        ZipFile(hostJar).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                if (!entry.isDirectory) {
                    val destDir = when {
                        entry.name.endsWith(".dylib") -> darwinDir
                        entry.name.endsWith(".so") -> linuxDir
                        entry.name.endsWith(".dll") -> windowsDir
                        else -> null
                    }

                    destDir?.let { dir ->
                        val destFile = File(dir, File(entry.name).name)
                        println("extracting: ${destFile.absolutePath}")
                        zip.getInputStream(entry).use { input ->
                            destFile.outputStream().use { output ->
                                input.copyTo(output)
                            }
                        }
                    }
                }
            }
        }
    } else {
        println("host jar not found in test classpath")
    }
}
