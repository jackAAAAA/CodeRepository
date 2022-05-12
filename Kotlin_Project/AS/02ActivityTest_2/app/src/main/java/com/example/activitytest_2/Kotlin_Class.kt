package com.example.activitytest_2

import androidx.core.view.GravityCompat.apply

//Kotlin课堂
fun main() {
    val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape")

//    4.apply
//    4.1语法
//    val result = obj.apply {
////        这里是obj的上下文
//    }
//    result = obj
//    4.2写法
    val result = StringBuilder().apply {
        append("Starting eating fruits: \n")
        for (fruit in list) {
            append(fruit).append("\n")
        }
        append("Ate all fruits!\n")
    }

//    3.run
//    3.1语法
//    val result = obj.run {
////        这里是obj的上下文
//        “value” //run的返回值
//    }
//    3.2写法
//    val result = StringBuilder().run {
//        append("Starting eating fruits: \n")
//        for (fruit in list) {
//            append(fruit).append("\n")
//        }
//        append("Ate all fruits!\n")
//        toString()
//    }

//    2.with
//    2.1语法
//    val result = with(obj) {
////        这里是obj的上下文
////        "value" //with的返回值
//    }
//    val result = with(StringBuilder()) {
//        append("Starting eating fruits: \n")
//        for (fruit in list) {
//            append(fruit).append("\n")
//        }
//        append("Ate all fruits!\n")
//        toString()
//    }

//    1.常规写法
//    val builder: StringBuilder = StringBuilder()
//    builder.append("Starting eating fruits: \n")
//    for (fruit in list) {
//        builder.append(fruit).append("\n")
//    }
//    builder.append("Ate all fruits!\n")
//    val result: String = builder.toString()

    println(result.toString())
}