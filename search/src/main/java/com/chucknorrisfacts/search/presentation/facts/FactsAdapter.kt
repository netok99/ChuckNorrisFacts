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
import kotlinx.android.synthetic.main.item_fact_long.view.*

class FactsAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var item: SearchModel? = null
    private val normalItemType = 0
    private val longItemType = 1

    fun addFacts(search: SearchModel) {
        item = search
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = when (item?.result!![position].value.length) {
        in 0..80 -> normalItemType
        else -> longItemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        normalItemType -> FactsNormalViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fact, parent, false)
        )
        else -> FactsLongViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_fact_long, parent, false))
    }

    override fun getItemCount(): Int = item?.total ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder.itemViewType) {
        normalItemType -> (holder as FactsNormalViewHolder).bind(context, item?.result!![position])
        else -> (holder as FactsLongViewHolder).bind(context, item?.result!![position])
    }

    inner class FactsNormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

    inner class FactsLongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(context: Context, item: JokeModel) = with(itemView) {
            titleLong.text = item.value
            categoryTitle.text = if (item.category.isNullOrEmpty())
                context.getString(R.string.uncategorized_category) else item.category.first()

            shareLong.setOnClickListener {
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
