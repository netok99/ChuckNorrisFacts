package com.chucknorrisfacts.search.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chucknorrisfacts.search.R
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}
