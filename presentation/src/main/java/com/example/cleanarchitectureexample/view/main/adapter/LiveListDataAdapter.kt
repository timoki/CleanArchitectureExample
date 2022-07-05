package com.example.cleanarchitectureexample.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitectureexample.databinding.ItemLiveListBinding
import com.example.domain.model.live.LiveListModel

class LiveListDataAdapter :
    PagingDataAdapter<LiveListModel, LiveListDataAdapter.Holder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemLiveListBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class Holder(
        private val binding: ItemLiveListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LiveListModel) = with(binding) {
            info = item
            executePendingBindings()
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<LiveListModel>() {
            override fun areItemsTheSame(
                oldItem: LiveListModel,
                newItem: LiveListModel
            ): Boolean = oldItem.userId == newItem.userId

            override fun areContentsTheSame(
                oldItem: LiveListModel,
                newItem: LiveListModel
            ): Boolean = oldItem == newItem
        }
    }
}