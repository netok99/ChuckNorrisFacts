package com.chucknorrisfacts.search.data.repository

import com.chucknorrisfacts.search.data.datasource.SearchDataSource
import com.chucknorrisfacts.search.data.model.JokeModel
import com.chucknorrisfacts.search.data.model.SearchModel
import io.reactivex.Single

class SearchRepositoryImpl constructor(private val searchDataSource: SearchDataSource) : SearchRepository {

    override fun getRandomFact(): Single<JokeModel> = searchDataSource.getRandomFact()

    override fun getRandomCategoryFact(category: String): Single<JokeModel> =
        searchDataSource.getRandomCategoryFact(category)

    override fun getCategoriesFact(): Single<List<String>> = searchDataSource.getCategoriesFact()

    override fun getFact(query: String): Single<SearchModel> = searchDataSource.getFact(query)

}
