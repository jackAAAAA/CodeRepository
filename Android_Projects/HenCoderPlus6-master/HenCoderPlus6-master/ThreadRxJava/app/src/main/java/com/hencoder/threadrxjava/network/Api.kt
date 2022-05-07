package com.hencoder.threadrxjava.network

import com.hencoder.threadrxjava.model.Repo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String) : Single<List<Repo>>
}