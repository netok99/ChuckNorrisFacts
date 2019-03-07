package com.chucknorrisfacts.search.data.datasource

import io.reactivex.Single

interface SearchCacheDataSource {

    fun getCategoriesFact(): Single<List<String>>

    fun saveCategoriesFact(categories: List<String>)

    fun getSearches(): Single<List<String>>

    fun saveSearch(searches: List<String>)
}
