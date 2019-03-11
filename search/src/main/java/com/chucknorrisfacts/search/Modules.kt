package com.chucknorrisfacts.search

import com.chucknorrisfacts.cache.ReactiveCache
import com.chucknorrisfacts.network.createNetworkClient
import com.chucknorrisfacts.search.data.datasource.SearchCacheDataSource
import com.chucknorrisfacts.search.data.datasource.SearchRemoteDataSource
import com.chucknorrisfacts.search.data.local.SearchCacheDataSourceImpl
import com.chucknorrisfacts.search.presentation.search.SearchViewModel
import com.chucknorrisfacts.search.data.remote.SearchApi
import com.chucknorrisfacts.search.data.remote.SearchRemoteApiImpl
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
    single { SearchRepositoryImpl(searchCacheDataSource = get(), searchRemoteDataSource = get()) as SearchRepository }
}

private const val BASE_URL = "https://api.chucknorris.io/jokes/"
private val retrofit: Retrofit = createNetworkClient(BASE_URL, BuildConfig.DEBUG)
private val chuckNorrisApi: SearchApi = retrofit.create(SearchApi::class.java)

val dataSourceModule: Module = module {
    single { SearchRemoteApiImpl(api = chuckNorrisApi) as SearchRemoteDataSource }
    single {
        SearchCacheDataSourceImpl(cacheCategoriesFact = get(CATEGORIES), cacheSearches = get(SEARCHES))
                as SearchCacheDataSource
    }
}

val cacheModule: Module = module {
    single(name = CATEGORIES) { ReactiveCache<List<String>>() }
    single(name = SEARCHES) { ReactiveCache<List<String>>() }
}

val networkModule: Module = module {
    single { chuckNorrisApi }
}

private const val CATEGORIES = "CATEGORIES"
private const val SEARCHES = "SEARCHES"