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

        var skinType: String = p0[0].toString()
        var page: Int = p0[1]!!.toInt()

        val client: OkHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
//            .url("https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products?skin_type=oily&page=10")
            .url("https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products?skin_type=${skinType}&page=${page}")
            .build()
        var response: Response? = null
        try {
            response = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val jsonObject: JSONObject = JSONObject(response!!.body().string())
        val jsonArrayList: JSONArray = jsonObject.getJSONArray("body")
        for (i in 0 until jsonArrayList.length()){
            val jsonObject : JSONObject = jsonArrayList.getJSONObject(i)

            Log.d("GetDataAsyctask", jsonObject.optString("title"))
        }

        return 0
    }

}