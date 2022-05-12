package com.example.activitylifecycletest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.main_layout.*

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(R.layout.main_layout)

//        获取当Activity被销毁之前通过onSaveInstanceState保存的数据
        if (savedInstanceState != null) {
            val tempData = savedInstanceState.getString("data_key")
            Log.d(TAG, "tempData is $tempData")
        }

        startNormalActivity.setOnClickListener {
            val intent1 = Intent(this, NormalActivity::class.java)
            startActivity(intent1)
        }

        startDialogActivity.setOnClickListener {
            val intent2 = Intent(this, DialogActivity::class.java)
            startActivity(intent2)
        }
    }

//    保证在Activity被回收之前一定会被调用
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val tempData: String = "Something that you have typed!"
        outState.putString("data_key", tempData)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }
}