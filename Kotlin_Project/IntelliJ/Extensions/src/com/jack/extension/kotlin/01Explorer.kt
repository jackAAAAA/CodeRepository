package com.jack.extension.kotlin//Re: https://www.runoob.com/kotlin/kotlin-extensions.html

//9.扩展声明为成员
//9.1扩展方法所在类的实例被称为分发接受者；而被扩展的目标类的实例被称为扩展接受者
//9.2以成员的形式定义的扩展函数, 可以声明为 open , 而且可以在子类中覆盖
// （被关键字open修饰了的类和方法才可以被重写）。
// 也就是说, 在这类扩展函数的派发过程中, 针对分发接受者是虚拟的(virtual，此处理解为Java的abstract)（即可以被重写覆盖）, 但针对扩展接受者仍然是静态的（此处理解为：即调用
// 父类的扩展函数但传参为子类实例之时，返回的是父类的扩展函数）。
//9.3假如在调用某一个函数，而该函数在分发接受者和扩展接受者均存在，则以扩展接收者优先。
//9.4在扩展函数当中可以调用分发接受者的成员函数，要指定引用分发接收者的成员需使用限定的 this@ 语法，
// 否则（不添加this@的话）优先使用扩展接受者中的同名函数（如果在扩展接受者中存在同名函数的话）。
//eg3:
//open class D {
//}
//
//class D1 : D() {
//}
//C为分发接受者
//D和D1为扩展接受者
//open class C {
//    open fun D.foo() {
//        println("D.foo in C")
//    }
//
//    open fun D1.foo() {
//        println("D1.foo in C")
//    }
//
//    fun caller(d: D) {
//        d.foo()
//    }
//}
//
//class C1 : C() {
//    override fun D.foo() {
//        println("D.foo in C1")
//    }
//
//    override fun D1.foo() {
//        println("D1.foo in C1")
//    }
//}
//
//fun com.jack.extension.kotlin.main(args: Array<String>) {
//    C().caller(D())
//    C1().caller(D())
//    C().caller(D1())
//}

//eg2:
//class D {
//    fun bar() {
//        println("D bar")
//    }
//}
//
//class C {
//    fun bar() {
//        println("C bar")
//    }
//
//    fun D.foo() {
////        假如在调用某一个函数，而该函数在分发接受者和扩展接受者均存在，则以扩展接收者优先。
//        bar()
////        在扩展函数当中可以调用分发接受者的成员函数，要引用分发接收者的成员使用限定的 this@ 语法
//        this@C.bar()
//    }
//
//    fun caller(d: D) {
//        d.foo()
//    }
//}
//
//fun com.jack.extension.kotlin.main(args: Array<String>) {
//    val c: C = C()
//    val d: D = D()
//    c.caller(d)
//}

//eg1:
//被关键字open修饰了的类才可以被继承
//open class D {
//    fun bar() { println("D bar") }
//}
//
//class C {
//    fun baz() { println("C baz") }
//
//    fun D.foo() {
//        bar()   // 调用 D.bar
//        baz()   // 调用 C.baz
//    }
//
//    fun caller(d: D) {
//        d.foo()   // 调用扩展函数
//    }
//}
//
//fun com.jack.extension.kotlin.main(args: Array<String>) {
//    val c: C = C()
//    val d: D = D()
//    c.caller(d)
//}

//8.扩展的作用域
//通常扩展函数或属性定义在顶级包下
//要使用所定义包之外的一个扩展，通过import导入扩展的函数名进行使用
//import foo.bar.goo // 导入所有名为goo的扩展
//import foo.bar.* // 从foo.bar导入一切

