package com.e.birdviewtest

import android.app.Application
import android.graphics.Bitmap

class GlobalVariable : Application() {
    companion object {
        var cosmeticsArr: ArrayList<Cosmetics> = arrayListOf()
        var mainImg : ArrayList<Bitmap> = arrayListOf()
        var mainTitle : ArrayList<String> = arrayListOf()
        var mainPrice : ArrayList<String> = arrayListOf()
    }
}