package com.chucknorrisfacts.search.data.remote

import com.chucknorrisfacts.search.data.model.JokeModel
import com.chucknorrisfacts.search.data.model.SearchModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApi {

    @GET("random")
    fun getRandomFact(): Single<JokeModel>

    @GET("random?category={category}")
    fun getRandomCategoryFact(@Path("category") category: String): Single<JokeModel>

    @GET("categories")
    fun getCategoriesFact(): Single<List<String>>

    @GET("search?query={query}")
    fun getFact(@Path("query") query: String): Single<SearchModel>
}
