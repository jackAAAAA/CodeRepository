package com.example.fragmentbesetpractice

object StringUtil {

    fun lettersCount(str: String) : Int {
        var cnt: Int = 0
        for (char in str) {
            if (char.isLetter()) {
                ++cnt;
            }
        }
        return cnt
    }

//    fun lettersCount(str: String) : Int{
//        var cnt = 0
//        for (c in str) {
//            if (c.isLetter()) {
//                ++cnt
//            }
//        }
//        return cnt
//    }
}