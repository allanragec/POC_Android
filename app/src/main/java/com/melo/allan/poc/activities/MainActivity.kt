package com.melo.allan.poc.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.kizitonwose.android.disposebag.disposedWith
import com.melo.allan.poc.R
import com.melo.allan.poc.interactors.GetImageInteractor
import com.melo.allan.poc.interactors.GetProductsInteractor
import com.melo.allan.poc.util.PrefsUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById(R.id.text) as TextView

        val bundle = intent.extras

        if (bundle != null) {
            val description = (if (bundle.containsKey("description")) bundle.get("description") else "") as String

            textView.text = description
        }

        val imageView = findViewById(R.id.imageView) as ImageView

        val screenName = PrefsUtil(this).screenName
        Log.e("screenName", screenName)

        GetProductsInteractor(this)
                .execute(screenName)
                .subscribeOn(Schedulers.io())
                .flatMap { GetImageInteractor(this).execute(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Glide.with(this).load(it).into(imageView)
                }
                .doOnError { t -> Toast.makeText(applicationContext, getString(R.string.could_not_load), Toast.LENGTH_LONG).show() }
                .subscribe()
                .disposedWith(this)
    }

}
