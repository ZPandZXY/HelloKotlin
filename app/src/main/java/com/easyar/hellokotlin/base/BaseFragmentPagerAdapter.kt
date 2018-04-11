package com.easyar.hellokotlin.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by zp on 2017/11/15.
 */
class BaseFragmentPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    var fragments: ArrayList<Fragment>? = ArrayList()

    fun addAll(fs: ArrayList<Fragment>): Unit {
        fragments!!.addAll(fs)
    }

    override fun getItem(position: Int): Fragment {
        return fragments!!.get(position)
    }

    override fun getCount(): Int {
        return fragments!!.size
    }
}