package com.permissionx.lydev

import android.app.Activity
import androidx.fragment.app.FragmentActivity

object PermissionX {
    private const val TAG = "InvisibleFragment"

    fun request(
        activity: FragmentActivity,
        vararg permissions: String,
        callback: PermissionCallback
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction()
                .add(invisibleFragment, TAG)
                .commitNow()
            invisibleFragment
        }
//        此处在数组permission前添加*，是为了将数组转换成可变长度参数
        fragment.requestNow(callback, *permissions)
    }
}