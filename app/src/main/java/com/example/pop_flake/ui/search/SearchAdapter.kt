package com.example.pop_flake.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pop_flake.R
import com.example.pop_flake.data.pojo.MovieResult
import com.example.pop_flake.databinding.VerticalMovieCellBinding


class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MovieViewHolder>() {
    private val movies: MutableList<MovieResult> = mutableListOf()

    fun submitMovies(newMovies: List<MovieResult>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            VerticalMovieCellBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(private val binding: VerticalMovieCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieResult) {
            // Bind the data to the views
            binding.titleTextView.text = movie.title
            binding.descriptionTextView.text = movie.description
            Glide.with(binding.imageView)
                .load(movie.image)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imageView)
        }
    }
}