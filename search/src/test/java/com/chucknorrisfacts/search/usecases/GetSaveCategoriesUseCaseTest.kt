package com.chucknorrisfacts.search.usecases

import com.chucknorrisfacts.search.categoriesList
import com.chucknorrisfacts.search.data.repository.SearchRepository
import com.chucknorrisfacts.search.searchModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetSaveCategoriesUseCaseTest {

    private lateinit var usecase: GetSaveCategoriesUseCase
    private val mockRepository: SearchRepository = mock()

    private val categories = categoriesList

    @Before
    fun setUp() {
        usecase = GetSaveCategoriesUseCase(mockRepository)
    }

    @Test
    fun `getFact repository get success`() {
        whenever(mockRepository.getLocalCategoriesFact()).thenReturn(Single.just(categories))
        val test = usecase.get().test()

        verify(mockRepository).getLocalCategoriesFact()

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(categories)
    }

    @Test
    fun `getFact repository get fail`() {
        val throwable = Throwable()
        whenever(mockRepository.getLocalCategoriesFact()).thenReturn(Single.error(throwable))
        val test = usecase.get().test()

        verify(mockRepository).getLocalCategoriesFact()

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }
}
