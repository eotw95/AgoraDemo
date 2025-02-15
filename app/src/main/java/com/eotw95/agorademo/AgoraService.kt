package com.eotw95.agorademo

import android.content.Context
import android.util.Log
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig

/**
 * Agora のAPIクライアント
 */
interface AgoraService {
    fun initializeRtcEngine(context: Context)
    fun joinChannel()
    fun leaveChannel()
}

class  AgoraServiceImpl: AgoraService {
    companion object {
        private const val TAG = "AgoraServiceImpl"
    }

    private var mRtcEngine: RtcEngine? = null

    /**
     * Rtc Engine を生成
     */
    override fun initializeRtcEngine(context: Context) {
        // 古いRtcEngineの影響を受けないようにするため初期化前に解放する
        RtcEngine.destroy()

        // RtcEngineConfigを生成
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

        // RtcEngineを生成
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

        // ChannelMediaOptionsを生成
        val channelMediaOptions = ChannelMediaOptions().apply {
            clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            publishMicrophoneTrack = true
        }

        // Channelに参加
        mRtcEngine?.joinChannel(
            AgoraConfig.TOKEN,
            AgoraConfig.CHANNEL_NAME,
            0,
            channelMediaOptions
        )
    }

    /**
     * Channelから退出
     * RtcEngineインスタンスを解放
     */
    override fun leaveChannel() {
        mRtcEngine?.leaveChannel()
        mRtcEngine = null
    }
}