package com.example.jsonkeeper.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jsonkeeper.R
import com.example.jsonkeeper.api.model.JsonKeeperItem

class JsonKeeperAdapter(
    private val jsonKeeperList: ArrayList<JsonKeeperItem>
) : RecyclerView.Adapter<MyViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel =
        MyViewModel(
            LayoutInflater.from(parent.context).inflate(R.layout.jsonkeeper_item, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        holder.bindView(jsonKeeperList[position])
    }

    override fun getItemCount() = jsonKeeperList.size

    fun setNewList(newList: List<JsonKeeperItem>) {
        jsonKeeperList.clear()
        jsonKeeperList.addAll(newList)
        notifyDataSetChanged()
    }
}

class MyViewModel(itemView: View) : RecyclerView.ViewHolder(itemView.rootView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
    val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
    val image = itemView.findViewById<ImageView>(R.id.imgJsonKeeper)

    fun bindView(data: JsonKeeperItem) {
        tvTitle.text = data.title
        tvDescription.text = data.description
        tvDate.text = data.date

        Glide.with(itemView.context).load(data.img).into(image)
    }
}