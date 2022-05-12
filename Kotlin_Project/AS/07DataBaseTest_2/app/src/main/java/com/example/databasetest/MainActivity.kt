package com.example.databasetest

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    //    1. 7章数据持久化
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        1.create
//        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 1)
//        让创建的数据库表升级
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)
        createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }

//        2.add
        addData.setOnClickListener {
            val db = dbHelper.writableDatabase
//          1.ContentValues的传统写法
            val values1 = ContentValues().apply {
                put("name", "The Da Vinci Code")
                put("author", "Dan Brown")
                put("pages", 454)
                put("price", 16.96)
            }
            db.insert("Book", null, values1)

            val values2 = ContentValues().apply {
                put("name", "The Lost Symbol")
                put("author", "Dan Brown")
                put("pages", 510)
                put("price", 19.95)
            }

            db.insert("Book", null, values2)
        }

//        3.update
        updateData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("price", 10.99)
            db.update("Book", values, "name = ?",
            arrayOf("The Da Vinci Code"))
        }

//        4.delete
        deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book", "pages > ?", arrayOf("500"))
        }

//        5.query
        queryData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val cursor = db.query("Book", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getString(cursor.getColumnIndex("pages"))
                    val price = cursor.getString(cursor.getColumnIndex("price"))
                    Log.d(TAG, "book name is $name")
                    Log.d(TAG, "book author is $author")
                    Log.d(TAG, "book pages is $pages")
                    Log.d(TAG, "book price is $price")
                } while (cursor.moveToNext())
            }
            cursor.close()
        }

//        6.replace
        replaceData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.beginTransaction()
            try {
                db.delete("Book", null, null)
                if (true) {
//                1.此处为中途抛出异常
//                2.不过由于事务的原因导致操作无法成功
//                3.除非法这个if代码块注释掉
//                    throw NullPointerException()
                }

//                2.利用ktx库的contentValuesOf()来进行简化
//                val values = contentValuesOf("name" to "Game of Thrones",
//                    "author" to "George Martin",
//                    "pages" to 720, "price" to 20.85)
                val values = cvOf("name" to "Game of Thrones", "author" to "George Martin", "pages" to 720, "price" to 20.85)

//                1.ContentValues的传统写法
//                val values = ContentValues().apply {
//                    put("name", "Game of Thrones")
//                    put("author", "George Martin")
//                    put("pages", 720)
//                    put("price", 20.85)
//                }
                db.insert("Book", null, values)
                db.setTransactionSuccessful()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.endTransaction()
            }
        }

        /**
         * @see later
         * 验证later的懒加载是否生效了
         */
        testLazy.setOnClickListener {
            val p by later {
                Toast.makeText(this, "test later", Toast.LENGTH_LONG).show()
                Log.d(TAG, "run codes inside later block")
                "test later"
            }
            Log.d(TAG, "By later is $p")
        }
    }
}