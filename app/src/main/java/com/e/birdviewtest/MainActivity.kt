package com.e.birdviewtest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AbsListView.OnScrollListener {

    val getMoreInfoCode: Int = 1
    var tempId: Int = -1

    var pageCount: Int = 1
    var skinType: String = ""
    var gridItemVisibleFlag: Boolean = false
    var myHandler: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pageCount = callMyAsync(this, "oily", pageCount.toString(), false)

        myGridView.setOnScrollListener(this)
        myGridView.setOnItemClickListener { adapterView, view, i, l ->
            tempId = GlobalVariable.mainId.get(i)
            val myItent: Intent = Intent(this, CosmeticInfo::class.java)

            myHandler = object : Handler() {
                override fun handleMessage(msg: Message?) {
                    when (msg!!.what) {
                        getMoreInfoCode -> {
                            startActivity(myItent)
                        }
                    }
                }
            }

            GetMoreInfo().start()

            Toast.makeText(
                applicationContext,
                GlobalVariable.mainId.get(i).toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.mainSpinner) as Array<String>
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mainSpinner.adapter = spinnerAdapter
        mainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //To change body of created functions use File | Settings | File Templates.
                when (p2) {
                    1 -> {
                        pageCount = 1
                        skinType = "oily"
                        GlobalVariable.cosmeticsArr.clear()
                        pageCount =
                            callMyAsync(this@MainActivity, "oily", pageCount.toString(), true)
                    }

                    2 -> {
                        pageCount = 1
                        skinType = "dry"
                        GlobalVariable.cosmeticsArr.clear()
                        pageCount =
                            callMyAsync(this@MainActivity, "dry", pageCount.toString(), true)
                    }

                    3 -> {
                        pageCount = 1
                        skinType = "sensitive"
                        GlobalVariable.cosmeticsArr.clear()
                        pageCount =
                            callMyAsync(this@MainActivity, "sensitive", pageCount.toString(), true)
                    }
                }
            }
        }

        //TODO 클릭스 키패드 닫기
        search_button.setOnClickListener { view ->
            GlobalVariable.cosmeticsArr.clear()
            callMyAsync(this, "oily", 1.toString(), false, search_edit.text.toString())
        }

    }

    inner class GetMoreInfo : Thread() {
        val myMessage: Message = myHandler!!.obtainMessage()
        override fun run() {
            myMessage.what = getMoreInfoCode
            LoadImgFromURL.loadMoreInfo(tempId)

            myHandler!!.sendMessage(myMessage)
        }
    }

    override fun onStop() {
        super.onStop()
        GetMoreInfo().interrupt()
    }

    override fun onScroll(
        view: AbsListView?,
        firstVisibleItem: Int,
        visibleItemCount: Int,
        totalItemCount: Int
    ) {
        //To change body of created functions use File | Settings | File Templates.
        gridItemVisibleFlag =
            (totalItemCount > 0) && (firstVisibleItem + visibleItemCount) >= totalItemCount
    }

    override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
        //To change body of created functions use File | Settings | File Templates.
        if (p1 == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && gridItemVisibleFlag) {
            when (skinType) {
                "oily" -> {
                    pageCount = callMyAsync(this, "oily", pageCount.toString(), true)
                }

                "dry" -> {
                    pageCount = callMyAsync(this, "dry", pageCount.toString(), true)
                }

                "sensitive" -> {
                    pageCount = callMyAsync(this, "sensitive", pageCount.toString(), true)
                }

                else -> {
                    pageCount = callMyAsync(this, "oily", pageCount.toString(), false)
                }
            }
        }
    }
}

fun callMyAsync(
    _activity: Activity,
    _skinType: String,
    _page: String,
    _sort: Boolean,
    _search: String? = null
): Int {
    val myAsyctask: GetDataAsyctask = GetDataAsyctask(_activity)
    myAsyctask.execute(_skinType, _page, _sort.toString(), _search)

    return _page.toInt() + 1
}
