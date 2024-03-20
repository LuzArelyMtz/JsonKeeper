package com.example.jsonkeeper.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.jsonkeeper.R
import com.example.jsonkeeper.viewmodel.JsonKeeperViewModel

class DetailsFragment: Fragment() {
    private val sharedViewModel: JsonKeeperViewModel by activityViewModels()
    private lateinit var context: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        val image = view.findViewById<ImageView>(R.id.image)

        sharedViewModel.livedataJsonKeeperItem.observe(requireActivity(), Observer {
            tvTitle.text = it.title
            tvDescription.text = it.description
            tvDate.text = it.date
            Glide.with(context).load(it.img).into(image)
        })
    }
}