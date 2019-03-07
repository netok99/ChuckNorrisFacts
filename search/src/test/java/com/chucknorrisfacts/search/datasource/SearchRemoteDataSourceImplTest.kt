package com.chucknorrisfacts.search.datasource

import com.chucknorrisfacts.search.categories
import com.chucknorrisfacts.search.data.remote.SearchApi
import com.chucknorrisfacts.search.data.remote.SearchRemoteApiImpl
import com.chucknorrisfacts.search.jokeModel
import com.chucknorrisfacts.search.searchModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class SearchRemoteDataSourceImplTest {

    private lateinit var dataSource: SearchRemoteApiImpl
    private val mockApi: SearchApi = mock()

    private val jokerItem = jokeModel.copy(value = "remote")
    private val searchModelItem = searchModel.copy()
    private val categoryList = categories

    private val throwable = Throwable()

    @Before
    fun setUp() {
        dataSource = SearchRemoteApiImpl(mockApi)
    }

    @Test
    fun `get comments remote success`() {
        val query = "teste"
        whenever(mockApi.getFact(query)).thenReturn(Single.just(searchModelItem))
        val test = dataSource.getFact(query).test()

        verify(mockApi).getFact(query)
        test.assertValue(searchModelItem)
    }

    @Test
    fun `get comments remote fail`() {
        val query = "teste"
        whenever(mockApi.getFact(query)).thenReturn(Single.error(throwable))
        val test = dataSource.getFact(query).test()

        verify(mockApi).getFact(query)
        test.assertError(throwable)
    }

    @Test
    fun `getCategoriesFact remote success`() {
        whenever(mockApi.getCategoriesFact()).thenReturn(Single.just(categoryList))
        val test = dataSource.getCategoriesFact().test()

        verify(mockApi).getCategoriesFact()
        test.assertValue(categoryList)
    }

    @Test
    fun `getCategoriesFact remote fail`() {
        whenever(mockApi.getCategoriesFact()).thenReturn(Single.error(throwable))
        val test = dataSource.getCategoriesFact().test()

        verify(mockApi).getCategoriesFact()
        test.assertError(throwable)
    }

    @Test
    fun `getRandomCategoryFact remote success`() {
        val category = "dev"
        whenever(mockApi.getRandomCategoryFact(category)).thenReturn(Single.just(jokerItem))
        val test = dataSource.getRandomCategoryFact(category).test()

        verify(mockApi).getRandomCategoryFact(category)
        test.assertValue(jokerItem)
    }

    @Test
    fun `getRandomCategoryFact remote fail`() {
        val category = "dev"
        whenever(mockApi.getRandomCategoryFact(category)).thenReturn(Single.error(throwable))
        val test = dataSource.getRandomCategoryFact(category).test()

        verify(mockApi).getRandomCategoryFact(category)
        test.assertError(throwable)
    }

    @Test
    fun `getRandomFact() remote success`() {
        whenever(mockApi.getRandomFact()).thenReturn(Single.just(jokerItem))
        val test = dataSource.getRandomFact().test()

        verify(mockApi).getRandomFact()
        test.assertValue(jokerItem)
    }

    @Test
    fun `getRandomFact() remote fail`() {
        whenever(mockApi.getRandomFact()).thenReturn(Single.error(throwable))
        val test = dataSource.getRandomFact().test()

        verify(mockApi).getRandomFact()
        test.assertError(throwable)
    }
}