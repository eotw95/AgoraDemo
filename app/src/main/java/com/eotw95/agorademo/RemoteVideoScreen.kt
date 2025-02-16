package com.eotw95.agorademo

import android.view.SurfaceView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoCanvas

/**
 * 通話相手の通話中のPreview画面
 */
@Composable
fun RemoteVideoScreen(rtcEngine: RtcEngine, uid: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                SurfaceView(context).apply {
                    rtcEngine.setupRemoteVideo(
                        // uidはIRtcEngineEventHandler#onUserJoined()で通知されるuidを使用する
                        VideoCanvas(this, VideoCanvas.RENDER_MODE_FIT, uid)
                    )
                }
            }
        )
    }
}