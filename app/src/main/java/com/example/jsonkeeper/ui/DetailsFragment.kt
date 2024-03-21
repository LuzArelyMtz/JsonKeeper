package com.example.jsonkeeper.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.jsonkeeper.api.model.JsonKeeperItem
import com.example.jsonkeeper.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment() {

    //private val sharedViewModel: JsonKeeperViewModel by activityViewModels()
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var context: Context
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var jsonKeeperItem:JsonKeeperItem

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

        jsonKeeperItem = args.jsonKeeperItem
        //sharedViewModel.livedataJsonKeeperItem.observe(requireActivity(), Observer {
            binding.tvDescription.text = jsonKeeperItem.title
            binding.tvDescription.text = jsonKeeperItem.description
            binding.tvDate.text = jsonKeeperItem.date
            Glide.with(context).load(jsonKeeperItem.img).into(binding.image)
       // })
    }
}