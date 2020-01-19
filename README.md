* OkHttp - <GetDataAsyctask.kt에서 사용><br>
https://stackoverflow.com/a/29680883/12355451<br>
https://dpdpwl.tistory.com/23<br>
https://github.com/square/okhttp/issues/2280#issuecomment-190232945<br><br>

request를 해서 response를 받는다.
JSON타입의 데이터를 라인으로 받아오지 않고 통으로. 즉, JSONObject로 받아와 getJSONArray("body")를 사용해서 body부분만 추출할 수 있다.
이 추출한 데이터는 JSONArray타입으로 저장을하여 body내의 아이템 각각의 아이템들은 getJSONObject()로 접근이 가능하다.
접근한 각 아이템의 요소들은 optString("요소명")으로 parsing이 가능하다.
데이터를 가져오는 단계에서 timeOut은 okhttp3.OkHttpClient.Builder객체를 생성해서 설정이 가능하다.<br><br>

* Load Image from URL - <GetDataAsyctask.kt에서 사용><br>
https://stackoverflow.com/a/5776903/12355451<br><br>
URL을 HttpsURLConnection으로 연결해서 InputStream으로 데이터를 받게 된다.<br>
받은 InputStream을 BitmapFactory.decodeStream()을 사용해 Bitmap으로 변환할 수 있다.<br><br>

* Asyctask에서 UI변화 - <GetDataAsyctask.kt에서 사용><br>
https://stackoverflow.com/a/19388540/12355451<br><br>
Asyctask의 생성자에 UI가 포함되는 Activity를 매개변수로 받아서 onPostExecute에서 UI에 접근할 때 사용한다.<br><br>

* GridView의 무한 스크롤 - <MainActivity.kt에서 사용><br>
https://krespo.net/176<br><br>
AbsListView.OnScrollListener를 implement한 후 onScroll()와 onScrollStateChanged()를 overrid해서 사용한다.<br><br>

* ScrollView안에 LinearLayout의 weight는 android:fillViewport="true"옵션을 추가하면 가능하다.<br><br>

* 네트워크 작업을 메인 스레드와 분리시켜 비동기 처리 하지 않고 Activity를 전환시킬 경우 네트워크 관련 코드에서 NetworkOnMainThreadException이 아닌 NullPointException이 나올 수 있다.<br><br>

* Handler & Tread<br>
https://recipes4dev.tistory.com/166 <br>
메시지 객체를 획득하기 위해서는 Handler의 obtainMessage() 메서드를 사용합니다. obtainMessage() 메서드는 글로벌 메시지 풀(Global Message Pool)로부터 메시지를 가져오는데, 정적(static)으로 생성된 재사용(recycled) 객체로 관리되기 때문에 new 키워드로 새로운 Message 인스턴스를 만드는 것보다 효율적이다.<br>

※ Message가 재사용 되면 "this message is already in use androidruntimeexception" 이 발생하게 된다. <br>


    myHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                infoCode -> {
                    cosmetic_full_img.setImageBitmap(msg.obj as Bitmap)
                }
            }
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

[Message 클래스 변수]<br>
ⓐ int what : 메시지 종류 식별을 위한 사용자 정의 메시지 코드.<br>
ⓑ int arg1 : 메시지를 통해 전달되는 정수 값 저장.<br>
ⓒ int arg2 : 메시지를 통해 전달되는 정수 값 저장.<br>
ⓓ Object obj : 수신 스레드에 전달할 임의의 객체 저장.<br><br>

[Message 메소드]<br>
ⓐ Message obtainMessage() : 메시지의 target이 핸들러 자신으로 지정된 Message 객체 리턴
ⓑMessage obtainMessage(int what) : what이 지정된 Message 객체 리턴.