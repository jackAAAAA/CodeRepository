package com.hencoder.clipandcamera.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.clipandcamera.R
import com.hencoder.clipandcamera.dp

private val BITMAP_SIZE = 200.dp
private val BITMAP_PADDING = 100.dp
//图像-范围裁切和几何变换
class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val bitmap = getAvatar(BITMAP_SIZE.toInt())
  private val camera = Camera()

  init {
//    camera.rotateX(30f)
//    camera.setLocation(0f, 0f, -6 * resources.displayMetrics.density)
  }

  override fun onDraw(canvas: Canvas) {

//    TO DO系统学习canva的几何变换操作
    // 上半部分
    canvas.save()
    canvas.translate(BITMAP_PADDING + BITMAP_SIZE, BITMAP_PADDING + BITMAP_SIZE)
    canvas.clipRect(- BITMAP_SIZE / 2, - BITMAP_SIZE / 2, BITMAP_SIZE / 2, 0f)
    canvas.drawColor(Color.RED)
    canvas.rotate(-45f)
//    clipRect填入的参数也是相当于View视图坐标而言的
//    canvas.clipRect(- BITMAP_SIZE / 2, - BITMAP_SIZE / 2, BITMAP_SIZE / 2, 0f)
    canvas.rotate(45f)
    canvas.translate(- (BITMAP_PADDING + BITMAP_SIZE ), - (BITMAP_PADDING + BITMAP_SIZE))
    canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
    canvas.restore()

    // 下半部分
//    canvas.save()
//    canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
////    canvas.rotate(-30f)
////    camera.applyToCanvas(canvas)
//    canvas.clipRect(- BITMAP_SIZE / 2, 0f, BITMAP_SIZE / 2, BITMAP_SIZE / 2)
////    canvas.rotate(30f)
//    canvas.translate(- (BITMAP_PADDING + BITMAP_SIZE / 2), - (BITMAP_PADDING + BITMAP_SIZE / 2))
//    canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
//    canvas.restore()
  }

  private fun getAvatar(width: Int): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width
    return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
  }
}