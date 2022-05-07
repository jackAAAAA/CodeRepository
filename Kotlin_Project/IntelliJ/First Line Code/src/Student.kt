
class Student(val sno: String = "", val grade:
Int = 0, name: String = "", age: Int = 0) : Person(name, age) {
}


//6. interface
//class Student(name: String, age: Int) : Person
//(name, age), Study {
//    override fun readBooks() {
//        println("$name is reading.")
//    }
//
////    override fun doHomework() {
////        println("$name is doing homework.")
////    }
//}

//5. only sub constructor
//class Student : Person {
//    constructor(name: String, age: Int) : super(name, age)
//
//    constructor() : this("c1", 0)
//}

//4. main & sub constructor
//class Student(val sno: String, val grade: Int,
//              name: String, age: Int) : Person(name, age) {
//    init {
//        println("sno is $sno")
//        println("grade is $grade")
//    }
//
//    constructor(name: String, age: Int) : this("c2", 0, name, age)
//
//    constructor() : this("c1", 0)
//}

//3. init
//class Student(val sno: String, val grade: Int) : Person() {
//    init {
//        println("sno is $sno")
//        println("grade is $grade")
//    }
//}

//1. & 2.的构造函数写法表达的含义是一致的
//2.
//class Student(val sno: String, val grade: Int) : Person() {
//}
//
//1.
//class Student : Person() {
//    val sno = ""
//    val grade = 0
//}