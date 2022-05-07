import kotlin.math.max


//12. 默认值（主构造函数采用的传参采用默认值会有多个构造函数）
fun main() {
    val s1 = Student(name = "")
    val s2 = Student("666", 5, "Yp")
    val s3 = Student("123", 3, "Jack", 19)
    s1.eat()
    s2.eat()
    s3.eat()
}

//11. 判空 let函数
//fun main() {
////    println(getTextLength("String"))
////    printParams(123)
//    printParams(str = "Hello")
//}
//
//fun printParams(num: Int = 123, str: String) {
//    println("num is $num, str is $str")
//}

//fun printParams(num: Int, str: String = "Hello, world!") {
//    println("num is $num, str is $str")
//}

//fun getTextLength(text: String?) = text?.length ?: 0
//
//var study: Study? = null
//
//fun doStudy() {
//    study?.let {
//        it.readBooks()
//        it.doHomework()
//    }
//}

//10. Thread
//fun main() {
////    Thread(object : Runnable {
////        override fun run() {
////            println("Thread is running")
////        }
////    }).start()
//
////    Thread(Runnable {
////        println("Thread is running")
////    }).start()
//
////    Thread({
////        println("Thread is running")
////    }).start()
//
////    Thread {
////        println("Thread is running")
////    }.start()
//}

//9. lsit set map
//fun main() {
////    val map = mapOf("Apple" to 1, "Banana" to 2,
////    "Orange" to 3, "Pear" to 4, "Grape" to 5)
////    for ((fruit, number) in map) {
////        println("fruit is $fruit, number is $number")
////    }
//
////    val map = HashMap<String, Int>()
////    map["Apple"] = 1
////    map["Banana"] = 2
////    map["Watermelon"] = 3
////    map["Orange"] = 4
////    map["Pear"] = 5
////    map["Grape"] = 6
//
////    val set = mutableSetOf("Apple", "Banana", "Orange", "Pear", "Grape")
////    set.add("Watermelon")
////    for (fruit in set) {
////        println(fruit)
////    }
//
////    val list = mutableListOf("Apple", "Banana", "Orange", "Pear", "Grape")
////    list.add("Watermelon")
////    for (fruit in list) {
////        println(fruit)
////    }
//
//    val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape")
////    var maxLengthFruit = ""
////    for (fruit in list) {
////        if (fruit.length > maxLengthFruit.length) {
////            maxLengthFruit = fruit
////        }
////    }
////    val lambda = { fruit: String -> fruit.length}
////    val maxLengthFruit = list.maxBy(lambda)
////    val maxLengthFruit = list.maxBy({ fruit: String -> fruit.length })
////    val maxLengthFruit = list.maxBy() { fruit: String -> fruit.length }
////    val maxLengthFruit = list.maxBy { fruit: String -> fruit.length }
////    val maxLengthFruit = list.maxBy { it.length}
////    println("max length fruit is $maxLengthFruit")
//
////    val newList = list.map() { fruit -> fruit.toUpperCase()}
////    val newList = list.map { it.toUpperCase()}
////    val newList = list.filter { it.length <= 5 }.map { it.toUpperCase() }
//
////    for (fruit in newList) {
////        println(fruit)
////    }
//
//    val anyResult = list.any { it.length <= 5 }
//    val allResult = list.all { it.length <= 5 }
//    println("anyResult is $anyResult, allResult is $allResult")
//}

//8. Singleton
//fun main() {
//    Singleton.singletonTest()
//}

//7. data class
//fun main() {
//    val cellphone1 = Cellphone("Samsung", 1299.99)
//    val cellphone2 = Cellphone("Samsung", 1299.99)
//
//    println(cellphone1)
//    println("cellphone equals cellphone2 " + (cellphone1 == cellphone2))
//}

//6. readBooks & doHomework
//fun main() {
//    val student = Student("Jack", 19)
//    doStudy(student)
//}
//
//fun doStudy(study: Study) {
//    study.readBooks()
//    study.doHomework()
//}

//5. main & sub constructor
//fun main() {

//    val s1 = Student("001", 100, "Jack", 19)
//    val s2 = Student("Jack", 19)
//    val s3 = Student()
//    s2.eat()
//    s3.eat()

//    val s: Student = Student("001", 100)

//    val s: Student = Student("001", 100)
//    println(s.sno)
//    println(s.grade)

//    val p = Person()
//    p.name = "Jack"
//    p.age = 19
//    p.eat()
//}

//4. for
//decrease
//[10, 1]
//fun main() {
//    for (i in 10 downTo 1 step 2) {
//        println(i)
//    }
//}

//Increase
//[0, 10)
//fun main() {
//    for (i in 0 until 10 step 2) {
//        println(i)
//    }
//}

//[0,10]
//fun main() {
//    for (i in 0..10) {
//        println(i)
//    }
//}

//3. when & startsWith
//fun main() {
//    val a: Int = getScore("Tom")
//    println(a)
//    checkNumber(a)
//    val b: Long = 10L
//    checkNumber(b)
//}
//
//fun checkNumber(num: Number) {
//    when (num) {
//        is Int -> println("number is Int")
//        is Double -> println("number is Double")
//        else -> println("number not support")
//    }
//}
//
//fun getScore(name: String) = when {
//    name.startsWith("Tom") -> 86
//    name == "Jim" -> 77
//    name == "Jack" -> 95
//    name == "Lily" -> 100
//    else -> 0
//}

//fun getScore(name: String) = when {
//    name == "Tom" -> 86
//    name == "Jim" -> 77
//    name == "Jack" -> 95
//    name == "Lily" -> 100
//    else -> 0
//}

//fun getScore(name: String) = when (name) {
//    "Tom" -> 86
//    "Jim" -> 77
//    "Jack" -> 95
//    "Lily" -> 100
//    else -> 0
//}

//2. max & if_else
//fun main() {
//    println("max = " + largerNumber(1000, 100000))
//}
//
//fun largerNumber(num1: Int, num2: Int): Int =
//        if (num1 > num2) num1 else num2

//fun largerNumber(num1: Int, num2: Int): Int = if (num1 > num2) {
//    num1
//} else {
//    num2
//}

//fun largerNumber(num1: Int, num2: Int): Int = max(num1, num2)

//fun largerNumber(num1: Int, num2: Int): Int {
//    return max(num1, num2)
//}

// 1. var
// fun main() {
//     var a: Int = 10
//     a *= 10
//     println("a = " + a)
// }