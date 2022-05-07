package com.example.motionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.transition.TransitionManager
import com.example.motionlayout.util.dp

class ObjectAnimatorActivity : AppCompatActivity() {

  lateinit var root: ViewGroup

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_object_animator)
    root = findViewById(R.id.root)
  }

  fun onClick(v: View) {
    TransitionManager.beginDelayedTransition(root)
    with(v.layoutParams as FrameLayout.LayoutParams) {
  //利用以下代码，可以轻松实现动画的位置摆放——TOP BOTTOM START END CENTER等
      gravity = Gravity.END

      height *= 2
      width *= 2
    }

    v.requestLayout()

  }
}
