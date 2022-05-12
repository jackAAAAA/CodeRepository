package com.example.fragmenttest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class leftFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        获取另外一个Fragment的实例，比如：rightFragment
        if (activity != null) {
            val mainActivity = activity as MainActivity
//            val rightFragment = mainActivity.supportFragmentManager.findFragmentById(R.id.rightFrag) as rightFragment
//            val rightFragment = rightFrag as rightFragment
        }

        return inflater.inflate(R.layout.left_fragment,container, false)
    }
}