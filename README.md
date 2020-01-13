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

