package com.example.pop_flake.ui.shimmering

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pop_flake.databinding.ShimmerHorizontalBinding

class ShimmerHorizontalAdapter(private val itemCount: Int) :
    RecyclerView.Adapter<ShimmerHorizontalAdapter.ShimmerHorizontalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerHorizontalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShimmerHorizontalBinding.inflate(inflater, parent, false)
        return ShimmerHorizontalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShimmerHorizontalViewHolder, position: Int) {
        // No need to bind any data as it's a shimmer placeholder
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    inner class ShimmerHorizontalViewHolder(rowView: ShimmerHorizontalBinding) :
        RecyclerView.ViewHolder(rowView.root)
}