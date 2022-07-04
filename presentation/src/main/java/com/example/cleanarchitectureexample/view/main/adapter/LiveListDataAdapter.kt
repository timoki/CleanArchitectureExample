package com.example.cleanarchitectureexample.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitectureexample.databinding.ItemLiveListBinding
import com.example.domain.model.live.LiveListModel

class LiveListDataAdapter: PagingDataAdapter<LiveListModel, LiveListDataAdapter.Holder>(COMPARATOR) {

    private lateinit var mBinding: ItemLiveListBinding

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        mBinding = ItemLiveListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(mBinding)
    }

    class Holder(binding: ItemLiveListBinding): RecyclerView.ViewHolder(binding.root) {

        private val viewModel: LivelistAdapterViewModel = LivelistAdapterViewModel()

        init {
            binding.viewModel = viewModel
        }
        fun bind(item: LiveListModel?) {
            item?.let {
                viewModel.setItem(it)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<LiveListModel>() {
            override fun areItemsTheSame(
                oldItem: LiveListModel,
                newItem: LiveListModel)
                    : Boolean =
                oldItem.code == newItem.code


            override fun areContentsTheSame(
                oldItem: LiveListModel,
                newItem: LiveListModel
            ): Boolean =
                oldItem == newItem

        }
    }
}