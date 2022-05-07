package com.jack.extension.kotlin

//2.只含有get()的扩展属性只能被声明为 val；除了get()方法之外还有set()方法的话，必须声明为var
val String.lastChar: Char
    get() = get(length - 1)

var StringBuilder.lastChar: Char
//以下两种get()的写法是等价的
//    get() {
//        return get(length - 1)
//    }
    get() = get(length - 1) //返回的Char
    set(value) { //返回的是Unit
        this.setCharAt(length -1, value)
    }

fun main(args: Array<String>) {
    println("Kotlin".lastChar)
    val sb = StringBuilder("Kotlin")
    sb.lastChar = 'g'
    println(sb)
}

//1.调用一个扩展函数并没有涉及对象的创建或者其他运行时开销，在底层，一个扩展函数是一个接收器对象作为第一个参数的静态方法
//open class View{
//    open fun click() {
//        println("view clicked")
//    }
//}
//
//open class Button: View() {
//    override fun click(){
//        println("button clicked")
//    }
//}
//
//fun View.longClick() = println("view longClicked")
//fun Button.longClick(name: String) = println("button longClicked$name")
//
//fun main(args: Array<String>) {
////    扩展函数是静态解析的：在调用扩展函数时，具体被调用的的是哪一个函数？
////    由调用函数的的对象表达式来决定的（1.当把父类引用用子类对象来实现之时，要具体看该实例的类型属于哪个类，就调用那个类的同名扩展函数；
////    2.当一个函数基于父类作为传参之时，但是传入子类的对象实例，那么调用的也是父类的同名扩展函数），而不是动态的类型决定的
////    val button:com.jack.extension.kotlin.View = com.jack.extension.kotlin.Button()
////    button.click()
////    button.com.jack.extension.kotlin.longClick()
//    val button: Button = Button()
//    button.click()
//    button.longClick()
//}