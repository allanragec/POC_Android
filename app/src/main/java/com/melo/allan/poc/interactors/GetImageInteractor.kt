package com.melo.allan.poc.interactors

import android.content.Context
import com.melo.allan.poc.R
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

/**
 * Created by allan.melo on 25/05/18.
 */
class GetImageInteractor(private val context: Context) {
    fun execute(products: String) : Observable<String> {
        return Observable.create<String> {
            val client = OkHttpClient()
            val url = String.format(context.getString(R.string.image_url), products)
            val request = Request.Builder()
                    .url(url)
                    .build()

            try {
                val result = getUrlImage(client.newCall(request).execute().body().string())
                it.onNext(result)
                it.onComplete()
            } catch (e: IOException) {
                it.onError(e)
            }
        }
    }

    private fun getUrlImage(html: String): String {
        val b = html.indexOf("<li class=\"active\"")
        var d: Int
        var e: Int
        var c: String

        if (b > -1) {
            c = html.substring(b, html.length)
            d = c.indexOf("http://")
            e = c.indexOf("l=1")
        } else {
            c = html
            d = html.indexOf("http://")
            e = html.indexOf("w=1")
        }

        return c.substring(d, e + 3)
    }
}