import Android
import Foundation

extension String {
    public init(fromJNI value: jstring?, in environment: UnsafeMutablePointer<JNIEnv?>) {
        guard let value else {
            self.init()
            return
        }
        let cString = environment.pointee!.pointee.GetStringUTFChars(environment, value, nil)!
        defer { environment.pointee!.pointee.ReleaseStringUTFChars(environment, value, cString) }
        self = String(cString: cString) // copy
    }
}

@_cdecl("Java_com_dockyard_lightpandarenderer_LightpandaRuntime_init")
@MainActor
public func LightpandaRuntime_init(env: UnsafeMutablePointer<JNIEnv?>, clazz: jclass, urlString: jstring) -> jlong {
    // Convert jstring to Swift String
    let runtime = LightpandaRuntime(url: URL(string: String(fromJNI: urlString, in: env))!)
    return jlong(Int(bitPattern: Unmanaged.passRetained(runtime).toOpaque()))
}

@_cdecl("Java_com_dockyard_lightpandarenderer_LightpandaRuntime_start")
public func LightpandaRuntime_start(env: UnsafeMutablePointer<JNIEnv?>, clazz: jclass, handle: jlong) {
    let runtime = Unmanaged<LightpandaRuntime>.fromOpaque(UnsafeRawPointer(bitPattern: Int(handle))!).takeUnretainedValue()
    Task {
        try await runtime.start()
    }
}

@_cdecl("Java_com_dockyard_lightpandarenderer_LightpandaRuntime_dom")
public func LightpandaRuntime_dom(env: UnsafeMutablePointer<JNIEnv?>, clazz: jclass, handle: jlong) {
    let runtime = Unmanaged<LightpandaRuntime>.fromOpaque(UnsafeRawPointer(bitPattern: Int(handle))!).takeUnretainedValue()
    
}