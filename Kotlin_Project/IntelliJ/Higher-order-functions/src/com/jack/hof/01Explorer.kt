package com.jack.hof


//3.带参数和返回值的函数，作为形参
fun main(args: Array<String>) {
    action(2) {it-> //Lambda语法，回调参数在这里
        println("回调函数参数= : $it")
//        true//Lambda语法，最后一行返回值就是闭包的返回值
        false
    }
//    action(first = 1)
}

/**
 * 函数作为 形参
 */
fun action(first:Int, callback:(Int)->Boolean){
    //调用
//    TO DO辨析这两种写法 20 & 26行的写法
    if(callback(first)){
        println("回调函数返回值 true")
    }else{
        println("回调函数返回值 false")
    }

//    if(callback(1)){
//        println("回调函数返回值 true")
//    }else{
//        println("回调函数返回值 false")
//    }
}

//2.不带参数和返回值的函数 作为形参
//fun say() {
//    println("Hello World")
//}
//
///**
// * 在 Kotlin 中无返回为 Unit
// *
// * 此方法接收一个无参数的函数并且无返回
// *
// * 使用参数名加 () 来调用
// */
//fun people(hello: () -> Unit) {
//    hello()
//}
//
///**
// * 在 kotlin 中有一个约定，如果最后一个参数是函数，可以省略括号
// */
//fun main() {
//    people({ say() })
//    people { say() }
//}

//1.带参数但不带返回值的函数 作为形参
//fun say(msg: String) {
//    println("Hello $msg")
//}
//
//val say: (msg: String) -> Unit = {
//    println("Hello $it")
//}

/**
 * 当调用的函数有形参时，
 * 需要在调用的函数声明，并使用声明的形参；
 * 函数参数中的形参无法使用
 */
//fun people(arg0: String, hello: (arg1: String) -> Unit) {
//    hello(arg0)
//    // hello(arg1) 这样调用将报错
//}

//fun main() {
////    以下六种写法都可以都可以将高阶函数声明的传参传给函数
////    使用双冒号才能调用此函数fun say
////    people("Android", ::say)
//
////    直接使用变量名称say，就可以调用val say
////    people("Android", say)
////    people("Android", {say("$it")})
//
////    lambda 闭包写在函数体外部，形参中的最后一个形参是函数参数
////    people("Android") {say("$it")}
//    people("Android") {say(it)}
////    people("Android", {println("Hello $it")})
//}

