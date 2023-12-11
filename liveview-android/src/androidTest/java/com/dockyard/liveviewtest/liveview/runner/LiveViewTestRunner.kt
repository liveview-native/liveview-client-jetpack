package com.dockyard.liveviewtest.liveview.runner

import android.os.Bundle
import com.karumi.shot.ShotTestRunner

class LiveViewTestRunner : ShotTestRunner() {
    override fun onCreate(args: Bundle) {
        // This parameter must be passed via command line in order to generate the screenshot
        // reference images. This is done using `android.testInstrumentationRunnerArguments`.
        // ./gradlew executeScreenshotTests -Precord -Pandroid.testInstrumentationRunnerArguments.record=true
        isRecording = args.containsKey("record")
        super.onCreate(args)
    }

    companion object {
        var isRecording: Boolean = false
            private set
    }
}
