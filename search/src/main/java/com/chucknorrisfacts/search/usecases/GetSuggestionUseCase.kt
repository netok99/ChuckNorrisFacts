package com.chucknorrisfacts.search.usecases

import com.chucknorrisfacts.search.data.repository.SearchRepository

class GetSuggestionUseCase constructor(private val searchRepository: SearchRepository) {

    fun get(): List<String> = searchRepository.getCategoriesLocalFact()
}
