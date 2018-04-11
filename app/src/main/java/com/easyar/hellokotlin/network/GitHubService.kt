package com.easyar.hellokotlin.network

import com.easyar.hellokotlin.data.Repo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by zp on 2017/11/8.
 */
interface GitHubService {
    @GET("http://www.baidu.com")
    fun listRepos(@Query("user") user: String): Observable<List<Repo>>
}