package com.chucknorrisfacts.search.usecases

import com.chucknorrisfacts.search.data.repository.SearchRepository
import com.chucknorrisfacts.search.searchModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class SearchFactUseCaseTest {

    private lateinit var usecase: SearchFactUseCase
    private val mockRepository: SearchRepository = mock()

    private val searchModelItem = searchModel.copy()

    @Before
    fun setUp() {
        usecase = SearchFactUseCase(mockRepository)
    }

    @Test
    fun `getFact repository get success`() {
        val query = "test"
        whenever(mockRepository.getFact(query)).thenReturn(Single.just(searchModelItem))
        val test = usecase.get(query).test()

        verify(mockRepository).getFact(query)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(searchModelItem)
    }

    @Test
    fun `getFact repository get fail`() {
        val query = "test"
        val throwable = Throwable()
        whenever(mockRepository.getFact(query)).thenReturn(Single.error(throwable))
        val test = usecase.get(query).test()

        verify(mockRepository).getFact(query)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }
}