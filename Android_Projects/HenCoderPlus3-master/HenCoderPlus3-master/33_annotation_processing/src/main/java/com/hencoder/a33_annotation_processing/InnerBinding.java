package com.hencoder.a33_annotation_processing;

public class InnerBinding {
    public static void bind(MainActivity activity) {
        activity.textView = activity.findViewById(R.id.textView);
    }
}
