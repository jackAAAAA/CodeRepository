package com.hencoder.xfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.xfermode.R
import com.hencoder.xfermode.px

private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.DARKEN)

//图像——实操PorterDuff.Mode的合成图像
//相较于AvatarView：XfermodeView特点在于绘制了背景，但AvatarView并没有绘制
//TODO待优化：应该把”定位 & 定形“尺寸用变量提取出来
class XfermodeView(context: Context?, attrs: AttributeSet?) :
  View(context, attrs) {
//  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val paint = Paint()
//  bounds代表离屏缓冲的区域范围，既有定位尺寸也有定形尺寸
  private val bounds = RectF(150f.px, 50f.px, 300f.px, 200f.px)
//  circleBitmap的定形尺寸
  private val circleBitmap = Bitmap.createBitmap(150f.px.toInt(), 150f.px.toInt(), Bitmap.Config.ARGB_8888)
//  squareBitmap的定形尺寸
  private val squareBitmap = Bitmap.createBitmap(150f.px.toInt(), 150f.px.toInt(), Bitmap.Config.ARGB_8888)

  init {
    val canvas = Canvas(circleBitmap)
    paint.color = Color.parseColor("#D81B60")
//    这里left、top、right、bottom相较于的是circleBitmap，既有定位尺寸也有定形尺寸
    canvas.drawOval(50f.px, 0f.px, 150f.px, 100f.px, paint)
    paint.color = Color.parseColor("#2196F3")
    canvas.setBitmap(squareBitmap)
//    这里left、top、right、bottom相较于的是squareBitmap，既有定位尺寸也有定形尺寸
    canvas.drawRect(0f.px, 50f.px, 100f.px, 150f.px, paint)
  }

  override fun onDraw(canvas: Canvas) {
    val count = canvas.saveLayer(bounds, null)
//  circleBitmap的定位尺寸
    canvas.drawBitmap(circleBitmap, 150f.px, 50f.px, paint)
    paint.xfermode = XFERMODE
//  squareBitmap的定位尺寸
    canvas.drawBitmap(squareBitmap, 150f.px, 50f.px, paint)
    paint.xfermode = null
    canvas.restoreToCount(count)
  }
}