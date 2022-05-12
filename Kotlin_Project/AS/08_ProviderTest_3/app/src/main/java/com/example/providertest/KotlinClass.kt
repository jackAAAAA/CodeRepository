package com.example.providertest




//fun <T> T.build(block: T.() -> Unit): T {
//    block()
//    return this
//}
//向上扩展成泛型就可以作用在所有类的上面
//||
//fun StringBuilder.build(block: StringBuilder.() -> Unit): StringBuilder {
//    block()
//    return this
//}

//注意：
//1.泛型类表示传入该类的泛型，而泛型方法一般就是指定该方法的传参和返回值为泛型
//2.泛型的表示符号<>只是表示该泛型而已
//3.限定泛型类型
class MyClass2 {
    fun <T : Number> method(param: T): T {
        return param
    }
}

val myClass2 = MyClass2()
val result2 = myClass2.method<Int>(124)

//2.非泛型类而是泛型方法
class MyClass1 {
    fun <T> method(param: T): T {
        return param
    }
}

val myClass1 = MyClass1()
//val result1 = myClass1.method<Int>(123)
val result1 = myClass1.method(123)

//1.泛型类
class MyClass<T> {
    fun method(param: T): T {
        return param
    }
}

val myClass = MyClass<Int>()
val result = myClass.method(123)
