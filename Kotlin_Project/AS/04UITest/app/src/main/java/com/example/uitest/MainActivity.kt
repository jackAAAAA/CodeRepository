package com.example.uitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        隐藏系统自定义标题栏，从而实现加载自定义标题栏
        supportActionBar?.hide()

//        Lambda写法
//        button.setOnClickListener {
//            when (it?.id) {
//                R.id.button -> {
//                    val inputText = editText.text.toString()
//                    Toast.makeText(this, inputText, Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//        传统写法：继承View.OnClickListener接口，重写OnClick方法
//        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.button -> {
////                1.获取文本框的输入内容并用Toast来显示
////                val inputText = editText.text.toString()
////                Toast.makeText(this, inputText, Toast.LENGTH_LONG).show()
////                2.加载imageView
////                imageView.setImageResource(R.drawable.img_1)
////                3.让进度条显示或消失
////                if (progressBar.visibility == View.VISIBLE) {
////                    progressBar.visibility = View.GONE
////                } else {
////                    progressBar.visibility = View.VISIBLE
////                }
////                4.点击水平进度条，让其每次增加10的进度
////                progressBar.progress += 10
//                AlertDialog.Builder(this).apply {
//                    setTitle("This is a Dialog")
//                    setMessage("Something important.")
//                    setCancelable(false)
//                    setPositiveButton("OK") { dialog, which ->
//                    }
//                    setNegativeButton("Cancel") { dialog, which ->
//                    }
//                    show()
//                }
//            }
//        }
//    }
}