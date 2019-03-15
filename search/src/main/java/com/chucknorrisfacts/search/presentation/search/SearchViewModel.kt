package com.chucknorrisfacts.search.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chucknorrisfacts.search.data.model.SearchModel
import com.chucknorrisfacts.search.usecases.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel constructor(
    private val searchFactUseCase: SearchFactUseCase,
    private val saveSearchUseCase: SaveSearchUseCase,
    private val getSearchesUseCase: GetSearchesUseCase,
    private val getSaveCategoriesUseCase: GetSaveCategoriesUseCase,
    private val saveCategoriesUseCase: SaveCategoriesUseCase
) : ViewModel() {

    val searchModel = MutableLiveData<SearchModel>()
    val listSearches = MutableLiveData<List<String>>()
    val listCategories = MutableLiveData<List<String>>()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val showLoadingArea: MutableLiveData<Boolean> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    private var lastSearches = mutableListOf<String>()
    private var lastCategories = mutableListOf<String>()

    fun getFact(query: String?) {
        query?.let { text ->
            showLoadingArea.postValue(true)
            compositeDisposable.add(searchFactUseCase.get(text)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { searchModel ->
                        saveSearchUseCase.save(lastSearches, query)
                        saveCategoriesUseCase.save(lastCategories, searchModel)
                        this.searchModel.postValue(searchModel)
                    },
                    {
                        showLoadingArea.postValue(false)
                        errorMessage.postValue(it.message)
                    }
                )
            )
        }
    }

    fun getSaveSearch() = compositeDisposable.add(getSearchesUseCase.get()
        .subscribeOn(Schedulers.io())
        .subscribe(
            {
                setListSearches(it)
                listSearches.postValue(it)
            },
            { errorMessage.postValue(it.message) }
        )
    )

    fun getSaveCategories() = compositeDisposable.add(getSaveCategoriesUseCase.get()
        .doOnSubscribe { }
        .subscribeOn(Schedulers.io())
        .subscribe(
            {
                setListCategories(it)
                listCategories.postValue(it)
            },
            { errorMessage.postValue(it.message) }
        )
    )

    private fun setListSearches(listSearches: List<String>) = with(lastSearches) {
        clear()
        addAll(listSearches)
    }

    private fun setListCategories(listCategories: List<String>) = with(lastCategories) {
        clear()
        addAll(listCategories)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
