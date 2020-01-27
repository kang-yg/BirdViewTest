package com.e.birdviewtest

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URLEncoder
import java.util.concurrent.TimeUnit


class GetDataAsyctask : AsyncTask<String, Any, Any> {
    var activity: Activity? = null
    var statusCode: Int = 0

    constructor(_mainActivit: Activity) {
        this.activity = _mainActivit
    }

    override fun doInBackground(vararg p0: String?) {
        Log.d("GetDataAsyctask", "GetDataAsyctask")
        var skinType: String = p0[0].toString()
        var page: Int = p0[1]!!.toInt()
        var sort: Boolean = p0[2]!!.toBoolean()
        var mySearchKey: String = p0[3].toString()
        val forEncode: String = URLEncoder.encode(mySearchKey, "UTF-8")

        var timeBuilder: okhttp3.OkHttpClient.Builder = OkHttpClient.Builder()
        timeBuilder.readTimeout(10000, TimeUnit.SECONDS)
        timeBuilder.writeTimeout(10000, TimeUnit.SECONDS)
        val client: OkHttpClient = timeBuilder.build()
        var request: Request? = null
        if (p0[3].isNullOrEmpty()) {
            request = Request.Builder()
                .url("https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products?skin_type=${skinType}&page=${page}")
                .build()
        } else {
            request = Request.Builder()
                .url("https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products?search=${forEncode}")
                .build()
        }
        var response: Response? = null
        try {
            response = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        statusCode = response!!.code()
        Log.d("statusCode", response!!.code().toString())

        if (response!!.code() != 200) {
            cancel(true)
        } else {
            val jsonObject: JSONObject = JSONObject(response!!.body().string())
            val jsonArrayList: JSONArray = jsonObject.getJSONArray("body")

            var tempJsonObject: JSONObject

            var tempId: Int
            var tempThumbnailImg: String
            var tempTitle: String
            var tempPrice: String
            var tempCosmetics: Cosmetics?
            for (i in 0 until jsonArrayList.length()) {
                tempJsonObject = jsonArrayList.getJSONObject(i)

                tempId = tempJsonObject.optInt("id")
                tempThumbnailImg = tempJsonObject.optString("thumbnail_image")
                tempTitle = tempJsonObject.optString("title")
                tempPrice = tempJsonObject.optString("price")
                tempCosmetics = null
                when (skinType) {
                    "oily" -> {
                        val tempOily: Int = tempJsonObject.optString("oily_score").toInt()
                        tempCosmetics =
                            Cosmetics(
                                tempId,
                                tempThumbnailImg,
                                tempTitle,
                                tempPrice,
                                tempOily,
                                -1,
                                -1
                            )
                    }

                    "dry" -> {
                        val tempDry: Int = tempJsonObject.optString("dry_score").toInt()
                        tempCosmetics =
                            Cosmetics(
                                tempId,
                                tempThumbnailImg,
                                tempTitle,
                                tempPrice,
                                -1,
                                tempDry,
                                -1
                            )
                    }

                    "sensitive" -> {
                        val tempSensitive: Int = tempJsonObject.optString("sensitive_score").toInt()
                        tempCosmetics =
                            Cosmetics(
                                tempId,
                                tempThumbnailImg,
                                tempTitle,
                                tempPrice,
                                -1,
                                -1,
                                tempSensitive
                            )
                    }

                    else -> {
                        Cosmetics(tempId, tempThumbnailImg, tempTitle, tempPrice, -1, -1, -1)
                    }
                }
                GlobalVariable.cosmeticsArr.add(tempCosmetics!!)
            }
            when (sort) {
                true -> {
                    when (skinType) {
                        "oily" -> {
                            GlobalVariable.cosmeticsArr.sortBy { it.oily_score }
                            LoadImgFromURL.loadImg()
                        }

                        "dry" -> {
                            GlobalVariable.cosmeticsArr.sortBy { it.dry_score }
                            LoadImgFromURL.loadImg()
                        }

                        "sensitive" -> {
                            GlobalVariable.cosmeticsArr.sortBy { it.sensitive_score }
                            LoadImgFromURL.loadImg()
                        }
                    }
                }

                else -> LoadImgFromURL.loadImg()
            }
        }
    }

    override fun onCancelled() {
        super.onCancelled()
        Log.d("GetDataAsyctask", "onCancelled")
        Log.d("GetDataAsyctask", "onCancelled : ".plus(statusCode.toString()))

        activity!!.main_progress.visibility = View.GONE
        Toast.makeText(
            activity!!,
            statusCode.toString().plus(activity!!.resources.getString(R.string.response_exception)),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onPostExecute(result: Any?) {
        super.onPostExecute(result)
        Log.d("GetDataAsyctask", "onPostExecute")

        this.activity!!.search_edit.visibility = View.VISIBLE
        this.activity!!.mainSpinner.visibility = View.VISIBLE
        this.activity!!.main_progress.visibility = View.GONE
        this.activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        val gridAdapte: GridViewAdapter =
            GridViewAdapter(
                activity!!.applicationContext,
                R.layout.grid_item,
                GlobalVariable.mainImg,
                GlobalVariable.mainTitle,
                GlobalVariable.mainPrice,
                GlobalVariable.mainId
            )

        activity!!.myGridView.adapter = gridAdapte
    }

    override fun onPreExecute() {
        super.onPreExecute()
        Log.d("GetDataAsyctask", "onPreExecute")
        this.activity!!.main_progress.visibility = View.VISIBLE
        this.activity!!.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}