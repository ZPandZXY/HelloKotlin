package com.easyar.numberclock

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_number.view.*

/**
 * Created by zp on 2018/1/3.
 */
class NumberClockView : LinearLayout {
    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }


    private fun initView(): Unit {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view: View = layoutInflater.inflate(R.layout.activity_number, this)

        var assets: AssetManager = context.assets
        var font: Typeface = Typeface.createFromAsset(assets, "digital_7.ttf")
        number.typeface = font
        number_bg.typeface = font
    }
}