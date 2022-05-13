package com.example.materialtest

import java.lang.RuntimeException

fun main() {
    val a = 3.5
    val b = 3.8
    val c = 4.1
    val d = 5.9
//    val largest = max(a, b, c, d)
//    println(largest)
    val smallest = min(a, b, c, d)
    println(smallest)
}

//工具函数：在N个数（double float long int short）据求最大值
fun <T : Comparable<T>> max(vararg nums: T): T {
    if (nums.isEmpty()) throw RuntimeException("Params can not be empty.")
    var maxNum = nums[0]
    for (num in nums) {
        if (num > maxNum) {
            maxNum = num
        }
    }
    return maxNum
}

fun <T : Comparable<T>> min(vararg nums: T): T {
    if (nums.isEmpty()) throw RuntimeException("Params can not be empty.")
    var minNum = nums[0]
    for (num in nums) {
        if (num < minNum) {
            minNum = num
        }
    }
    return minNum
}