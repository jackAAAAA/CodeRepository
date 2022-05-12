package com.example.notificationtest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        2.重要等级的通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel2 = NotificationChannel(
                "important", "Important",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel2)
        }
//        1.普通状态的通知
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                "normal", "Normal",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            manager.createNotificationChannel(channel)
//        }
        sendNotice.setOnClickListener {
//            3.发送“高”重要等级的通知
            val intent = Intent(this, NotificationActivity::class.java)
            val pi = PendingIntent.getActivity(this, 0, intent, 0)
            val notification = NotificationCompat.Builder(this, "important")
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setSmallIcon(R.drawable.small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.large_icon))
                .setContentIntent(pi)
                .build()
            manager.notify(1, notification)

//           2.发送通知，实现点击跳转
//            val intent = Intent(this, NotificationActivity::class.java)
//            val pi = PendingIntent.getActivity(this, 0, intent, 0)
//            val notification = NotificationCompat.Builder(this, "normal")
//                .setContentTitle("This is content title")
////              setContentText无法显示长文本
////                .setContentText("This is content test")
////              将NotificationCompat.BigTextStyle传入setStyle才能创建长文本
//                .setStyle(NotificationCompat.BigTextStyle().bigText("Learn" +
//                        "how to build notifications, send and sync data," +
//                        "and use voice actions. Get the official Android" +
//                        "IDE and developer tools to build apps for Android."))
////              将NotificationCompat.BigPictureStyle传入setStyle才能创建长文本
////              同时出现长文本和长图片之时，显示由第二个setStyle覆盖第一个setStyle
////                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources,
////                    R.drawable.big_image)))
//                .setSmallIcon(R.drawable.small_icon)
//                .setLargeIcon(
//                    BitmapFactory.decodeResource(
//                        resources,
//                        R.drawable.large_icon
//                    )
//                )
////              点击通知跳转
//                .setContentIntent(pi)
//                /**
//                 * 移除系统状态栏和下拉通知栏的通知图标，这是写法1；写法2请看如下
//                 * @see NotificationActivity
//                 */
//                .setAutoCancel(true)
//                .build()
//            manager.notify(1, notification)

//            1.发送通知
//            val notification = NotificationCompat.Builder(this, "normal")
//                .setContentTitle("This is content title")
//                .setContentText("This is content text")
//                .setSmallIcon(R.drawable.small_icon)
//                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.large_icon))
//                .build()
//            manager.notify(1, notification)
        }
    }
}