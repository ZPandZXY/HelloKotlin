package com.ceb.cameramodule

import android.net.Uri
import android.os.Environment
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zp on 2018/4/4.
 */
class FileUtil {

    companion object {

        fun getOutputMediaFileUri(type: Int): Uri? {
            return Uri.fromFile(getOutputMediaFile(type))
        }

        fun getOutputMediaFile(type: Int): File? {
            var mediaStorageDir: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCamrea")
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    print("failed to create directory")
                    return null
                }
            }
            var timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date().time)
            var mediaFile: File
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp + ".jpg")
            } else if (type == MEDIA_TYPE_VIDEO) {
                mediaFile = File(mediaStorageDir.path + File.separator + "VID_" + timeStamp + ".mp4")
            } else return null
            return mediaFile
        }

    }
}