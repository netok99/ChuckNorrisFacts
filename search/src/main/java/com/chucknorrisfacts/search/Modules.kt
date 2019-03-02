package com.chucknorrisfacts.search

import com.chucknorrisfacts.network.createNetworkClient
import com.chucknorrisfacts.search.data.datasource.SearchDataSource
import com.chucknorrisfacts.search.presentation.SearchViewModel
import com.chucknorrisfacts.search.data.remote.SearchApi
import com.chucknorrisfacts.search.data.remote.SearchApiImpl
import com.chucknorrisfacts.search.data.repository.SearchRepository
import com.chucknorrisfacts.search.data.repository.SearchRepositoryImpl
import com.chucknorrisfacts.search.usecases.SearchUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule,
        useCaseModule,
        repositoryModule,
        dataSourceModule,
        networkModule
    )
}

val viewModelModule: Module = module {
    viewModel { SearchViewModel(searchUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { SearchUseCase(searchRepository = get()) }
}

val repositoryModule: Module = module {
    single { SearchRepositoryImpl(searchDataSource = get()) as SearchRepository }
}

val dataSourceModule: Module = module {
    single { SearchApiImpl(api = chuckNorrisApi) as SearchDataSource }
}

val networkModule: Module = module {
    single { chuckNorrisApi }
}

private const val BASE_URL = "https://api.chucknorris.io/jokes/"
private val retrofit: Retrofit = createNetworkClient(BASE_URL, BuildConfig.DEBUG)
private val chuckNorrisApi: SearchApi = retrofit.create(SearchApi::class.java)