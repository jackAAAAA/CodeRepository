package com.example.app.entity;


import com.example.app.samples.View;
import com.example.app.widget.CodeView;
import com.example.core.BaseApplication;
import com.example.core.utils.Utils;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class A {
    public static void main(String[] args) {
//        BaseApplication.currentApplication();
//        BaseApplication.Companion.currentApplication();
        BaseApplication.Companion.getCurrentApplication();
        Utils.INSTANCE.toast("dd");
        Utils.INSTANCE.toast("dd", 500);
//        CodeView codeView = new CodeView(this);
        new View().setOnClickListener(new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                return null;
            }
        });
    }
}
