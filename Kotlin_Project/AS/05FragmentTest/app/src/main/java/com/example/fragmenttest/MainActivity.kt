package com.example.fragmenttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        在Activity当中获取Fragment的实例两种写法
//        val fragment = supportFragmentManager.findFragmentById(R.id.leftFragment) as leftFragment
        val fragment = leftFrag as leftFragment

//        button.setOnClickListener {
//            replaceFragment(anotherFragment())
//        }
//        replaceFragment(rightFragment())
    }

//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        transaction.replace(R.id.rightLayout, fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }
}