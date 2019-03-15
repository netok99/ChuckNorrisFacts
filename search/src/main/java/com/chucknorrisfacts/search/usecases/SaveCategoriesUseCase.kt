package com.chucknorrisfacts.search.usecases

import com.chucknorrisfacts.search.data.model.SearchModel
import com.chucknorrisfacts.search.data.repository.SearchRepository

class SaveCategoriesUseCase constructor(private val searchRepository: SearchRepository) {

    fun save(lastCategories: List<String>, searchModel: SearchModel) = searchRepository.saveCategoriesFact(
        mutableListOf<String>().apply {
            addAll(lastCategories)
            searchModel.result.map { it.category }.forEach { joker ->
                joker?.let { listCategory ->
                    listCategory.forEach {
                        this.add(it)
                    }
                }
            }
        }.distinct().takeLast(9)
    )
}
