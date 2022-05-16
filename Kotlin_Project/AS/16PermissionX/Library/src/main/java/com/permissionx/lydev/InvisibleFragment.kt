package com.permissionx.lydev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

//    此处的回调写法没看懂，如何通知应用申请方权限是否调用成功呢？
//    1.callback为null就是没有传递一个已经具体的实现函数给callback
//    而它的初始值为null代表刚开始时确实没有传递具体的回调函数给callback
//    2.关于callback?.let函数：只有在callback非null，即有具体的实现函数之时才有
//    进入{}中来执行
//    private var callback: ((Boolean, List<String>) -> Unit)? = null
    private var callback: PermissionCallback? = null

    fun requestNow(cb: PermissionCallback?, vararg permission: String) {
        callback = cb
        requestPermissions(permission, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let {
                it(allGranted, deniedList)
            }
        }
    }
}