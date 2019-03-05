package com.chucknorrisfacts.search.presentation

import androidx.lifecycle.ViewModel
import com.chucknorrisfacts.search.usecases.SearchFactUseCase
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel constructor(private val searchFactUseCase: SearchFactUseCase) : ViewModel() {

//    val posts = MutableLiveData <<List<PostItem>>()
    private val compositeDisposable = CompositeDisposable()

    fun getRandomFact() {
    }

    fun getRandomCategoryFact(category: String) {}

    fun getCategoriesFact() {}

    fun getFact(query: String) {}

//    fun get(query: String) =
//        compositeDisposable.add(searchFactUseCase.get(query)
//            .doOnSubscribe { posts.setLoading() }
//            .subscribeOn(Schedulers.io())
//            .map { it.mapToPresentation() }
//            .subscribe({ posts.setSuccess(it) }, { posts.setError(it.message) })
//        )

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}