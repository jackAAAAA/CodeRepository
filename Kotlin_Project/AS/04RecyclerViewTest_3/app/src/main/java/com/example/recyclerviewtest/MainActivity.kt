package com.example.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var fruitList: MutableList<Fruit> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        瀑布流布局
        initFruits()
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(fruitList)
        recyclerView.adapter = adapter

//        纵向 & 横向滚动
////      1.初始化数据源
//        initFruits()
////      2.设置RecyclerView的layoutManager
//        val layoutManager = LinearLayoutManager(this)
//
////        加入此行代码 & fruit_item的纵向布局来实现横向滚动
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//
//        recyclerView.layoutManager = layoutManager
////      3.设置RecyclerView的adapter
//        val adapter: RecyclerView.Adapter<FruitAdapter.ViewHolder> = FruitAdapter(fruitList)
//        recyclerView.adapter = adapter
    }

//    private fun initFruits() {
//        repeat(2) {
//            fruitList.add(Fruit("Apple", R.drawable.apple_pic))
//            fruitList.add (Fruit ( "Banana", R.drawable.banana_pic))
//            fruitList.add (Fruit ("Orange ",R.drawable.orange_pic))
//            fruitList.add (Fruit ("Watermelon",R.drawable.watermelon))
//            fruitList.add (Fruit( "Pear",R.drawable.pear_pic))
//            fruitList.add (Fruit ( "Grape",R.drawable.grape_pic))
//            fruitList.add (Fruit ("Pineapple",R.drawable.pineapple_pic))
//            fruitList.add (Fruit ("Strawberry",R.drawable.strawberry_pic))
//            fruitList.add (Fruit ("Cherry", R.drawable.cherry_pic))
//            fruitList.add (Fruit ( "Mango",R.drawable.mango_pic))
//        }
//    }

    private fun initFruits() {
        repeat(2) {
            fruitList.add(Fruit(getRandomLengthString("Apple"), R.drawable.apple_pic))
            fruitList.add (Fruit (getRandomLengthString("Banana"), R.drawable.banana_pic))
            fruitList.add (Fruit (getRandomLengthString("Orange "),R.drawable.orange_pic))
            fruitList.add (Fruit (getRandomLengthString("Watermelon"),R.drawable.watermelon))
            fruitList.add (Fruit(getRandomLengthString("Pear"),R.drawable.pear_pic))
            fruitList.add (Fruit (getRandomLengthString("Grape"),R.drawable.grape_pic))
            fruitList.add (Fruit (getRandomLengthString("Pineapple"),R.drawable.pineapple_pic))
            fruitList.add (Fruit (getRandomLengthString("Strawberry"),R.drawable.strawberry_pic))
            fruitList.add (Fruit (getRandomLengthString("Cherry"), R.drawable.cherry_pic))
            fruitList.add (Fruit (getRandomLengthString("Mango"),R.drawable.mango_pic))
        }
    }

    private fun getRandomLengthString(str: String) : String {
        val n = (1..20).random()
        val builder = StringBuilder()
        repeat(n) {
            builder.append(str)
        }
        return builder.toString()
    }
}