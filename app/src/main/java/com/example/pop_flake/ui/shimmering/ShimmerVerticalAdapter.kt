package com.example.pop_flake.ui.shimmering

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pop_flake.databinding.ShimmerVerticalBinding


class ShimmerVerticalAdapter(private val itemCount: Int) :
    RecyclerView.Adapter<ShimmerVerticalAdapter.ShimmerVerticalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerVerticalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShimmerVerticalBinding.inflate(inflater, parent, false)
        return ShimmerVerticalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShimmerVerticalViewHolder, position: Int) {
        // No need to bind any data as it's a shimmer placeholder
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    inner class ShimmerVerticalViewHolder(rowView: ShimmerVerticalBinding) :
        RecyclerView.ViewHolder(rowView.root)
}