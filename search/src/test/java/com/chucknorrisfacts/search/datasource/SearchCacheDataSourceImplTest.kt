package com.chucknorrisfacts.search.datasource

import com.chucknorrisfacts.cache.ReactiveCache
import com.chucknorrisfacts.search.categoriesList
import com.chucknorrisfacts.search.data.local.SearchCacheDataSourceImpl
import com.chucknorrisfacts.search.searchList
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test


class SearchCacheDataSourceImplTest {

    private lateinit var dataSource: SearchCacheDataSourceImpl

    private val mockCacheCategoriesFact: ReactiveCache<List<String>> = mock()
    private val mockCacheSearches: ReactiveCache<List<String>> = mock()

    private val cacheListCategories = categoriesList
    private val cacheListSearch = searchList

    private val keyCategoriesFact = SearchCacheDataSourceImpl.KEY_CATEGORIES_FACT
    private val keySearches = SearchCacheDataSourceImpl.KEY_SEARCHES

    private val throwable = Throwable()

    @Before
    fun setUp() {
        dataSource = SearchCacheDataSourceImpl(mockCacheCategoriesFact, mockCacheSearches)
    }

    @Test
    fun `get categories fact local cache success`() {
        whenever(mockCacheCategoriesFact.load(keyCategoriesFact)).thenReturn(Single.just(cacheListCategories))

        val test = dataSource.getCategoriesFact().test()

        verify(mockCacheCategoriesFact).load(keyCategoriesFact)
        test.assertValue(cacheListCategories)
    }

    @Test
    fun `get categories fact local cache fail`() {
        whenever(mockCacheCategoriesFact.load(keyCategoriesFact)).thenReturn(Single.error(throwable))
        val test = dataSource.getCategoriesFact().test()

        verify(mockCacheCategoriesFact).load(keyCategoriesFact)
        test.assertError(throwable)
    }

    @Test
    fun `set categories fact local cache success`() {
        whenever(mockCacheCategoriesFact.save(keyCategoriesFact, cacheListCategories))
            .thenReturn(Single.just(cacheListCategories))

        verify(mockCacheCategoriesFact).save(keyCategoriesFact, cacheListCategories)
    }

    @Test
    fun `set categories fact local cache fail`() {
        whenever(mockCacheCategoriesFact.save(keyCategoriesFact, cacheListCategories)).thenReturn(Single.error(throwable))

        verify(mockCacheCategoriesFact).save(keyCategoriesFact, cacheListCategories)
    }

    @Test
    fun `get searches local cache success`() {
        whenever(mockCacheSearches.load(keySearches)).thenReturn(Single.just(cacheListSearch))

        val test = dataSource.getSearches().test()

        verify(mockCacheSearches).load(keySearches)
        test.assertValue(cacheListSearch)
    }

    @Test
    fun `get searches local cache fail`() {
        whenever(mockCacheSearches.load(keySearches)).thenReturn(Single.error(throwable))

        val test = dataSource.getSearches().test()

        verify(mockCacheSearches).load(keySearches)
        test.assertError(throwable)
    }

    @Test
    fun `set searches local cache success`() {
        whenever(mockCacheSearches.save(keySearches, cacheListSearch)).thenReturn(Single.just(cacheListSearch))

        verify(mockCacheSearches).save(keySearches, cacheListSearch)
    }

    @Test
    fun `set searches local cache fail`() {
        whenever(mockCacheSearches.save(keySearches, cacheListSearch)).thenReturn(Single.error(throwable))

        verify(mockCacheCategoriesFact).save(keySearches, cacheListSearch)
    }
}
