package com.example.pop_flake.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pop_flake.data.pojo.ComplaintModel
import com.example.pop_flake.databinding.ItemComplaintsBinding

class ComplaintsAdapter : RecyclerView.Adapter<ComplaintsAdapter.ComplaintViewHolder>() {

    private var complaints: MutableList<ComplaintModel>? = null


    inner class ComplaintViewHolder(private val rowView: ItemComplaintsBinding) :
        RecyclerView.ViewHolder(rowView.root) {
        fun onBind(item: ComplaintModel) {
            rowView.tvTitle.text = item.title
            rowView.tvDesc.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {
        return ComplaintViewHolder(
            ItemComplaintsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return complaints?.size ?: 0
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
        complaints?.get(position)?.let { holder.onBind(it) }
    }

    fun submitList(list: MutableList<ComplaintModel>) {
        complaints = list
        notifyDataSetChanged()
    }


}