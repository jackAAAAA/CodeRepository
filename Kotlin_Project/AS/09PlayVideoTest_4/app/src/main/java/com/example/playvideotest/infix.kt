package com.example.playvideotest

//1.to并不是Kotlin语言中的一个关键字，之所以我们能够使用A to B这样的语法结构，
// 是因为Kotlin提供了一种高级语法糖特性：infix函数。
//2.nfix函数允许我们将函数调用时的小数点、括号等计算机相关的语法去掉，
//从而使用一种更接近英语的语法来编写程序，让代码看起来更加具有可读性。
//3.infix函数常用于定义某个类的扩展函数且只能接收一个参数
infix fun String.beginsWith(prefix: String) = startsWith(prefix)

infix fun <T> Collection<T>.has(element: T) = contains(element)

infix fun <A, B> A.with(that: B): Pair<A, B> = Pair(this, that)

fun main() {

//    2. to <=> with
    val map = mapOf("Apple" to 1, "Banana" to 2, "Grape" to 4)
    val map1 = mapOf("Apple" with 1, "Banana" with 2, "Grape" with 4)
    println("This is a map:")
    for (a in map) {
        println("key: ${a.key}; value: ${a.value}")
    }
    println("This is a map1:")
    for (a in map1) {
        println("key: ${a.key}; value: ${a.value}")
    }

//    1. contains <=> has
//    val list = listOf("Apple", "Banana", "Orange", "Grape", "Pear")
//    if (list has "Banana") {
//        println("香蕉")
//    }
}