package com.e.birdviewtest

import android.os.AsyncTask
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class GetDataAsyctask : AsyncTask<String, Any, Any>() {
    override fun doInBackground(vararg p0: String?): Any? {
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
        for (i in 0 until jsonArrayList.length()){
            val tempJsonObject : JSONObject = jsonArrayList.getJSONObject(i)

            val tempId : Int = tempJsonObject.optString("id").toInt()
            val tempThumbnailImg : String = tempJsonObject.optString("thumbnail_image")
            val tempTitle : String = tempJsonObject.optString("title")
            val tempPrice : String = tempJsonObject.optString("price")
            var tempCosmetics : Cosmetics? = null
            when(skinType){
                "oily" -> {
                    val tempOily : Int = tempJsonObject.optString("oily_score").toInt()
                    tempCosmetics = Cosmetics(tempId, tempThumbnailImg, tempTitle, tempPrice, tempOily)
                }

                "dry" -> {
                    val tempDry : Int = tempJsonObject.optString("dry_score").toInt()
                    tempCosmetics = Cosmetics(tempId, tempThumbnailImg, tempTitle, tempPrice, tempDry)
                }

                "sensitive" -> {
                    val tempSensitive : Int = tempJsonObject.optString("sensitive_score").toInt()
                    tempCosmetics = Cosmetics(tempId, tempThumbnailImg, tempTitle, tempPrice, tempSensitive)
                }
            }

            GlobalVariable.cosmeticsArr.add(tempCosmetics!!)
            for (i in  0 until GlobalVariable.cosmeticsArr.size){
                LoadImgFromURL.loadImg(GlobalVariable.cosmeticsArr.get(i).thumbnail_image)
                GlobalVariable.mainTitle.add(GlobalVariable.cosmeticsArr.get(i).title)
                GlobalVariable.mainPrice.add(GlobalVariable.cosmeticsArr.get(i).price)
            }
        }

        return 0
    }
}