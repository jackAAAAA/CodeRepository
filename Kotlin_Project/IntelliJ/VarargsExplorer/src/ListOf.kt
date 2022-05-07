// Varargs/MainArgConversion.kt
//fun main(args: Array<String>) {
//    if (args.size < 3) return
//    val first = args[0]
//    val second = args[1].toInt()
//    val third = args[2].toFloat()
//    println("$first  $second  $third")
//}

// Varargs/MainArgs.kt
//fun main(args: Array<String>) {
//    for (a in args) {
//        println(a)
//    }
//}

// Varargs/SpreadOperator.kt
//fun main() {
//    val array = intArrayOf(4, 5)
//    sum(1, 2, 3, *array, 6) eq 21  // [1]
//    // Doesn't compile:
//    // sum(1, 2, 3, array, 6)
//
//    val list = listOf(9, 10, 11)
//    sum(*list.toIntArray()) eq 30  // [2]
//}

// Varargs/TwoFunctionsWithVarargs.kt
//fun first(vararg numbers: Int): String {
//    var result = ""
//    for (i in numbers) {
//        result += "[$i]"
//    }
//    return result
//}
//
//fun second(vararg numbers: Int) =
//        first(*numbers)
//
//fun main() {
//    println(second(7, 9, 32))
////    println(first(8, 10, 11))
//}

// Varargs/VarargLikeList.kt
//fun evaluate(vararg ints: Int) =
//        "Size: ${ints.size}\n" +
//                "Sum: ${ints.sum()}\n" +
//                "Average: ${ints.average()}"
//
//fun main() {
//    println(evaluate(10, -3, 8, 1, 9))
//}

// Varargs/VarargSum.kt
//fun sum(vararg numbers: Int): Int {
//    var total = 0
//    for (n in numbers) {
//        total += n
//    }
//    return total
//}
//
//fun main() {
//    println(sum(13, 27, 44))
//    println(sum(1, 3, 5, 7, 9, 11))
//    println(sum())
//}

fun main(args: Array<String>) {
    invokMsg("AAA", "BBB", "CCC", "DDD")
}

fun invokMsg(vararg value: Any?){
    printlnMsg(value)
    printlnMsg(*value)
}

fun printlnMsg(vararg msg: Any?){
    println(msg.joinToString(" ", "test_prefix ", " post" +
            "fix" ))
}