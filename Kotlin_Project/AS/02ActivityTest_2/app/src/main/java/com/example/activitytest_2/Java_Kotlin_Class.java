package com.example.activitytest_2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

public class Java_Kotlin_Class {
    public static void main(String[] args) {
        List<String> list = CollectionsKt.listOf("Apple", "Banana", "Orange", "Pear", "Grape");
        StringBuilder builder = new StringBuilder();
        builder.append("Starting eating fruits: \n");

        String result;
        for (String mem : list) {
            builder.append(mem).append("\n");
        }

        builder.append("Ate all fruits!\n");
        String var10000 = builder.toString();
        Intrinsics.checkNotNullExpressionValue(var10000, "builder.toString()");
        result = var10000;
        boolean var4 = false;
        System.out.println(result);
//        Java中调用顶层方法
        HelperKt.doSomething();
    }
}
