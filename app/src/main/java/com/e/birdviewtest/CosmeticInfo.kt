package com.e.birdviewtest

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.cosmeic_info.*


class CosmeticInfo : AppCompatActivity() {

    final val infoCode: Int = 1
    var myHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cosmeic_info)

        myHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    infoCode -> {
                        cosmetic_full_img.setImageBitmap(msg.obj as Bitmap)
                    }
                }
            }
        }

        GetFullImg().start()
    }


    inner class GetFullImg : Thread() {
        val myMessage: Message = myHandler!!.obtainMessage()
        override fun run() {
            while (true){
                myMessage.what = infoCode
                myMessage.obj = LoadImgFromURL.loadFullImg(GlobalVariable.cosmeticInfo.fullImage)

                myHandler!!.sendMessage(myMessage)
            }
        }
    }
}