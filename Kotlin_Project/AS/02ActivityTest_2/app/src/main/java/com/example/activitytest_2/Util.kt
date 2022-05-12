package com.example.activitytest_2

class Util {
    fun doAction1() {
        println("do Action1")
    }

    companion object {
        @JvmStatic
        fun doAction2() {
            println("do Action2")
        }
    }

}