package com.e.birdviewtest

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlinx.android.synthetic.main.activity_main.*


class GetDataAsyctask : AsyncTask<String, Any, Any> {
    var activity : Activity? = null

    constructor(_mainActivit : Activity){
        this.activity = _mainActivit
    }

    override fun doInBackground(vararg p0: String?) {
        //To change body of created functions use File | Settings | File Templates.
        Log.d("GetDataAsyctask", "GetDataAsyctask")
        var skinType: String = p0[0].toString()
        var page: Int = p0[1]!!.toInt()

        val client: OkHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products?skin_type=${skinType}&page=${page}")
            .build()
        var response: Response? = null
        try {
            response = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //TODO URL에서 이미지 가져오기 https://stackoverflow.com/questions/5776851/load-image-from-url
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

            tempId = tempJsonObject.optString("id").toInt()
            tempThumbnailImg = tempJsonObject.optString("thumbnail_image")
            tempTitle = tempJsonObject.optString("title")
            tempPrice = tempJsonObject.optString("price")
            tempCosmetics = null
            when (skinType) {
                "oily" -> {
                    val tempOily: Int = tempJsonObject.optString("oily_score").toInt()
                    tempCosmetics =
                        Cosmetics(tempId, tempThumbnailImg, tempTitle, tempPrice, tempOily)
                }

                "dry" -> {
                    val tempDry: Int = tempJsonObject.optString("dry_score").toInt()
                    tempCosmetics =
                        Cosmetics(tempId, tempThumbnailImg, tempTitle, tempPrice, tempDry)
                }

                "sensitive" -> {
                    val tempSensitive: Int = tempJsonObject.optString("sensitive_score").toInt()
                    tempCosmetics =
                        Cosmetics(tempId, tempThumbnailImg, tempTitle, tempPrice, tempSensitive)
                }
            }
            GlobalVariable.cosmeticsArr.add(tempCosmetics!!)
        }
        loadImgFromURL()
    }

    override fun onPostExecute(result: Any?) {
        super.onPostExecute(result)
        val gridAdapte: GridViewAdapter =
            GridViewAdapter(activity!!.applicationContext, R.layout.grid_item, GlobalVariable.mainImg, GlobalVariable.mainTitle, GlobalVariable.mainPrice)

        activity!!.myGridView.adapter = gridAdapte
    }
}

fun loadImgFromURL() {
    Log.d("size:",GlobalVariable.cosmeticsArr.size.toString())
    for (i in 0 until GlobalVariable.cosmeticsArr.size) {
        val url: URL = URL(GlobalVariable.cosmeticsArr.get(i).thumbnail_image)
        val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection
        conn.connect()

        val inputStr: InputStream = conn.inputStream
        GlobalVariable.mainImg.add(BitmapFactory.decodeStream(inputStr))
        GlobalVariable.mainTitle.add(GlobalVariable.cosmeticsArr.get(i).title)
        GlobalVariable.mainPrice.add(GlobalVariable.cosmeticsArr.get(i).price)
    }
}