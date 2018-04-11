package com.ceb.cameramodule

import android.content.Context
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.reflect.Method

/**
 * Created by zp on 2018/4/4.
 */
class CameraPreview(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {

    var mHolder: SurfaceHolder = holder
    var mCamera: Camera? = null

    constructor(context: Context?, camera: Camera) : this(context) {
        mCamera = camera
        mHolder.addCallback(this)
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        try {
            mCamera?.setPreviewDisplay(holder)
            mCamera?.startPreview()
        } catch (e: Exception) {
            print("IOException")
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        try {
            mCamera?.stopPreview()
//            mCamera?.setDisplayOrientation(Surface.ROTATION_0)
            setDisplayOrientation(this!!.mCamera!!, 90)
            mCamera?.setPreviewDisplay(mHolder)
            mCamera?.startPreview()
        } catch (e: Exception) {
            print("IOException ${e.message}")
            Log.e("=========", " ${e.message}")
        }
    }

    fun setDisplayOrientation(camera: Camera, angle: Int) {
        var downPolymorphic: Method = camera.javaClass.getMethod("setDisplayOrientation", Int::class.java)
        downPolymorphic.invoke(camera, angle)
    }
}