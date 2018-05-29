package com.melo.allan.poc.interactors

import android.content.Context
import com.melo.allan.poc.MyApplication
import com.melo.allan.poc.services.ProdutoService
import io.reactivex.Observable

/**
 * Created by allan.melo on 25/05/18.
 */

class GetProductsInteractor(private val context: Context) {
    fun execute(screenName: String): Observable<String> {
        val retrofit = MyApplication.retrofit!!
        val service = retrofit.create(ProdutoService::class.java)

        return service.getProdutos(screenName)
                .filter { !it.isEmpty() }
                .map { it.map { it.tipo }.reduce { s1, s2 -> s1 + "," + s2 } }
    }
}