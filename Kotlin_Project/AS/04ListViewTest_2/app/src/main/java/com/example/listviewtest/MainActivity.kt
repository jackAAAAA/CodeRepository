package com.example.listviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val data = listOf("Apple","Banana","Orange","watermelon",
        "Pear","Grape","Pineapple","Strawberry","Cherry","Mango",
        "Apple","Banana","orange", "watermelon","Pear", "Grape","Pineapple", "Strawberry", "Cherry","Mango")

    private val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        2.有图片
//        1.初始化数据源
        initFruits()
//        2.设置ListView的adapter
        val adapter = FruitAdapter(this, R.layout.fruit_item, fruitList)
        listView.adapter = adapter

        listView.setOnItemClickListener{ _, _, position, _ ->
            val fruit = fruitList[position]
            Toast.makeText(this, fruit.name, Toast.LENGTH_SHORT).show()
        }

//        1.无图片
//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
//        listView.adapter = adapter
    }

    private fun initFruits() {
        repeat(2) {
            fruitList.add(Fruit("Apple", R.drawable.apple_pic))
            fruitList.add (Fruit ( "Banana", R.drawable.banana_pic))
            fruitList.add (Fruit ("Orange ",R.drawable.orange_pic))
            fruitList.add (Fruit ("Watermelon",R.drawable.watermelon_pic))
            fruitList.add (Fruit( "Pear",R.drawable.pear_pic))
            fruitList.add (Fruit ( "Grape",R.drawable.grape_pic))
            fruitList.add (Fruit ("Pineapple",R.drawable.pineapple_pic))
            fruitList.add (Fruit ("Strawberry",R.drawable.strawberry_pic))
            fruitList.add (Fruit ("Cherry", R.drawable.cherry_pic))
            fruitList.add (Fruit ( "Mango",R.drawable.mango_pic))
        }
    }
}