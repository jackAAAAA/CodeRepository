package com.hencoder.text.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.hencoder.text.R
import com.hencoder.text.dp

private val IMAGE_SIZE = 150.dp
private val IMAGE_PADDING = 50.dp
//图像——多行文本视图
class MultilineTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
  val text = """"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tristique urna tincidunt maximus viverra. Maecenas commodo pellentesque dolor ultrices porttitor. Vestibulum in arcu rhoncus, maximus ligula vel, consequat sem. Maecenas a quam libero. Praesent hendrerit ex lacus, ac feugiat nibh interdum et. Vestibulum in gravida neque. Morbi maximus scelerisque odio, vel pellentesque purus ultrices quis. Praesent eu turpis et metus venenatis maximus blandit sed magna. Sed imperdiet est semper urna laoreet congue. Praesent mattis magna sed est accumsan posuere. Morbi lobortis fermentum fringilla. Fusce sed ex tempus, venenatis odio ac, tempor metus."""
  private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
    textSize = 16.dp
  }
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    textSize = 16.dp
  }
  private val bitmap = getAvatar(IMAGE_SIZE.toInt())
  private val fontMetrics = Paint.FontMetrics()

  override fun onDraw(canvas: Canvas) {
//    val staticLayout = StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)
//    staticLayout.draw(canvas)
    canvas.drawBitmap(bitmap, width - IMAGE_SIZE, IMAGE_PADDING, paint)
    paint.getFontMetrics(fontMetrics)
    val measuredWidth = floatArrayOf(0f)
    var start = 0
    var count: Int
//    为啥verticalOffset为负的？？？：其实verticalOffset为正，因为fontMetrics.top为负值
    var verticalOffset = - fontMetrics.top
    var maxWidth: Float
//    image_padding是啥？？？：图片顶部与页面顶部的距离
    while (start < text.length) {
//      文字底部的纵坐标没触及图片顶部的纵坐标 || 文字顶部的纵坐标已超过图片底部的纵坐标
      maxWidth = if (verticalOffset + fontMetrics.bottom < IMAGE_PADDING
        || verticalOffset + fontMetrics.top > IMAGE_PADDING + IMAGE_SIZE) {
        width.toFloat()
      } else {
        width.toFloat() - IMAGE_SIZE
      }
      count = paint.breakText(text, start, text.length, true, maxWidth, measuredWidth)
      canvas.drawText(text, start, start + count, 0f, verticalOffset, paint)
      start += count
//      fontSpacing：系统推荐的两行文字的 baseline的距离
      verticalOffset += paint.fontSpacing
    }
  }

  fun getAvatar(width: Int): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width
    return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
  }
}