//7.伴生对象的扩展
//7.1伴生对象内的成员相当于 Java 中的静态成员，其生命周期伴生类始终；
//7.2在伴生对象内部可以定义变量和函数，这些变量和函数可以直接用类名引用；
//7.3如果一个类定义有一个伴生对象 ，也可以为伴生对象定义扩展函数和属性；
//7.4对于伴生对象扩展函数，有两种形式，一种是在类内扩展，一种是在类外扩展；
//这两种形式扩展后的函数互不影响（甚至名称都可以相同），即使名称相同，它们也完全是两个不同的函数，并且有以下特点：
// （1）类内扩展的伴生对象函数和类外扩展的伴生对象可以同名，它们是两个独立的函数，互不影响；
// （2）当类内扩展的伴生对象函数和类外扩展的伴生对象同名时，类内的其它函数优先引用类内扩展的伴生对象函数，即对于类内其它成员函数来说，类内扩展屏蔽类外扩展；
// （3）类内扩展的伴生对象函数只能被类内的函数引用，不能被类外的函数和伴生对象内的函数引用；
// （4）类外扩展的伴生对象函数可以被伴生对象内的函数引用
//7.5伴生对象通过"类名."形式来调用；
//7.6伴生对象声明的扩展函数，也通过用"类名."来调用。
//eg1:
//class MyClass {
//    companion object {
//        var myClassField1: Int = 1
//        var myClassField2 = "this is myClassField2"
//
//       fun companionFun1() {
//            println("this is 1st companion function.")
////            类外扩展的伴生对象函数可以被伴生对象内的函数引用
////            注意：类内扩展的伴生对象函数 只能 被类内的函数引用，即
////            1.不能被类外的函数引用；
////            2.不能被伴生对象内的函数引用，比如如下即为类外的扩展函数foo()
//            MyClass.foo() //或者通过foo()来调用。此foo为类外扩展函数，非类内伴生对象的扩展函数
//        }
//
//        fun companionFun2() {
//            println("this is 2st companion function.")
//            companionFun1()
//        }
//    }
//
////    当类内扩展的伴生对象函数和类外扩展的伴生对象同名时，类内的其它函数优先引用类内扩展的伴生对象函数，即对于类内其它成员函数来说，类内扩展屏蔽类外扩展；
//    fun MyClass.Companion.foo() {
//        println("伴生对象的扩展函数（内部）")
//    }
//
//    fun test2() {
//        MyClass.foo()
//    }
//
//    init {
//        test2()
//    }
//}
//
//val MyClass.Companion.no: Int
//    get() = 10
//
//fun MyClass.Companion.foo() {
//    println("伴生对象外部扩展函数")
//}
//
//fun com.jack.extension.kotlin.main(args: Array<String>) {
//    println("no:${MyClass.no}")
//    println("filed1:${MyClass.myClassField1}")
//    println("filed2:${MyClass.myClassField2}")
////    类内扩展的伴生对象函数只能被类内的函数引用，不能被类外的函数和伴生对象内的函数引用；
////    此处调用的是类外扩展的foo()
//    MyClass.foo()
//    MyClass.companionFun2()
//}

//eg2:
//class MyClass {
//    companion object {}
//}
//
//fun MyClass.Companion.foo() {
//    println("伴生对象的扩展函数")
//}
//
//val MyClass.Companion.no: Int
//    get() = 10
//
//fun com.jack.extension.kotlin.main(args: Array<String>) {
//    println("no:${MyClass.no}")
//    MyClass.foo()
//}

//6.扩展属性
//扩展属性允许定义在类或者kotlin文件中，不允许定义在函数中
//扩展属性不能被初始化
//只含有get()的扩展属性只能被声明为 val；除了get()方法之外还有set()方法的话，必须声明为var
//val <T> List<T>.lastIndex: Int
//    get() = size - 1
//
//fun com.jack.extension.kotlin.main(arg: Array<String>) {
////    val list1 = listOf(1, 2, 3, 5)
//    val list2 = listOf("String", "test", "aaa")
//    println(list2.lastIndex)
//}

//5.扩展一个空对象
//在扩展函数内，可以通过 this 来判断接收者是否为 NULL；
// 这样，即使接收者为 NULL，也可以调用扩展函数。
//fun Any?.toString(): String {
//    if (this == null) {
//        return "null"
//    }
//    return toString()
//}
//
//fun com.jack.extension.kotlin.main(arg: Array<String>) {
//    var t = null
//    println(t.toString())
//}

//4.若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数。
//class C {
//    fun foo() {
//        println("成员函数")
//    }
//}
//
//fun C.foo() {
//    println("扩展函数")
//}
//
//fun com.jack.extension.kotlin.main(arg: Array<String>) {
//    var c = C()
//    c.foo()
//}

//3.扩展函数是静态解析的
//扩展函数是静态解析的，并不是接收者类型的虚拟成员；
// 在调用扩展函数时，具体被调用的的是哪一个函数：
// 1.由调用函数的对象表达式来决定的（此处理解为：即调用
// 父类的扩展函数但传参为子类实例之时，返回的是父类的扩展函数）；
// 2.而不是动态的类型决定的（而不是由调用处传入的具体子类实例传参类型决定）。
//open class C
//
//class D: C()
//
//fun C.foo() = "c"
//
//fun D.foo() = 'd'
//
//fun printFoo(c: C) {
//    println(c.foo())
//}
//
//fun com.jack.extension.kotlin.main(arg: Array<String>) {
//    printFoo(D())
//}

//2.为MutableList添加一个swap函数
//fun MutableList<Int>.swap(index1: Int, index2: Int) {
//this关键字指代接收者对象(receiver object)(也就是调用扩展函数时, 在点号之前指定的对象实例)。
//    val tmp = this[index1]
//    this[index1] = this[index2]
//    this[index2] = tmp
//}
//
//fun com.jack.extension.kotlin.main(args: Array<String>) {
//    val l = mutableListOf(1, 2, 3)
//    l.swap(0, 2)
//    println(l.toString())
//}

//1.扩展函数示例
//class User(var name: String)
//
//fun User.Print() {
//    print("用户名 $name")
//}
//
//fun com.jack.extension.kotlin.main(arg: Array<String>) {
//    var user = User("Hello name!")
//    user.Print()
//}

//0.扩展函数的定义
//扩展函数可以在已有类中添加新的方法，不会对原类做修改，形式为：
//fun receiverType.functionName(params) {
//    body
//}
//receiverType: 表示函数的接受者，也就是函数扩展的对象
//functionName: 扩展函数的名称
//params: 扩展函数的参数，可以为NULL
