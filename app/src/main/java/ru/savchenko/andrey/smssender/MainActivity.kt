package ru.savchenko.andrey.smssender

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import io.victoralbertos.rx2_permissions_result.RxPermissionsResult
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val PERMISSIONS_STORAGE = arrayOf<String>(Manifest.permission.SEND_SMS)
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(FirebaseInstanceId.getInstance()!=null)
        Log.i(TAG, FirebaseInstanceId.getInstance().getToken())

        FirebaseMessaging.getInstance().subscribeToTopic("pizda");

        val name = intent.getStringExtra("title")
        val phone = intent.getStringExtra("message")
        if(name!=null){
            tvName.text = name
        }
        if(phone!=null){
            tvPhone.text = "+" + phone
        }
        btnSend.setOnClickListener({
            sendMessage()
        })
    }

    private fun sendMessage(){
        if (android.os.Build.VERSION.SDK_INT >=23) {
            requestPermissions()
        }else{
            sendSMS()
        }
    }

    private fun checkRussiaLength():Boolean{
        val phoneText = tvPhone.text.toString()
        return phoneText.length==12 && phoneText.startsWith("+7")
    }

    private fun requestPermissions() {
        RxPermissionsResult.on(this).requestPermissions(*PERMISSIONS_STORAGE)
                .subscribe({result -> result.targetUI()
                        .showPermissionStatus(result.grantResults())})
    }

    private fun showPermissionStatus(grantResults: IntArray) {
        val granted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        if (granted) {
            sendSMS()
        }
    }

    private fun sendSMS() {
        if(checkRussiaLength()) {
            val sms = SmsManager.getDefault()
            sms.sendTextMessage(tvPhone.text.toString(), null, "Тестовый код " + Math.random() * 9999 + " для " + tvName.text.toString(), null, null)
        }
    }
}
