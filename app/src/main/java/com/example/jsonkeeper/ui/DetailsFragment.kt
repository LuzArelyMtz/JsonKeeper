package com.example.jsonkeeper.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.jsonkeeper.databinding.DetailsFragmentBinding
import com.example.jsonkeeper.viewmodel.JsonKeeperViewModel

class DetailsFragment : Fragment() {

    private val sharedViewModel: JsonKeeperViewModel by activityViewModels()
    private lateinit var binding: DetailsFragmentBinding
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
        binding = DetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.livedataJsonKeeperItem.observe(requireActivity(), Observer {
            binding.tvDescription.text = it.title
            binding.tvDescription.text = it.description
            binding.tvDate.text = it.date
            Glide.with(context).load(it.img).into(binding.image)
        })
    }
}