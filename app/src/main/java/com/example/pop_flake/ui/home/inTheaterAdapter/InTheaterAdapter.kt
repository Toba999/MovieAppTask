package com.example.pop_flake.ui.home.inTheaterAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pop_flake.R
import com.example.pop_flake.data.pojo.ComingSoonMovie
import com.example.pop_flake.data.pojo.InTheaterMovie
import com.example.pop_flake.databinding.HorizontalMovieCellBinding
import com.example.pop_flake.databinding.ShimmerHorizontalBinding
import com.example.pop_flake.utils.Constants

class InTheaterAdapter  : PagingDataAdapter<InTheaterMovie, InTheaterAdapter.MovieViewHolder>(MovieDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
                    HorizontalMovieCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
    inner class MovieViewHolder(private val binding: HorizontalMovieCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: InTheaterMovie) {
            // Bind the data to the views
            binding.titleTextView.text = movie.title
            binding.descriptionTextView.text = movie.runtimeMins+" minutes"
            // Set other views as needed
            Glide.with(binding.imageView)
                .load(movie.image)
                .placeholder(R.drawable.placeholder_image) // Optional: Placeholder image while loading
                .into(binding.imageView)
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<InTheaterMovie>() {
        override fun areItemsTheSame(oldItem: InTheaterMovie, newItem: InTheaterMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: InTheaterMovie, newItem: InTheaterMovie): Boolean {
            return oldItem == newItem
        }
    }
}
