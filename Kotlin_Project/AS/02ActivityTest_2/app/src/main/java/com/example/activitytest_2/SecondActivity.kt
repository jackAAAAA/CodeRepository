package com.example.activitytest_2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.second_layout.*

class SecondActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SecondActivity", "Task id is $taskId")
        setContentView(R.layout.second_layout)
        button2.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

//        启动模式测试
//        2.singleTop
//        button2.setOnClickListener {
//            val intent = Intent(this, FirstActivity::class.java)
//            startActivity(intent)
//        }

//        隐式Intent
//        获取来自FirstActivity的intent
//        val extraData: String = intent.getStringExtra("extra_data")
//        Log.d("SecondActivity", "extra data is $extraData")

//        隐式Intent
//        button2.setOnClickListener {
//            val intent: Intent = Intent()
//            val data: String = "Hello FirstActivity!"
//            intent.putExtra("data_return", data)
//            setResult(RESULT_OK, intent)
//            finish()
//        }
    }

    override fun onBackPressed() {
        val intent: Intent = Intent()
        intent.putExtra("data_return_back", "Hello FirstActivity")
        setResult(RESULT_OK, intent)
        finish()
    }

//        启动模式测试
//    3.singleTask
    override fun onDestroy() {
        super.onDestroy()
        Log.d("SecondActivity", "onDestroy: ")
    }

    companion object {
        fun actionStart(context: Context, data1: String, data2: String) {
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("param1", data1)
            intent.putExtra("param2", data2)
            context.startActivity(intent)
        }
    }
}