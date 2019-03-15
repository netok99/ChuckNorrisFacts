package com.chucknorrisfacts.search.usecases

import com.chucknorrisfacts.search.data.repository.SearchRepository

class SaveSearchUseCase constructor(private val searchRepository: SearchRepository) {

    fun save(list: List<String>, newItem: String) = searchRepository.saveSearches(
        mutableListOf<String>().apply {
            addAll(list)
            add(newItem)
        }.distinct().take(4)
    )
}
