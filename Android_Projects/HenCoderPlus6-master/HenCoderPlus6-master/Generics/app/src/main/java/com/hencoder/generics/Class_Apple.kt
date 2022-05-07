package com.hencoder.generics

import com.hencoder.generics.fruit.Apple

//泛型类型
class Class_Apple<T> {
    fun buy() : T {
        return 0 as T
    }
    fun refund(): Float {
        return 1f
    }
}