package com.e.birdviewtest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class LoadImgFromURL {

    companion object {
        fun loadImg(thumbnailURL: String){
            Log.d("LoadImgFromURL", "loadImg()")
            GlobalScope.launch {
                val url: URL = URL(thumbnailURL)
                val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                conn.connect()

                val inputStr: InputStream = conn.inputStream
                GlobalVariable.mainImg.add(BitmapFactory.decodeStream(inputStr))
            }
        }
    }
}