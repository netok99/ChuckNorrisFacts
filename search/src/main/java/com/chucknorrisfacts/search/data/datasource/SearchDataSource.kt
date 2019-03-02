package com.chucknorrisfacts.search.data.datasource

import com.chucknorrisfacts.search.data.model.JokeModel
import com.chucknorrisfacts.search.data.model.SearchModel
import io.reactivex.Single

interface SearchDataSource {

    fun getRandomFact(): Single<JokeModel>

    fun getRandomCategoryFact(category: String): Single<JokeModel>

    fun getCategoriesFact(): Single<List<String>>

    fun getFact(query: String): Single<SearchModel>
}
