package com.chucknorrisfacts.search.usecases

import com.chucknorrisfacts.search.data.repository.SearchRepository
import io.reactivex.Single

class GetSearchesUseCase constructor(private val searchRepository: SearchRepository) {

    fun get(): Single<List<String>> = searchRepository.getSearches()
}
