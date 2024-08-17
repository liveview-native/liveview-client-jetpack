import org.gradle.api.tasks.testing.Test
import java.io.File

// TODO The Core-Jetpack project only generates the native platform library files for the
//  following architectures (arm, arm64, x86, and x86_64). In order to run the tests in the local
//  machine, we need the library for the host machine. For now, this file is being generated by
//  adding the "darwin-aarch64" (or "darwin-x86-64" for Intel Macbooks) target, and running the
//  following command in the Core-Jetpack project: `./gradlew assembleRelease`.
//  The library files are generated at "core/build/rustJniLibs/desktop" directory. The ideal
//  solution is release a dependency just for unit tests and declare at the `dependencies` section
//  above like:
//  `testImplementation "com.github.liveview-native:liveview-native-core-jetpack-host:<version>"`
fun copyDesktopJniLibs(rootDir: File, test: Test) {
    val jniLibsForDesktopDir = File("$rootDir/core-jetpack-desktop-libs/jniLibs")
    val archTypesSubdirs = jniLibsForDesktopDir.listFiles() ?: emptyArray()
    for (dir in archTypesSubdirs) {
        // Selecting the proper JNI lib file for run the unit tests
        // in according to the architecture. e.g.: darwin-aarch64, darwin-x86-64
        val arch = System.getProperty("os.arch").replace("_", "-")
        if (dir.isDirectory && dir.name.contains(arch)) {
            test.systemProperty("java.library.path", dir.absolutePath)
            test.systemProperty("jna.library.path", dir.absolutePath)
            break
        }
    }
}