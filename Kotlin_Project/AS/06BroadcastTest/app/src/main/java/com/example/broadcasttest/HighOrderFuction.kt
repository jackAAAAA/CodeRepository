package com.example.broadcasttest

fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
    val result: Int = operation(num1, num2)
    return result
}

fun plus(num1: Int, num2: Int): Int {
    return num1 + num2
}

fun minus(num1: Int, num2: Int): Int {
    return num1 - num2
}

fun StringBuilder.build(block: StringBuilder.() -> Unit): StringBuilder {
    block()
    return this
}

fun main() {
//    2.扩展函数 & 高阶函数
    val list = listOf("Apple", "Orange", "Banana", "Grape", "Pear")
    val builder = StringBuilder()
    val result = builder.build {
        append("Start eating fruits:\n")
        for (fruit in list) {
            append("$fruit\n")
        }
        append("Eaten all fruits!")
    }
    println(result.toString())

//    1.高阶函数测试
//    val num1 = 100
//    val num2 = 80
////    val result1 = num1AndNum2(num1, num2, ::plus)
////    val result2 = num1AndNum2(num1, num2, ::minus)
//    val result1 = num1AndNum2(num1, num2) { num1, num2 ->
//        num1 + num2
//    }
//    val result2 = num1AndNum2(num1, num2) { num1, num2 ->
//        num1 - num2
//    }
//    println("result1 is $result1\nresult2 is $result2")
}