package com.easyar.hellokotlin.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by zp on 2017/11/15.
 */
abstract class BaseFragment : Fragment() {

    var isViewCreated: Boolean = false
    var isUIVisible: Boolean = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isViewCreated = true
        lazyLoad()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isUIVisible = true
            lazyLoad()
        } else {
            isUIVisible = false
        }
    }

    private fun lazyLoad(): Unit {
        if (isViewCreated && isUIVisible) {
            loadData()
            isViewCreated = false
            isUIVisible = false
        }
    }

    protected abstract fun loadData(): Unit;
}