package com.eotw95.agorademo

import android.content.Context
import android.util.Log
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig

interface AgoraService {
    fun initializeRtcEngine(context: Context)
    fun joinChannel()
}

class  AgoraServiceImpl: AgoraService {
    companion object {
        private const val TAG = "AgoraServiceImpl"
    }

    var mRtcEngine: RtcEngine? = null

    /**
     * Rtc Engine を生成
     */
    override fun initializeRtcEngine(context: Context) {
        val rtcConfig = RtcEngineConfig().apply {
            mAppId = AgoraConfig.APP_ID
            mContext = context
            mEventHandler = object : IRtcEngineEventHandler() {
                override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
                    super.onJoinChannelSuccess(channel, uid, elapsed)
                    println("onJoinChannelSuccess")
                }

                override fun onUserJoined(uid: Int, elapsed: Int) {
                    super.onUserJoined(uid, elapsed)
                    println("onUserJoined")
                }

                override fun onUserOffline(uid: Int, reason: Int) {
                    super.onUserOffline(uid, reason)
                    println("onUserOffline")
                }

                override fun onError(err: Int) {
                    super.onError(err)
                    println("onError")
                }
            }
        }

        try {
            mRtcEngine = RtcEngine.create(rtcConfig)
        } catch (e: Exception) {
            Log.e(TAG, "creating rtc engine is fail.")
        }
    }

    /**
     * Rtc Channelに参加
     */
    override fun joinChannel() {
        if (mRtcEngine == null) {
            Log.e(TAG, "joinChannel, mRtcEngine is null")
            return
        }

        val channelMediaOptions = ChannelMediaOptions().apply {
            clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            publishMicrophoneTrack = true;
        }

        mRtcEngine?.joinChannel(
            AgoraConfig.TOKEN,
            AgoraConfig.CHANNEL_NAME,
            0,
            channelMediaOptions
        )
    }
}