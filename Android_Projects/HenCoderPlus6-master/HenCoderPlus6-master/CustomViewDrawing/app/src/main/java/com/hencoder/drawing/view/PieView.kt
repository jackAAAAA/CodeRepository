package com.hencoder.drawing.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

private val RADIUS = 150f.px
private val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)
private val COLORS = listOf(Color.parseColor("#C2185B"), Color.parseColor("#00ACC1"), Color.parseColor("#558B2F"), Color.parseColor("#5D4037"))
private val OFFSET_LENGTH = 20f.px
//图像——绘制饼图
//制作关于View学习的五毒打卡表
class PieView(context: Context?, attrs: AttributeSet?) :
  View(context, attrs) {
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
  }

  override fun onDraw(canvas: Canvas) {
    // 画弧
    var startAngle = 0f
    for ((index, angle) in ANGLES.withIndex()) {
      paint.color = COLORS[index]
      if (index == 3) {
//        用状态栈保存画布坐标系平移前的状态，入栈
        canvas.save()
        canvas.translate(OFFSET_LENGTH * cos(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat(), OFFSET_LENGTH * sin(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat())
      }
//      drawArc的参数分别是：四个定位尺寸、起始角度、扫描角度、绘制的圆弧面积是否包含圆心、设置画笔的属性
//      TO DO
      canvas.drawArc(width / 2f - RADIUS, height / 3f - RADIUS, width / 2f + RADIUS, height / 3f + RADIUS, startAngle, angle, true, paint)
      startAngle += angle
      if (index == 3) {
//        从状态栈中恢复处于栈顶的画布状态，出栈
        canvas.restore()
      }
    }
  }

}