@file:Suppress("IllegalIdentifier")

package com.chucknorrisfacts.search.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.chucknorrisfacts.search.RxSchedulersOverrideRule
import com.chucknorrisfacts.search.categoriesList
import com.chucknorrisfacts.search.presentation.search.SearchViewModel
import com.chucknorrisfacts.search.searchList
import com.chucknorrisfacts.search.searchModel
import com.chucknorrisfacts.search.usecases.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    private val mockSearchFactUseCase: SearchFactUseCase = mock()
    private val mockSaveSearchUseCase: SaveSearchUseCase = mock()
    private val mockGetSearchesUseCase: GetSearchesUseCase = mock()
    private val mockGetSaveCategoriesUseCase: GetSaveCategoriesUseCase = mock()
    private val mockSaveCategoriesUseCase: SaveCategoriesUseCase = mock()

    private val searchModelItem = searchModel.copy()
    private val searches = searchList
    private val categories = categoriesList

    private val throwable = Throwable()

    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = SearchViewModel(
            mockSearchFactUseCase, mockSaveSearchUseCase, mockGetSearchesUseCase,
            mockGetSaveCategoriesUseCase, mockSaveCategoriesUseCase
        )
    }

    @Test
    fun `get fact succeeds`() {
        val query = "query"
        whenever(mockSearchFactUseCase.get(query)).thenReturn(Single.just(searchModelItem))

        viewModel.getFact(query)

        verify(mockSearchFactUseCase).get(query)
        assertEquals(searchModelItem, viewModel.searchModel.value)
    }

    @Test
    fun `get fact fails`() {
        val query = "query"
        whenever(mockSearchFactUseCase.get(query)).thenReturn(Single.error(throwable))

        viewModel.getFact(query)

        verify(mockSearchFactUseCase).get(query)
        assertEquals(throwable.message, viewModel.errorMessage.value)
    }

    @Test
    fun `get searches succeeds`() {
        whenever(mockGetSearchesUseCase.get()).thenReturn(Single.just(searches))

        viewModel.getSaveSearch()

        verify(mockGetSearchesUseCase).get()
        assertEquals(searches, viewModel.listSearches.value)
    }

    @Test
    fun `get searches fail`() {
        whenever(mockGetSearchesUseCase.get()).thenReturn(Single.error(throwable))

        viewModel.getSaveSearch()

        verify(mockGetSearchesUseCase).get()
        assertEquals(throwable.message, viewModel.errorMessage.value)
    }

    @Test
    fun `get categories succeeds`() {
        whenever(mockGetSaveCategoriesUseCase.get()).thenReturn(Single.just(categories))

        viewModel.getSaveCategories()

        verify(mockGetSaveCategoriesUseCase).get()
        assertEquals(categories, viewModel.listCategories.value)
    }

    @Test
    fun `get categories fail`() {
        whenever(mockGetSaveCategoriesUseCase.get()).thenReturn(Single.error(throwable))

        viewModel.getSaveCategories()

        verify(mockGetSaveCategoriesUseCase).get()
        assertEquals(throwable.message, viewModel.errorMessage.value)
    }
}
