package com.easyar.hellokotlin

import android.os.Bundle
import com.easyar.hellokotlin.data.Repo
import com.easyar.hellokotlin.network.ApiClient
import com.easyar.hellokotlin.network.ApiErrorModel
import com.easyar.hellokotlin.network.ApiResponse
import com.easyar.hellokotlin.network.NetworkScheduler
import com.easyar.hellokotlin.util.L
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import kotlinx.android.synthetic.main.activity_net.*

/**
 * Created by zp on 2017/11/8.
 */
class NetActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        submit.setOnClickListener { fetchRepo() }
    }

    private fun fetchRepo() {
        ApiClient.instance.service.listRepos(inputUser.text.toString())
                .compose(NetworkScheduler.compose())
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribe(object : ApiResponse<List<Repo>>(this) {
                    override fun success(data: List<Repo>) {
                        L.d(data[0].owner.login)
                        L.d(data[0].name)
                        L.d(data[0].description)
                        L.d(data[0].html_url)
                    }

                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        L.d(apiErrorModel.message)
                    }
                })
    }
}