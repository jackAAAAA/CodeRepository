package com.example.runpermissiontest

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.permissionx.lydev.PermissionX
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val contactsList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        2.跳转到联系人
//        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
//        contactsList)
//        contactsView.adapter = adapter
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
//            != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS)
//            ,1)
//        } else {
//            readContacts()
//        }

//        3.基于PermissionX的写法
//        实际界面申请的权限顺序与预期的不一致，应该是Android系统内部有对权限进行编号
//        实际界面弹出的权限为contacts -> phone calls -> photos ans media on your device
//        按照字典序就可以理解界面权限申请的先后顺序了
        makeCall.setOnClickListener {
            PermissionX.request(
                this, Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) { allGranted, deniedList ->
               run {
                    if (allGranted) {
                        Toast.makeText(this, "All permissions are granted",
                        Toast.LENGTH_SHORT).show()
                        call()
                    } else {
                        Toast.makeText(this, "You denied $deniedList", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

//        1.拨打电话
//        makeCall.setOnClickListener {
////            2.申请权限
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
//            != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
//            } else {
//                call()
//            }
////            1.不申请权限
////            call()
//        }

    }

    //    call() / readContacts()的权限回调
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            1 -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    readContacts()
//                    call()
//                } else {
//                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//    }

    private fun readContacts() {
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )?.apply {
            while (moveToNext()) {
                val displayName = getString(
                    getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone
                            .DISPLAY_NAME
                    )
                )
                val number =
                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add("$displayName\n$number")
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel: 10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}