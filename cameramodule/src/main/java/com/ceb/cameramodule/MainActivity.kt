package com.ceb.cameramodule

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    val REQUEST_PERMISSIONS_CODE: Int = 1
    val REQUIRED_PERMISSIONS: Array<String> = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
    var mFrontCameraInfo: Camera.CameraInfo = Camera.CameraInfo()
    var mFrontCameraId: Int = -1
    var mBackCameraInfo: Camera.CameraInfo = Camera.CameraInfo()
    var mBackCameraId: Int = -1
    var mCamera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initCameraInfo()
    }

    fun initCameraInfo(): Unit {
        val numberOfCamera: Int = Camera.getNumberOfCameras()
        for (cameraId in 0..(numberOfCamera - 1)) {
            var cameraInfo: Camera.CameraInfo = Camera.CameraInfo()
            Camera.getCameraInfo(cameraId, cameraInfo)
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                mBackCameraId = cameraId
                mBackCameraInfo = cameraInfo
            } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                mFrontCameraId = cameraId
                mFrontCameraInfo = cameraInfo
            }
        }
    }

    lateinit var mCameraPreview: CameraPreview
    var isRecording: Boolean = false

    fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (mCamera != null)
                print("相机已经被启动，无法同时开启多个相机")
            if (mBackCameraInfo != null) {
                mCamera = Camera.open(mBackCameraId)
            } else if (mFrontCameraInfo != null) {
                mCamera = Camera.open(mFrontCameraId)
            } else {
                print("没有相机可以被打开")
            }

            var params: Camera.Parameters = mCamera!!.parameters
            params.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
            mCamera!!.parameters = params

            var cameraPreview = CameraPreview(this, this!!.mCamera!!)
            camera_preview.addView(cameraPreview)
            mCameraPreview = cameraPreview
            take_picture.setOnClickListener {
                Thread.sleep(100)
                mCamera!!.takePicture(null, null, pictureCallback)
            }
            take_capture.setOnClickListener {
                if (isRecording) {
                    mMediaRecorder!!.stop()
                    releaseMediaRecorder()
                    take_capture.text = "录像"
                    isRecording = false
                } else {
                    if (prepareVideoRecorder()) {
                        mMediaRecorder!!.start()
                        take_capture.text = "停止"
                        isRecording = true
                    } else {
                        releaseMediaRecorder()
                    }
                }
            }
        }
    }

    var pictureCallback: Camera.PictureCallback = Camera.PictureCallback { data, camera ->
        var pictureFile: File = FileUtil.getOutputMediaFile(1)!!
        if (pictureFile == null) {
            print("error creatingg media file,check storage permissions:")
            return@PictureCallback
        }
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(pictureFile)
            fos.write(data)
            fos.close()
        } catch (e: Exception) {
            print("${e.message}")
            Log.e("---------", "拍摄失败！")
        } finally {
            Log.e("---------", "拍摄成功！")
            fos!!.close()
        }
    }

    var mMediaRecorder: MediaRecorder? = null

    fun prepareVideoRecorder(): Boolean {
        mCamera!!.unlock()

        mMediaRecorder = MediaRecorder()
        mMediaRecorder!!.setCamera(mCamera)

        mMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        mMediaRecorder!!.setVideoSource(MediaRecorder.VideoSource.CAMERA)

        mMediaRecorder!!.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_1080P))

        mMediaRecorder!!.setOutputFile(FileUtil.getOutputMediaFile(MEDIA_TYPE_VIDEO).toString())

        mMediaRecorder!!.setPreviewDisplay(mCameraPreview.mHolder.surface)
        try {
            mMediaRecorder!!.prepare()
        } catch (e: Exception) {
            releaseMediaRecorder()
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        if (!isRequirePermissionsGranted() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_PERMISSIONS_CODE)
        } else {
            openCamera()
        }
    }

    fun isRequirePermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            for (result in grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    onRequiredPermissionDenied()
                    return
                }
            }
            onRequiredPermissionsGranted()
        }
    }

    fun onRequiredPermissionsGranted() {
        openCamera()
    }

    fun onRequiredPermissionDenied(): Unit {
        var dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("权限检查")
        dialogBuilder.setMessage("监测到相关权限被拒绝，程序无法正常运行！")
        dialogBuilder.setNeutralButton("关闭程序", DialogInterface.OnClickListener { dialog, which -> finish() })
        dialogBuilder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCamera!!.release()
    }

    fun releaseMediaRecorder() {
        mMediaRecorder!!.reset()
        mMediaRecorder!!.release()
        mMediaRecorder = null
        mCamera!!.lock()
//        mCamera = null
    }
}
