package com.example.jsonkeeper.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jsonkeeper.api.model.JsonKeeperItem
import com.example.jsonkeeper.databinding.JsonkeeperItemBinding

class JsonKeeperAdapter(
    private val jsonKeeperList: ArrayList<JsonKeeperItem>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            JsonkeeperItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(jsonKeeperList[position], onItemClickListener)
    }

    override fun getItemCount() = jsonKeeperList.size

    fun setNewList(newList: List<JsonKeeperItem>) {
        jsonKeeperList.clear()
        jsonKeeperList.addAll(newList)
        notifyDataSetChanged()
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