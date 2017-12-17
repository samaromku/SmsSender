package ru.savchenko.andrey.smssender

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import io.victoralbertos.rx2_permissions_result.RxPermissionsResult


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        sendSMS("89189930667", "test message for nice guy")
        requestPermissions()
    }

    private fun requestPermissions() {
        val PERMISSIONS_STORAGE = arrayOf<String>(Manifest.permission.SEND_SMS)

        RxPermissionsResult.on(this).requestPermissions(*PERMISSIONS_STORAGE)
                .subscribe({result -> result.targetUI()
                        .showPermissionStatus(result.grantResults())})
    }

    private fun showPermissionStatus(grantResults: IntArray) {
        val granted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        if (granted) {
            sendSMS("89189930667", "test message for nice guy")
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, null, null)
    }
}
