package com.example.pop_flake.ui.home.boxOfficeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pop_flake.R
import com.example.pop_flake.data.pojo.BoxOfficeMovie
import com.example.pop_flake.databinding.VerticalMovieCellBinding
import com.example.pop_flake.utils.Constants

class BoxOfficeMovieAdapter : RecyclerView.Adapter<BoxOfficeMovieAdapter.MovieViewHolder>() {
    private val movies: MutableList<BoxOfficeMovie> = mutableListOf()

    fun submitMovies(newMovies: List<BoxOfficeMovie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return  MovieViewHolder(
                    VerticalMovieCellBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(private val binding: VerticalMovieCellBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: BoxOfficeMovie) {
            // Bind the data to the views
            binding.titleTextView.text = movie.title
            binding.descriptionTextView.text = movie.gross
            Glide.with(binding.imageView)
                .load(movie.image)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imageView)

        }
    }

}
