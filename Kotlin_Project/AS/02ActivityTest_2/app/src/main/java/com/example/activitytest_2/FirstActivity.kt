package com.example.activitytest_2

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.first_layout.*

class FirstActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FirstActivity", "Task id $taskId")
        setContentView(R.layout.first_layout)

//        调用静态方法
//        1.@JvmStatic添加在单例类或者普通类的companion object的方法之上
//        2.顶层方法
        button1.setOnClickListener {
            ActivityCollector.doAction()
            Util.doAction2()
            doSomething()
        }

//        通过companion object启动SecondActivity
//        button1.setOnClickListener {
//            SecondActivity.actionStart(this, "data1", "data2")
//        }

//        启动模式测试
//    4.singleInstance
//        button1.setOnClickListener {
//            val intent = Intent(this, SecondActivity::class.java)
//            startActivity(intent)
//        }

//        启动模式测试
//        1.standard
//        button1.setOnClickListener {
//            val intent = Intent(this, FirstActivity::class.java)
//            startActivity(intent)
//        }
//        启动模式测试
//        2.singleTop
//        button1.setOnClickListener {
//            val intent = Intent(this, SecondActivity::class.java)
//            startActivity(intent)
//        }

//        在app/build.gradle文件的头部引入一个kotlin-android-extensions插件；
//        这个插件会根据布局文件中定义的控件id自动生成一个具有相同名称的变量，此时findViewById可以去掉
//        val button1: Button = findViewById(R.id.button1)
//        button1.setOnClickListener{
//            Toast.makeText(this, "Clicked Button1", Toast.LENGTH_LONG).show()
//        }

//        销毁Activity
//        button1.setOnClickListener {
//            finish()
//        }

//        显示Itent
//        button1.setOnClickListener {
//            val intent = Intent(this, SecondActivity::class.java)
//            startActivity(intent)
//        }

//        隐式Intent
//        默认Category
//        button1.setOnClickListener {
//            val intent = Intent("com.example.activitytest_2.ACTION_START")
//            startActivity(intent)
//        }

//        隐式Intent
//        自定义Category
//        button1.setOnClickListener {
//            val intent = Intent("com.example.activitytest_2.ACTION_START")
//            intent.addCategory("com.example.activitytest_2.MY_CATEGORY")
//            startActivity(intent)
//        }

//        隐式Intent
//        解析网页
//        button1.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("https://cloud.tencent.com")
//            startActivity(intent)
//        }

//        隐式Intent
//        解析tel
//        button1.setOnClickListener {
//            val intent = Intent(Intent.ACTION_DIAL)
//            intent.data = Uri.parse("tel:10086")
//            startActivity(intent)
//        }

//        隐式Intent
//        向第2个Activity传递数据
//        button1.setOnClickListener {
//            val data = "Hello SecondActivity"
//            val intent = Intent(this, SecondActivity::class.java)
//            intent.putExtra("extra_data", data)
//            startActivity(intent)
//        }

//        隐式Intent
//        获取启动的SecondActivity的返回值
//        button1.setOnClickListener {
//            val intent = Intent(this, SecondActivity::class.java)
//            startActivityForResult(intent, 1)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode == RESULT_OK) {
                val returnData: String? = data?.getStringExtra("data_return")
                val returnData2: String? = data?.getStringExtra("data_return_back")
                Log.d("FirstActivity", "returnData is $returnData!")
                Log.d("FirstActivity", "returnData is $returnData2!")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item -> {
                Toast.makeText(this, "Clicked Add!", Toast.LENGTH_LONG).show()
            }
            R.id.remove_item -> {
                Toast.makeText(this, "Clicked Remove!", Toast.LENGTH_LONG).show()
            }
        }
        return true
    }

//        启动模式测试
//    3.singleTask
    override fun onRestart() {
        super.onRestart()
        Log.d("FirstActivity", "onRestart: ")
    }
}