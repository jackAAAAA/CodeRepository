package com.example.fragmentbesetpractice

fun String.lettersCount() : Int{
    var cnt = 0
    for (char in this) {
        if (char.isLetter()) {
            ++cnt;
        }
    }
    return cnt
}

fun main() {
//    val str = "123H$%^&#$@abc"
//    println(StringUtil.lettersCount(str))
//    println(str.lettersCount())

//    val money1 = Money(4)
//    val money2 = Money(6)
//    val money3 = money1 + money2

    val money1 = Money(4)
    val money3 = money1 + 6

    println(money3.value)

}
class Money(val value: Int) {
    operator fun plus(money: Money) : Money {
        val sum = value + money.value
        return Money(sum)
    }

    operator fun plus(value: Int): Money {
        val sum = this.value + value
        return Money(sum)
    }
}
