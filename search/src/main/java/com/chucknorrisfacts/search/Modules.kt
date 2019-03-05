package com.chucknorrisfacts.search

import com.chucknorrisfacts.network.createNetworkClient
import com.chucknorrisfacts.search.data.datasource.SearchDataSource
import com.chucknorrisfacts.search.presentation.SearchViewModel
import com.chucknorrisfacts.search.data.remote.SearchApi
import com.chucknorrisfacts.search.data.remote.SearchApiImpl
import com.chucknorrisfacts.search.data.repository.SearchRepository
import com.chucknorrisfacts.search.data.repository.SearchRepositoryImpl
import com.chucknorrisfacts.search.usecases.SearchFactUseCase
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit

val viewModelModule: Module = module {
    viewModel { SearchViewModel(searchFactUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { SearchFactUseCase(searchRepository = get()) }
}

val repositoryModule: Module = module {
    single { SearchRepositoryImpl(searchDataSource = get()) as SearchRepository }
}

private const val BASE_URL = "https://api.chucknorris.io/jokes/"
private val retrofit: Retrofit = createNetworkClient(BASE_URL, BuildConfig.DEBUG)
private val chuckNorrisApi: SearchApi = retrofit.create(SearchApi::class.java)

val dataSourceModule: Module = module {
    single { SearchApiImpl(api = chuckNorrisApi) as SearchDataSource }
}

val networkModule: Module = module {
    single { chuckNorrisApi }
}
