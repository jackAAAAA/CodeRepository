package com.hencoder.generics

import com.hencoder.generics.fruit.Apple

//泛型接口
interface Interface_Apple<T : Apple> {
    fun buy() : T
    fun refund(item : Apple) : Float
}