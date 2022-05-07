package com.jack.kotlin.helloobj

import java.sql.DriverManager.println

class Person(val name: String) {
    fun printName() {
        println(name)
    }
}