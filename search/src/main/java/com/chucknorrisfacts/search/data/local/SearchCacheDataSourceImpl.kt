package com.chucknorrisfacts.search.data.local

import com.chucknorrisfacts.cache.ReactiveCache
import com.chucknorrisfacts.search.data.datasource.SearchCacheDataSource
import io.reactivex.Single

class SearchCacheDataSourceImpl constructor(
    private val cacheCategoriesFact: ReactiveCache<List<String>>,
    private val cacheSearches: ReactiveCache<List<String>>
) : SearchCacheDataSource {

    private val keyCategoriesFact = "categoriesFact"
    private val keySearches = "searches"

    override fun getCategoriesFact(): Single<List<String>> =
        cacheCategoriesFact.load(keyCategoriesFact)

    override fun saveCategoriesFact(categories: List<String>) {
        cacheCategoriesFact.save(keySearches, categories)
    }

    override fun getSearches(): Single<List<String>> =
        cacheSearches.load(keySearches)

    override fun saveSearch(searches: List<String>) {
        cacheSearches.save(keySearches, searches)
    }
}
