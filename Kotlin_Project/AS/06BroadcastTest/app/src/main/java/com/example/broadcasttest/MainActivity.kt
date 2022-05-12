package com.example.broadcasttest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var changeReceiver: ChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        4.强制下线
        forceOffline.setOnClickListener {
            val intent = Intent("com.example.broadcasttest.FORCE_OFFLINE")
            sendBroadcast(intent)
        }

//        3.监测时间的广播
//        val intentFilter = IntentFilter()
//        intentFilter.addAction("android.intent.action.TIME_TICK")
//        changeReceiver = ChangeReceiver()
//        registerReceiver(changeReceiver, intentFilter)
//        发送和接收自定义广播
//        2.有序广播
//        button.setOnClickListener {
//            val intent = Intent("com.example.broadcasttest.MY_BROADCAST")
//            intent.setPackage(packageName)
//            sendOrderedBroadcast(intent, null)
//        }
//        1.标准广播
//        button.setOnClickListener {
//            val intent = Intent("com.example.broadcasttest.MY_BROADCAST")
//            intent.setPackage(packageName)
//            sendBroadcast(intent)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(changeReceiver)
    }

    inner class ChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "Time has changed!", Toast.LENGTH_SHORT).show()
        }
    }
}