package com.eotw95.agorademo

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.ACCESS_WIFI_STATE
import android.Manifest.permission.BLUETOOTH
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.Manifest.permission.CAMERA
import android.Manifest.permission.INTERNET
import android.Manifest.permission.MODIFY_AUDIO_SETTINGS
import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.RECORD_AUDIO
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.eotw95.agorademo.ui.theme.AgoraDemoTheme

class MainActivity : ComponentActivity() {
    companion object {
        private val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isGrantedSelfPermission(getRequiredPermissions())) {
            startVoiceCalling()
        } else {
            ActivityCompat.requestPermissions(this, getRequiredPermissions(), 22)
        }

        enableEdgeToEdge()
        setContent {
            AgoraDemoTheme {
                AgoraDemoApp()
            }
        }
    }

    /**
     * ActivityCompat.requestPermissions()が呼ばれ後に通知されるコールバック
     * Permissionの許可設定を再度チェックする
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        Log.d(TAG, "onRequestPermissionsResult")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)

        if (isGrantedSelfPermission(getRequiredPermissions())) {
            startVoiceCalling()
        }
    }

    /**
     * 音声通話を開始
     */
    private fun startVoiceCalling() {
        Log.d(TAG, "startVoiceCalling")

        val agoraService = AgoraServiceImpl()
        agoraService.initializeRtcEngine(applicationContext)
        agoraService.joinChannel()
    }

    /**
     * ユーザーの許可設定が必要なPermission
     */
    private fun getRequiredPermissions(): Array<String> {
        Log.d(TAG, "getRequirePermission")

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                CAMERA,
                RECORD_AUDIO,
                BLUETOOTH_CONNECT,
                READ_PHONE_STATE
            )
        } else {
            arrayOf(
                CAMERA,
                RECORD_AUDIO
            )
        }
    }

    /**
     * Permissionが許可されているかどうかチェック
     */
    private fun isGrantedSelfPermission(permissions: Array<String>): Boolean {
        Log.d(TAG, "isGrantedSelfPermission")

        permissions.forEach { permission ->
            val granted = ContextCompat.checkSelfPermission(application, permission)
            if (granted != PackageManager.PERMISSION_GRANTED) return false
        }

        return true
    }
}