package com.e.birdviewtest

import android.app.Activity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), AbsListView.OnScrollListener {

    var pageCount : Int = 1
    var gridItemVisibleFlag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pageCount = callMyAsync(this,"oily", pageCount.toString())

        myGridView.setOnScrollListener(this)
        myGridView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(applicationContext, GlobalVariable.mainId.get(i).toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        //To change body of created functions use File | Settings | File Templates.
        gridItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount) >= totalItemCount
    }

    override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
        //To change body of created functions use File | Settings | File Templates.
        if (p1 == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && gridItemVisibleFlag){
            pageCount = callMyAsync(this, "oily", pageCount.toString())
        }
    }
}

fun callMyAsync(_activity: Activity, _skinType : String, _page : String) : Int{
    val myAsyctask : GetDataAsyctask = GetDataAsyctask(_activity)
    myAsyctask.execute(_skinType,_page)

    return _page.toInt() + 1
}
