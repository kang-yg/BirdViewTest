package com.e.birdviewtest

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myAsync: GetDataAsyctask = GetDataAsyctask()
        myAsync.execute("oily", "1")

        //TODO 전역변수에 있는 데이터 사용해서 gridAdapte생성자에 전달

        val gridAdapte: GridViewAdapter =
            GridViewAdapter(applicationContext, R.layout.grid_item, GlobalVariable.mainImg, GlobalVariable.mainTitle, GlobalVariable.mainPrice)

        myGridView.adapter = gridAdapte
    }
}
