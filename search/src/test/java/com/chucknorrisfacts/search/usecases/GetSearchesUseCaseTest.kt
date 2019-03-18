package com.chucknorrisfacts.search.usecases

import com.chucknorrisfacts.search.data.repository.SearchRepository
import com.chucknorrisfacts.search.searchList
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetSearchesUseCaseTest {

    private lateinit var usecase: GetSearchesUseCase
    private val mockRepository: SearchRepository = mock()

    private val searches = searchList

    @Before
    fun setUp() {
        usecase = GetSearchesUseCase(mockRepository)
    }

    @Test
    fun `getFact repository get success`() {
        whenever(mockRepository.getSearches()).thenReturn(Single.just(searches))
        val test = usecase.get().test()

        verify(mockRepository).getSearches()

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(searches)
    }

    @Test
    fun `getFact repository get fail`() {
        val throwable = Throwable()
        whenever(mockRepository.getSearches()).thenReturn(Single.error(throwable))
        val test = usecase.get().test()

        verify(mockRepository).getSearches()

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }
}
