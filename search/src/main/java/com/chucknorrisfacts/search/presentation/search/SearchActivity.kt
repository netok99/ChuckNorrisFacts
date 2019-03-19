package com.chucknorrisfacts.search.presentation.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.chucknorrisfacts.search.R
import com.chucknorrisfacts.search.data.model.SearchModel
import com.chucknorrisfacts.search.presentation.facts.FactsActivity.Companion.FACTS_EXTRA
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_past_searches.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initViews()
        setListeners()

        with(viewModel) {
            searchModel.observe(this@SearchActivity, Observer {
                handleSearch(it)
            })
            errorMessage.observe(this@SearchActivity, Observer {
                showError(it)
            })
            listSearches.observe(this@SearchActivity, Observer {
                showListSearches(it)
            })
            listCategories.observe(this@SearchActivity, Observer {
                showListCategories(it)
            })
            showLoadingArea.observe(this@SearchActivity, Observer {
                showHideLoadingArea(it)
            })
            getSaveSearch()
            getSaveCategories()
        }
    }

    private fun initViews() {
        initToolbar()
        searchView.isIconified = false
        rootView.requestFocus()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.toolbar_search_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setListeners() {
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getFact(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        val editTextSearchView = searchView.findViewById(R.id.search_src_text) as EditText
        val closeButton = searchView.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            editTextSearchView.setText("")
        }
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun showListCategories(categories: List<String>) =
        gridLayoutSuggestion.apply {
            alignmentMode = GridLayout.ALIGN_BOUNDS
            columnCount = 3
            rowCount = 3

            for (index in categories.indices) {
                val category = categories[index]
                gridLayoutSuggestion.addView(
                    layoutInflater.inflate(R.layout.item_category, null, false).apply {
                        categoryTitle.text = category
                        val param = GridLayout.LayoutParams()
                        param.apply {
                            height = GridLayout.LayoutParams.WRAP_CONTENT
                            width = GridLayout.LayoutParams.WRAP_CONTENT
                            rightMargin = 12
                            leftMargin = 12
                            topMargin = 32
                            setGravity(Gravity.CENTER)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                            }
                        }
                        layoutParams = param
                        setOnClickListener {
                            viewModel.getFact(category)
                        }
                    },
                    index
                )
            }
        }

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun showListSearches(listSearches: List<String>) = listSearches.forEach { search ->
        pastSearchList.addView(
            layoutInflater.inflate(R.layout.item_past_searches, null, false).apply {
                searchTitle.text = search
                setOnClickListener {
                    viewModel.getFact(search)
                }
            })
    }

    private fun handleSearch(searchModel: SearchModel?) = searchModel?.let {
        setResult(RESULT_OK, Intent().apply {
            putExtra(FACTS_EXTRA, searchModel)
        })
        finish()
    }

    private fun showError(errorMessage: String) =
        Toast.makeText(this, getString(R.string.error_message, errorMessage), Toast.LENGTH_LONG).show()

    private fun showHideLoadingArea(isShow: Boolean) = with(loadingArea) {
        visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = finish()
}
