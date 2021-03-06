package com.example.networktest

import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okio.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() {
    runBlocking {
        getBaiduResponse()
    }
}

suspend fun getBaiduResponse() {
    try {
        val response = request("https://www.baidu.com/")
        // 得到服务器返回的具体内容
        println(response)
    } catch (e: Exception) {
        // 在这里对异常情况进行处理
    }
}

suspend fun request(address: String): String {
    return suspendCoroutine { continuation ->
        HttpUtil.sendHttpRequest(address, object : HttpCallbackListener {
            override fun onFinish(response: String) {
                continuation.resume(response)
            }

            override fun onError(e: Exception) {
                continuation.resumeWithException(e)
            }
        })

//        1.OkHttp
//        HttpUtil.sendOkHttpRequest(address, object : Callback {
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("OkHttpRequest Fail", "onFailure!", )
//            }
//        })
    }
}
