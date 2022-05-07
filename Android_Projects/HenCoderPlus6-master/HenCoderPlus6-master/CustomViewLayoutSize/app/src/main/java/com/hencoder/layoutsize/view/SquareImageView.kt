package com.hencoder.layoutsize.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

class SquareImageView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

//    设备屏幕的宽和高可以通过onMeasure中的measuredWidth、measuredHeight拿到
    val size = min(measuredWidth, measuredHeight)

    println("height: $measuredHeight, width: $measuredWidth")

    setMeasuredDimension(size, size)
//    setMeasuredDimension(measuredWidth, measuredHeight)
  }
}