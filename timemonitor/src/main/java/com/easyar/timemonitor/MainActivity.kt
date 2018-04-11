package com.easyar.timemonitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {
    var TAG: String = javaClass.name
    var intentFilter: IntentFilter = IntentFilter()
    var timeChangeReceiver: TimeChangeReceiver = TimeChangeReceiver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        registerReceiver(timeChangeReceiver, intentFilter)
    }

    class TimeChangeReceiver constructor(context: Context) : BroadcastReceiver() {

        var context: Context = context
        override fun onReceive(p0: Context?, p1: Intent?) {
            var cc: Calendar = Calendar.getInstance()
            when (p1!!.action) {
                in Intent.ACTION_TIME_TICK -> {
                    Log.d("time receiver", "==========" + cc.get(Calendar.MINUTE))
                    if (cc.get(Calendar.HOUR_OF_DAY) == 8 && cc.get(Calendar.MINUTE) > 40)
                        openOtherApp()
                }
            }
        }

        fun openOtherApp() {
            var packageName = "com.alibaba.android.rimet"
            var launchIntentForPackage = context.packageManager.getLaunchIntentForPackage(packageName)
            context.startActivity(launchIntentForPackage)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }
}
