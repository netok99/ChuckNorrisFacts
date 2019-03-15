package com.chucknorrisfacts.search.presentation.facts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chucknorrisfacts.search.R
import com.chucknorrisfacts.search.data.model.SearchModel
import com.chucknorrisfacts.search.presentation.search.SearchActivity
import kotlinx.android.synthetic.main.activity_facts.*

class FactsActivity : AppCompatActivity() {

    lateinit var factsAdapter: FactsAdapter
    private var searchModel: SearchModel? = null

    companion object {
        const val FACTS_REQUEST_CODE = 1
        const val FACTS_EXTRA = "factsExtra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        initFactsList()
    }

    private fun initFactsList() = recyclerView.apply {
        factsAdapter = FactsAdapter(this@FactsActivity)
        layoutManager = LinearLayoutManager(this@FactsActivity)
        isNestedScrollingEnabled = false
        adapter = factsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.facts_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_search -> {
            startActivityForResult(
                Intent(this, SearchActivity::class.java),
                FACTS_REQUEST_CODE
            )
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = finish()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == FACTS_REQUEST_CODE) {
            data?.getParcelableExtra<SearchModel>(FACTS_EXTRA)?.let {
                searchModel = it
                factsAdapter.addFacts(it)
                emptyState.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }
    }
}
