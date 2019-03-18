package com.chucknorrisfacts.search.usecases

import com.chucknorrisfacts.search.data.repository.SearchRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class SaveSearchUseCaseTest {

    private lateinit var usecase: SaveSearchUseCase
    private val mockRepository: SearchRepository = mock()

    @Before
    fun setUp() {
        usecase = SaveSearchUseCase(mockRepository)
    }

    @Test
    fun `Save Search more 4 items success`() {
        val query = "dev"
        val searchList = listOf("test", "hello", "test", "chuck")
        val expectResult = listOf("hello", "test", "chuck", "dev")

        usecase.save(searchList, query)

        verify(mockRepository).saveSearches(expectResult)
    }

    @Test
    fun `Save Search items success`() {
        val query = "dev"
        val searchList = listOf("test", "hello", "test")
        val expectResult = listOf("hello", "test", "chuck", "dev")

        usecase.save(searchList, query)

        verify(mockRepository).saveSearches(expectResult)
    }
}
