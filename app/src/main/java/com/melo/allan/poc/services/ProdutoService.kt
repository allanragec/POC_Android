package com.melo.allan.poc.services

import com.melo.allan.poc.models.Produto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by allan.melo on 25/05/18.
 */
interface ProdutoService {
    @GET("beneficiario/produto")
    fun getProdutos(@Query("codigoBeneficiario") codigoBeneficiario: String): Observable<List<Produto>>
}