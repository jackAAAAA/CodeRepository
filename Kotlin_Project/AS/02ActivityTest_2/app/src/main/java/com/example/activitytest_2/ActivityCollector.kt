package com.example.activitytest_2

import android.app.Activity

object ActivityCollector {
    private val activities: MutableList<Activity> = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }

    @JvmStatic
    fun doAction() {
        println("do Action")
    }
}