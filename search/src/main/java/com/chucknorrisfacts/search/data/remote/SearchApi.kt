package com.chucknorrisfacts.search.data.remote

import com.chucknorrisfacts.search.data.model.JokeModel
import com.chucknorrisfacts.search.data.model.SearchModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("random")
    fun getRandomFact(): Single<JokeModel>

    @GET("random")
    fun getRandomCategoryFact(@Query("category") category: String): Single<JokeModel>

    @GET("categories")
    fun getCategoriesFact(): Single<List<String>>

    @GET("search")
    fun getFact(@Query("query") query: String): Single<SearchModel>
}
