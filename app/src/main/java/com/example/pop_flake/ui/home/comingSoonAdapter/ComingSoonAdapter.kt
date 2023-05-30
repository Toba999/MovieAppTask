package com.example.pop_flake.ui.home.comingSoonAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pop_flake.R
import com.example.pop_flake.data.pojo.ComingSoonMovie
import com.example.pop_flake.databinding.HorizontalMovieCellBinding

class ComingSoonAdapter  : PagingDataAdapter<ComingSoonMovie, ComingSoonAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(HorizontalMovieCellBinding.
        inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MovieViewHolder(private val binding: HorizontalMovieCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: ComingSoonMovie) {
            // Bind the data to the views
            binding.titleTextView.text = movie.title
            binding.descriptionTextView.text = movie.year
            // Set other views as needed
            Glide.with(binding.imageView)
                .load(movie.image)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imageView)
        }
    }


    private class MovieDiffCallback : DiffUtil.ItemCallback<ComingSoonMovie>() {
        override fun areItemsTheSame(oldItem: ComingSoonMovie, newItem: ComingSoonMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ComingSoonMovie, newItem: ComingSoonMovie): Boolean {
            return oldItem == newItem
        }
    }
}
