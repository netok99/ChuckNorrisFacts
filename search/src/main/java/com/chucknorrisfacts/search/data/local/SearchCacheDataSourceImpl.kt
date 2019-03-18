package com.chucknorrisfacts.search.data.local

import com.chucknorrisfacts.cache.ReactiveCache
import com.chucknorrisfacts.search.data.datasource.SearchCacheDataSource
import io.reactivex.Single

class SearchCacheDataSourceImpl constructor(
    private val cacheCategoriesFact: ReactiveCache<List<String>>,
    private val cacheSearches: ReactiveCache<List<String>>
) : SearchCacheDataSource {

    companion object {
        const val KEY_CATEGORIES_FACT = "categoriesFact"
        const val KEY_SEARCHES = "searches"
    }

    override fun getCategoriesFact(): Single<List<String>> =
        cacheCategoriesFact.load(KEY_CATEGORIES_FACT)

    override fun saveCategoriesFact(categories: List<String>) {
        cacheCategoriesFact.save(KEY_CATEGORIES_FACT, categories)
    }

    override fun getSearches(): Single<List<String>> =
        cacheSearches.load(KEY_SEARCHES)

    override fun saveSearch(searches: List<String>) {
        cacheSearches.save(KEY_SEARCHES, searches).subscribe()
    }
}
