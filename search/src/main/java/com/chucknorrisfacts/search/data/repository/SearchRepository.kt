package com.chucknorrisfacts.search.data.repository

import com.chucknorrisfacts.search.data.model.JokeModel
import com.chucknorrisfacts.search.data.model.SearchModel
import io.reactivex.Single

interface SearchRepository {

    fun getRandomFact(): Single<JokeModel>

    fun getRandomCategoryFact(category: String): Single<JokeModel>

    fun saveCategoriesFact(categories: List<String>)

    fun getCategoriesFact(): Single<List<String>>

    fun getFact(query: String): Single<SearchModel>

    fun getLocalSearches(): Single<List<String>>

    fun getLocalSaveSearches(searches: List<String>)
}
