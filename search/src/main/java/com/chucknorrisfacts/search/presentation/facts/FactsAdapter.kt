package com.chucknorrisfacts.search.presentation.facts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chucknorrisfacts.search.R
import com.chucknorrisfacts.search.data.model.JokeModel
import com.chucknorrisfacts.search.data.model.SearchModel
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_fact.view.*

class FactsAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var item: SearchModel? = null

    fun addFacts(search: SearchModel) {
        item = search
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        FactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_fact, parent, false))

    override fun getItemCount(): Int = item?.total ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FactsViewHolder).bind(context, item?.result!![position])
    }

    inner class FactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(context: Context, item: JokeModel) = with(itemView) {
            title.text = item.value
            categoryTitle.text = if (item.category.isNullOrEmpty())
                context.getString(R.string.uncategorized_category) else item.category.first()

            share.setOnClickListener {
                try {
                    context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, item.value)
                    }, context.getString(R.string.share_fact)))
                } catch (ex: android.content.ActivityNotFoundException) {
                    Toast.makeText(context, context.getString(R.string.error_share_fact), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
