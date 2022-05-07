package com.hencoder.threadrxjava

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.hencoder.threadrxjava.model.Repo
import com.hencoder.threadrxjava.network.Api
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
  private var disposable: Disposable? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val retrofit: Retrofit = Retrofit.Builder()
      .baseUrl("https://api.github.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//      .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
      .build()

    val api: Api = retrofit.create(Api::class.java)

    val response: Single<List<Repo>> = api.getRepos("rengwuxian")

//    api.getRepos("rengwuxian")
//            有第45行语句，则第36行语句要被注释；否则如果注释这行语句，则也要注释第35行语句并且解除对第36行的注释
//      .subscribeOn(Schedulers.newThread())
//      .observeOn(AndroidSchedulers.mainThread())
//      .subscribe(object : SingleObserver<List<Repo>> {
//        override fun onSubscribe(d: Disposable) {
//          textView.text = "正在请求"
//          this@MainActivity.disposable = d
//        }
//
//        @SuppressLint("SetTextI18n")
//        override fun onSuccess(repos: List<Repo>) {
//          textView.text = "Result: ${repos[0].name}"
//        }
//
//        override fun onError(e: Throwable) {
//          textView.text = e.message ?: e.javaClass.name
//        }
//      })

//    Single.just(2)
//      .subscribeOn(Schedulers.io())
//      .observeOn(AndroidSchedulers.mainThread())

//    以下代码可利用RxJava设置一个秒表
//    initialDelay：初始时延；period：时间间隔
    Observable.interval(0, 1, TimeUnit.SECONDS)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(object : Observer<Long?> {
        override fun onComplete() {
        }

        override fun onSubscribe(d: Disposable?) {
        }

        override fun onNext(t: Long?) {
          textView.text = t.toString()
        }

        override fun onError(e: Throwable?) {
        }
      })
  }

  override fun onDestroy() {
    disposable?.dispose()
    super.onDestroy()
  }
}