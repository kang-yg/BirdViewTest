package com.e.birdviewtest

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import android.view.View
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

    constructor(_mainActivit: Activity) {
        this.activity = _mainActivit
    }

    //TODO 변수를 하나 더 받아서(search) null or empty가 아니면 request를 분기시켜서 호출 skinType을 empty로 만들어 Cosmetics객체 생성
    override fun doInBackground(vararg p0: String?) {
        //To change body of created functions use File | Settings | File Templates.
        Log.d("GetDataAsyctask", "GetDataAsyctask")
        var skinType: String = p0[0].toString()
        var page: Int = p0[1]!!.toInt()
        var sort: Boolean = p0[2]!!.toBoolean()
        var mySearchKey: String = p0[3].toString()
        val forEncode : String = URLEncoder.encode(mySearchKey, "UTF-8")

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
                        Cosmetics(tempId, tempThumbnailImg, tempTitle, tempPrice, tempOily, -1, -1)
                }

                "dry" -> {
                    val tempDry: Int = tempJsonObject.optString("dry_score").toInt()
                    tempCosmetics =
                        Cosmetics(tempId, tempThumbnailImg, tempTitle, tempPrice, -1, tempDry, -1)
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

    //TODO 아이템을 추가로 load시 ScrollBar의 위치가 Top으로 이동하는 문제 해결
    override fun onPostExecute(result: Any?) {
        super.onPostExecute(result)
        this.activity!!.serch_layout.visibility = View.VISIBLE
        this.activity!!.mainSpinner.visibility = View.VISIBLE

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
}