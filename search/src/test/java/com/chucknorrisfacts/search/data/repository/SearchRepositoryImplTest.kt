package com.chucknorrisfacts.search.data.repository

import com.chucknorrisfacts.search.categories
import com.chucknorrisfacts.search.data.datasource.SearchDataSource
import com.chucknorrisfacts.search.jokeModel
import com.chucknorrisfacts.search.searchModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class SearchRepositoryImplTest {

    private lateinit var repository: SearchRepositoryImpl
    private val mockRemoteDataSource: SearchDataSource = mock()

    private val jokerItem = jokeModel.copy(value = "remote")
    private val searchModelItem = searchModel.copy()
    private val categoryList = categories

    private val throwable = Throwable()

    @Before
    fun setUp() {
        repository = SearchRepositoryImpl(mockRemoteDataSource)
    }

    @Test
    fun `getFact remote success`() {
        val query = "teste"
        // given
        whenever(mockRemoteDataSource.getFact(query)).thenReturn(Single.just(searchModelItem))

        // when
        val test = repository.getFact(query).test()

        // then
        verify(mockRemoteDataSource).getFact(query)
        test.assertValue(searchModelItem)
    }

    @Test
    fun `getFact remote fail`() {
        val query = "teste"
        whenever(mockRemoteDataSource.getFact(query)).thenReturn(Single.error(throwable))
        val test = repository.getFact(query).test()

        verify(mockRemoteDataSource).getFact(query)
        test.assertError(throwable)
    }

    @Test
    fun `getCategoriesFact remote success`() {
        whenever(mockRemoteDataSource.getCategoriesFact()).thenReturn(Single.just(categoryList))
        val test = repository.getCategoriesFact().test()

        verify(mockRemoteDataSource).getCategoriesFact()
        test.assertValue(categoryList)
    }

    @Test
    fun `getCategoriesFact remote fail`() {
        whenever(mockRemoteDataSource.getCategoriesFact()).thenReturn(Single.error(throwable))
        val test = repository.getCategoriesFact().test()

        verify(mockRemoteDataSource).getCategoriesFact()
        test.assertError(throwable)
    }

    @Test
    fun `getRandomCategoryFact remote success`() {
        val category = "dev"
        whenever(mockRemoteDataSource.getRandomCategoryFact(category)).thenReturn(Single.just(jokerItem))
        val test = repository.getRandomCategoryFact(category).test()

        verify(mockRemoteDataSource).getRandomCategoryFact(category)
        test.assertValue(jokerItem)
    }

    @Test
    fun `getRandomCategoryFact remote fail`() {
        val category = "dev"
        whenever(mockRemoteDataSource.getRandomCategoryFact(category)).thenReturn(Single.error(throwable))
        val test = repository.getRandomCategoryFact(category).test()

        verify(mockRemoteDataSource).getRandomCategoryFact(category)
        test.assertError(throwable)
    }

    @Test
    fun `getRandomFact() remote success`() {
        whenever(mockRemoteDataSource.getRandomFact()).thenReturn(Single.just(jokerItem))
        val test = repository.getRandomFact().test()

        verify(mockRemoteDataSource).getRandomFact()
        test.assertValue(jokerItem)
    }

    @Test
    fun `getRandomFact() remote fail`() {
        whenever(mockRemoteDataSource.getRandomFact()).thenReturn(Single.error(throwable))
        val test = repository.getRandomFact().test()

        verify(mockRemoteDataSource).getRandomFact()
        test.assertError(throwable)
    }
}
