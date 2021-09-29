package net.simplifiedcoding.multiviewlist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.simplifiedcoding.multiviewlist.R
import net.simplifiedcoding.multiviewlist.databinding.ItemDirectorBinding
import net.simplifiedcoding.multiviewlist.databinding.ItemMovieBinding
import net.simplifiedcoding.multiviewlist.databinding.ItemTitleBinding
import java.lang.IllegalArgumentException

class HomeRecyclerViewAdapter : RecyclerView.Adapter<HomeRecyclerViewHolder>() {

    var items = listOf<HomeRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: ((view: View, item: HomeRecyclerViewItem, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        val directorBinding =
            ItemDirectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val movieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val titleBinding =
            ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return when (viewType) {
            R.layout.item_title -> HomeRecyclerViewHolder.TitleViewHolder(titleBinding)
            R.layout.item_movie -> HomeRecyclerViewHolder.MovieViewHolder(movieBinding)
            R.layout.item_director -> HomeRecyclerViewHolder.DirectorViewHolder(directorBinding)
            else -> throw IllegalArgumentException("Invalid view type provided!")
        }
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {

        holder.itemClickListener = itemClickListener
        when (holder) {
            is HomeRecyclerViewHolder.DirectorViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.Director)
            is HomeRecyclerViewHolder.MovieViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.Movie)
            is HomeRecyclerViewHolder.TitleViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.Title)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeRecyclerViewItem.Director -> R.layout.item_director
            is HomeRecyclerViewItem.Movie -> R.layout.item_movie
            is HomeRecyclerViewItem.Title -> R.layout.item_title
        }
    }

    override fun getItemCount() = items.size
}