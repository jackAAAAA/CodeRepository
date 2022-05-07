package com.hencoder.text

import android.content.res.Resources
import android.util.TypedValue

//把dp转化成px
val Float.dp
 get() = TypedValue.applyDimension(
   TypedValue.COMPLEX_UNIT_DIP,
   this,
   Resources.getSystem().displayMetrics
 )

val Int.dp
 get() = this.toFloat().dp

val Double.dp
 get() = this.toFloat().dp