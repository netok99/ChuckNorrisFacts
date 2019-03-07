package com.chucknorrisfacts.search.data.repository

import com.chucknorrisfacts.search.data.datasource.SearchCacheDataSource
import com.chucknorrisfacts.search.data.datasource.SearchRemoteDataSource
import com.chucknorrisfacts.search.data.model.JokeModel
import com.chucknorrisfacts.search.data.model.SearchModel
import io.reactivex.Single

class SearchRepositoryImpl constructor(
    private val searchCacheDataSource: SearchCacheDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override fun saveCategoriesFact(categories: List<String>) =
        searchCacheDataSource.saveCategoriesFact(categories)

    override fun getLocalSearches(): Single<List<String>> =
        searchCacheDataSource.getSearches()

    override fun getRandomFact(): Single<JokeModel> =
        searchRemoteDataSource.getRandomFact()

    override fun getRandomCategoryFact(category: String): Single<JokeModel> =
        searchRemoteDataSource.getRandomCategoryFact(category)

    override fun getCategoriesFact(): Single<List<String>> =
        searchRemoteDataSource.getCategoriesFact()

    override fun getFact(query: String): Single<SearchModel> =
        searchRemoteDataSource.getFact(query)

    override fun getLocalSaveSearches(searches: List<String>) =
        searchCacheDataSource.saveSearch(searches)
}
