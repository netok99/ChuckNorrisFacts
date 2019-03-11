package com.chucknorrisfacts.search.presentation.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
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

        viewModel.searchModel.observe(this, Observer { handleSearch(it) })
        viewModel.errorMessage.observe(this, Observer { showError(it) })
    }

    private fun initViews() {
        initToolbar()
        searchView.isIconified = false
        rootView.requestFocus()
        loadSuggestion2()
        loadPastSearches()
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

    override fun onResume() {
        super.onResume()
        searchView.setQuery("", false)
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun loadSuggestion2() {
        gridLayoutSuggestion.apply {
            alignmentMode = GridLayout.ALIGN_BOUNDS
            columnCount = 3
            rowCount = 3
        }
        for (i in 0..8) {
            gridLayoutSuggestion.addView(
                layoutInflater.inflate(R.layout.item_category, null, false).apply {
                    categoryTitle.text = "teste$i"
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
                    this.layoutParams = param
                },
                i)
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun loadPastSearches() {
        for (i in 0..3) {
            pastSearchList.addView(
                layoutInflater.inflate(R.layout.item_past_searches, null, false).apply {
                    searchTitle.text = "teste$i"
                })
        }
    }

    private fun handleSearch(searchModel: SearchModel?) {
        searchModel?.let { setResult(RESULT_OK, Intent().apply { putExtra(FACTS_EXTRA, searchModel) }) }
        finish()
    }

    private fun showError(errorMessage: String) =
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = finish()
}
