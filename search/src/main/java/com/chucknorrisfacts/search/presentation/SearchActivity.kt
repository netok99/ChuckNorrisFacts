package com.chucknorrisfacts.search.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chucknorrisfacts.search.R
import com.chucknorrisfacts.search.injectFeature
import org.koin.android.viewmodel.ext.viewModel

class SearchActivity : AppCompatActivity() {

    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        injectFeature()
    }
}
