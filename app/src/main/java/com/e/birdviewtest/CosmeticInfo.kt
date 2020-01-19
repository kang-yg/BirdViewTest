package com.e.birdviewtest

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.cosmeic_info.*


class CosmeticInfo : AppCompatActivity() {

    val infoCode: Int = 1
    var myHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cosmeic_info)

        //TODO 세부정보 보여주는 작업
        myHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    infoCode -> {
                        cosmetic_full_img.setImageBitmap(msg.obj as Bitmap)
                        cosmetic_title.text = GlobalVariable.cosmeticInfo.title
                        cosmetic_price.text = GlobalVariable.cosmeticInfo.price
                        cosmetic_description.text = GlobalVariable.cosmeticInfo.description
                    }
                }
            }
        }

        GetFullImg().start()
    }

    override fun onStop() {
        super.onStop()
        GetFullImg().interrupt()
    }

    inner class GetFullImg : Thread() {
        val myMessage: Message = myHandler!!.obtainMessage()
        override fun run() {
            myMessage.what = infoCode
            myMessage.obj = LoadImgFromURL.loadFullImg()

            myHandler!!.sendMessage(myMessage)
        }
    }
}