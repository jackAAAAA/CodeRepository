package com.example.retrofittest

import android.util.Log
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() {

//    6.
//    runBlocking {
//        val start = System.currentTimeMillis()
//        val deferred1 = async {
//            delay(1000)
//            5 + 5
//        }
//        val deferred2 = async {
//            delay(1000)
//            4 + 6
//        }
//        println("result is ${deferred1.await() + deferred2.await()}.")
//        val end = System.currentTimeMillis()
//        println("cost ${end - start} milliseconds.")
//    }

//    5.用于实际项目当中
//    val job = Job()
//    val scope = CoroutineScope(job)
//    scope.launch {
//        // do something
//    }
//    job.cancel()

//    4.
//    runBlocking {
//        coroutineScope {
//            launch {
//                for (i in 1..10) {
//                    println(i)
//                    delay(1000)
//                }
//            }
//        }
//        println("coroutineScope finished")
//    }
//    println("runBlocking finished")

//    3.
//    val start = System.currentTimeMillis()
//    runBlocking {
//        repeat(100000) {
//            launch {
//                println(".")
//            }
//        }
//    }
//    val end = System.currentTimeMillis()
//    println(end - start)

//    2.
    runBlocking {
//        2.3
        getAppData()

//        2.2
//        launch {
//            println("launch1")
//            delay(1000)
//            println("launch1 finished")
//        }
//        launch {
//            println("launch2")
//            delay(1000)
//            println("launch2 finished")
//        }
//
////        2.1
////        println("codes run in coroutine scope")
////        delay(1500)
////        println("codes run in coroutine scope finished")
    }

//    1.
//    GlobalScope.launch {
//        println("codes run in coroutine scope")
//        delay(1500)
//        println("codes run in coroutine scope finished")
//    }
//    Thread.sleep(1000)
}

//suspend fun printDot() {
//    println(".")
//    delay(1000)
//}

suspend fun printDot() = coroutineScope {
    launch {
        println(".")
        delay(1000)
    }
}

suspend fun getAppData() {
    try {
        val appList = ServiceCreator.create<AppService>().getAppData()
            .await() // 这段代码想运行通过，需要将BASE_URL中的地址改成http://localhost/
        if (appList != null) {
            for (app in appList) {
                println("id is ${app.id}")
                println("name is ${app.name}")
                println("version is ${app.version}")
            }
        }
//        println(appList)
        // 对服务器响应的数据进行处理
    } catch (e: Exception) {
        // 对异常情况进行处理
        e.printStackTrace()
    }
}

suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(RuntimeException("response body is null"))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}