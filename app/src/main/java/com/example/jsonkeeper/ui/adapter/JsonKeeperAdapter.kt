package com.example.jsonkeeper.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jsonkeeper.api.model.JsonKeeperItem
import com.example.jsonkeeper.databinding.JsonkeeperItemBinding

class JsonKeeperAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<JsonKeeperItem, RecyclerView.ViewHolder>(ListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            JsonkeeperItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bind(getItem(position), onItemClickListener)
    }

}

class MyViewHolder(private val binding: JsonkeeperItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: JsonKeeperItem, listener: OnItemClickListener) {
        binding.tvTitle.text = data.title
        binding.tvDescription.text = data.description
        binding.tvDate.text = data.date

        Glide.with(binding.root.context).load(data.img).into(binding.imgJsonKeeper)
        itemView.setOnClickListener({
            listener.onClick(itemView, data)
        })
    }
}

class ListDiffCallback : DiffUtil.ItemCallback<JsonKeeperItem>() {
    override fun areItemsTheSame(oldItem: JsonKeeperItem, newItem: JsonKeeperItem) =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: JsonKeeperItem, newItem: JsonKeeperItem) = oldItem == newItem
}