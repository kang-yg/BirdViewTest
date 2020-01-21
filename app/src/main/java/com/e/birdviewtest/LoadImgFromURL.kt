package com.e.birdviewtest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection

class LoadImgFromURL {
    companion object {
        //그리드뷰 아답터에 전달한 매개변수 정의
        fun loadImg() {
            GlobalVariable.mainImg.clear()
            GlobalVariable.mainTitle.clear()
            GlobalVariable.mainPrice.clear()
            GlobalVariable.mainId.clear()
            Log.d("size:", GlobalVariable.cosmeticsArr.size.toString())
            for (i in 0 until GlobalVariable.cosmeticsArr.size) {
                val url: URL = URL(GlobalVariable.cosmeticsArr.get(i).thumbnailImage)
                val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                conn.connect()

                val inputStr: InputStream = conn.inputStream
                GlobalVariable.mainImg.add(BitmapFactory.decodeStream(inputStr))
                GlobalVariable.mainTitle.add(GlobalVariable.cosmeticsArr.get(i).title)
                GlobalVariable.mainPrice.add(GlobalVariable.cosmeticsArr.get(i).price)
                GlobalVariable.mainId.add(GlobalVariable.cosmeticsArr.get(i).id)
            }
        }

        //그리드뷰 아이템 상세정보 가져오기
        fun loadMoreInfo(_id: Int) {
            val str: String =
                "https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/products/".plus(_id)

            var timeBuilder: okhttp3.OkHttpClient.Builder = OkHttpClient.Builder()
            timeBuilder.readTimeout(10000, TimeUnit.SECONDS)
            timeBuilder.writeTimeout(10000, TimeUnit.SECONDS)
            val client: OkHttpClient = timeBuilder.build()
            val request: Request = Request.Builder().url(str).build()
            var response: Response? = null
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val jsonObject: JSONObject = JSONObject(response!!.body().string()).getJSONObject("body")

            var tempId: Int
            var tempFullImg: String
            var tempTitle: String
            var tempPrice: String
            var tempDescription: String
            var tempOily: Int
            var tempDry: Int
            var tempSensitive: Int

            tempId = jsonObject.getInt("id")
            tempFullImg = jsonObject.getString("full_size_image")
            tempTitle = jsonObject.getString("title")
            tempPrice = jsonObject.getString("price")
            tempDescription = jsonObject.getString("description")
            tempOily = jsonObject.getInt("oily_score")
            tempDry = jsonObject.getInt("dry_score")
            tempSensitive = jsonObject.getInt("sensitive_score")

            GlobalVariable.cosmeticInfo = Cosmetics(
                tempId,
                tempFullImg,
                tempTitle,
                tempPrice,
                tempDescription,
                tempOily,
                tempDry,
                tempSensitive
            )

            Log.d("cosmeticInfo", "cosmeticInfo")
        }

        //그리드뷰 아이템 상세정보 이미지 가져오기
        fun loadFullImg() : Bitmap {
            val url: URL = URL(GlobalVariable.cosmeticInfo.fullImage)
            val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection
            conn.connect()

            val inputStream: InputStream = conn.inputStream
            return BitmapFactory.decodeStream(inputStream)
        }
    }
}
