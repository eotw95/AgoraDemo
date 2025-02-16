package com.eotw95.agorademo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.agora.rtc2.RtcEngine

@Composable
fun VideoCallingScreen(agoraService: AgoraService) {
    val remoteUserId by agoraService.remoteUserId.collectAsState()
    val localUserId by agoraService.localUserId.collectAsState()

    agoraService.mRtcEngine?.let { rtcEngine ->
        if (remoteUserId != null && localUserId != null) {
            VideoCallingScreenContent(rtcEngine, remoteUserId!!, localUserId!!)
        }
    }
}

@Composable
fun VideoCallingScreenContent(rtcEngine: RtcEngine, remoteUserId: Int, localUserId: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        RemoteVideoScreen(rtcEngine, remoteUserId)
        LocalVideoScreen(rtcEngine, localUserId)
    }
}