package com.chucknorrisfacts.search.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chucknorrisfacts.search.data.model.SearchModel
import com.chucknorrisfacts.search.usecases.SearchFactUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel constructor(private val searchFactUseCase: SearchFactUseCase) : ViewModel() {

    val searchModel = MutableLiveData<SearchModel>()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun getRandomFact() {
    }

    fun getRandomCategoryFact(category: String) {

    }

    fun getCategoriesFact() {}

    fun getFact(query: String?) {
        query?.let { text ->
            compositeDisposable.add(searchFactUseCase.get(text)
                .doOnSubscribe { }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { searchModel.postValue(it)},
                    { errorMessage.postValue(it.message) }
                )
            )
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}