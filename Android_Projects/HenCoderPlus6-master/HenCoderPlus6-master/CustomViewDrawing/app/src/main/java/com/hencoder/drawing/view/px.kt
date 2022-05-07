package com.hencoder.drawing.view

import android.content.res.Resources
import android.util.TypedValue

//不能用Int，只能用Float
//applyDimension(int unit, float value, DisplayMetrics metrics)
//val Int.px
// get() = TypedValue.applyDimension(
//   TypedValue.COMPLEX_UNIT_DIP,
//   this,
//   Resources.getSystem().displayMetrics
// )