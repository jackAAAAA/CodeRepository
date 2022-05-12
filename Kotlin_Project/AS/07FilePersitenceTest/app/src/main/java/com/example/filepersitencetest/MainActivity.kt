package com.example.filepersitencetest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        3.读取SP中的数据
        restoreButton.setOnClickListener {
            val prefs = getSharedPreferences("data", MODE_PRIVATE)
            val str1 = prefs.getString("name", "please input your name!")
            val str2 = prefs.getBoolean("married", false)
            val str3 = prefs.getInt("age", 0)
            Log.d(TAG, "name is $str1\n")
            Log.d(TAG, "married is $str2\n")
            Log.d(TAG, "age is $str3\n")
        }

//        2.通过SP中来保存数据
        /**
         * Kotlin当中函数跳转的写法
         * 函数扩展 + 高阶函数
         * @see open
         */
        saveButton.setOnClickListener {
//            2.高阶函数写法
              getSharedPreferences("data", Context.MODE_PRIVATE).edit {
                  putString("name", "Tom")
                  putInt("age", 28)
                  putBoolean("married", false)
              }
//            getSharedPreferences("data", Context.MODE_PRIVATE).open {
//                putString("name", "Tom")
//                putInt("age", 28)
//                putBoolean("married", false)
//            }

//            1.传统写法
//            val editor = getSharedPreferences("data", MODE_PRIVATE).edit()
////        val editor = getPreferences(MODE_PRIVATE).edit()
//            editor.putString("name", "Jack")
//            editor.putBoolean("married", false)
//            editor.putInt("age", 26)
//            editor.apply()
        }

//        1.通过读取文件来保存数据
//        val inputText = load()
//        if (inputText.isNotEmpty()) {
//            editText.setText(inputText)
//            editText.setSelection(inputText.length)
//            Toast.makeText(this, "Restoring data successfully!", Toast.LENGTH_LONG).show()
//        }
    }

    private fun load(): String {
        val content: StringBuilder = StringBuilder()
        val input = openFileInput("data")
        val reader = BufferedReader(InputStreamReader(input))
        reader.use {
            reader.forEachLine {
                content.append(it)
            }
        }
        return content.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        val inputText = editText.text.toString()
        save(inputText)
    }

    private fun save(inputText: String) {
        val output = openFileOutput("data", MODE_PRIVATE)
        val writer = BufferedWriter(OutputStreamWriter(output))
        writer.use {
            it.write(inputText)
        }
    }
}