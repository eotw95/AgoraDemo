package com.eotw95.agorademo

import android.view.SurfaceView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoCanvas

/**
 * 自分の通話中のPreview画面
 */
@Composable
fun LocalVideoScreen(rtcEngine: RtcEngine, uid: Int) {
    Box(modifier = Modifier.width(200.dp).height(400.dp)) {
        AndroidView(
            factory = { context ->
                SurfaceView(context).apply {
                    setZOrderMediaOverlay(true)
                    rtcEngine.setupLocalVideo(
                        // uidはIRtcEngineEventHandler#onJoinChannelSuccess()で通知されるuidを使用する
                        VideoCanvas(this, VideoCanvas.RENDER_MODE_FIT, uid)
                    )
                }
            }
        )
    }
}