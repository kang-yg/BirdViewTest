package com.e.birdviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myAsync: GetDataAsyctask = GetDataAsyctask()
        myAsync.execute("oily", "1")

        val img: ArrayList<Int> = arrayListOf()
        img.add(R.drawable.c)

        val title: ArrayList<String> = arrayListOf()
        title.add("title01")

        val price: ArrayList<String> = arrayListOf()
        price.add("price01")

        val gridAdapte: GridViewAdapter =
            GridViewAdapter(applicationContext, R.layout.grid_item, img, title, price)

        myGridView.adapter = gridAdapte
    }
}
