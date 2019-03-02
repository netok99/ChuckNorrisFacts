package com.chucknorrisfacts.search.data.remote

import com.chucknorrisfacts.search.data.datasource.SearchDataSource
import com.chucknorrisfacts.search.data.model.JokeModel
import com.chucknorrisfacts.search.data.model.SearchModel
import io.reactivex.Single

class SearchApiImpl constructor(private val api: SearchApi) : SearchDataSource {

    override fun getRandomFact(): Single<JokeModel> = api.getRandomFact()

    override fun getRandomCategoryFact(category: String): Single<JokeModel> = api.getRandomCategoryFact(category)

    override fun getCategoriesFact(): Single<List<String>> = api.getCategoriesFact()

    override fun getFact(query: String): Single<SearchModel> = api.getFact(query)
}
