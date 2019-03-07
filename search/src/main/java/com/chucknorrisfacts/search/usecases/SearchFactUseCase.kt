package com.chucknorrisfacts.search.usecases

import com.chucknorrisfacts.search.data.model.SearchModel
import com.chucknorrisfacts.search.data.repository.SearchRepository
import io.reactivex.Single

class SearchFactUseCase constructor(private val searchRepository: SearchRepository) {

    fun get(query: String): Single<SearchModel> =
        searchRepository.getFact(query)
}
