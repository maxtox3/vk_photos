package gusev.max.photo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class NetworkClient @Inject constructor() {
    private val mOkHttpClient: OkHttpClient = OkHttpClient()

    fun loadImage(imageUrl: String): Observable<Bitmap> {
        return Observable.fromCallable({
            val loadRequest = Request.Builder()
                .url(imageUrl)
                .build()

            val response = mOkHttpClient
                .newCall(loadRequest)
                .execute()

            BitmapFactory.decodeStream(response.body()?.byteStream())
        })
    }
}