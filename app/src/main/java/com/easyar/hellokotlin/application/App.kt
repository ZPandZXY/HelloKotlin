package com.easyar.hellokotlin.application

import android.app.Application
import com.easyar.hellokotlin.network.ApiClient

/**
 * Created by zp on 2017/11/8.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.instance.init()
    }
}