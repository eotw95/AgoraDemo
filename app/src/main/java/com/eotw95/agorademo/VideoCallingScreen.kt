package com.eotw95.agorademo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import io.agora.rtc2.RtcEngine

@Composable
fun VideoCallingScreen(agoraService: AgoraService) {
    val partnerUserId = agoraService.partnerUserId.collectAsState()
    agoraService.mRtcEngine?.let { rtcEngine ->
        VideoCallingScreenContent(rtcEngine, partnerUserId.value)
    }
}

@Composable
fun VideoCallingScreenContent(rtcEngine: RtcEngine, partnerUserId: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        RemoteVideoScreen(rtcEngine, partnerUserId)
        LocalVideoScreen(rtcEngine)
    }
}