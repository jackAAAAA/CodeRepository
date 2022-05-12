package com.example.fragmenttest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class anotherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        在Fragment中获取Activity
        if (activity != null) {
            val mainActivity = activity as MainActivity
        }

        return inflater.inflate(R.layout.another_fragment, container, false)
    }
}