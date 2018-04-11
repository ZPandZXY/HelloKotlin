package com.easyar.hellokotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easyar.hellokotlin.R
import com.easyar.hellokotlin.base.BaseFragment
import com.easyar.hellokotlin.util.L

/**
 * Created by zp on 2017/11/3.
 */
class SplashFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        L.e("onCreateView" + index)
        return inflater?.inflate(R.layout.splashfragment, null)
    }

    override fun loadData() {
        L.e("loadData" + index)
    }

    var index: Int = -1

    fun init(index: Int) {
        L.e("index" + index)
        this.index = index
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        L.e("onCreate" + index)
    }


    override fun onDestroy() {
        super.onDestroy()
        L.e("onDestroy" + index)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        L.e("onDestroyView" + index)
    }
}