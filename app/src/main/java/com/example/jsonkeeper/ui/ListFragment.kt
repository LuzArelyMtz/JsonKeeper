package com.example.jsonkeeper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsonkeeper.api.model.JsonKeeperItem
import com.example.jsonkeeper.databinding.ListFragmentBinding
import com.example.jsonkeeper.ui.adapter.JsonKeeperAdapter
import com.example.jsonkeeper.ui.adapter.OnItemClickListener
import com.example.jsonkeeper.viewmodel.JsonKeeperViewModel
import androidx.navigation.fragment.findNavController

class ListFragment : Fragment() {
    private lateinit var binding: ListFragmentBinding
    private lateinit var adapter: JsonKeeperAdapter
    //private val sharedViewModel: JsonKeeperViewModel by activityViewModels()
    private lateinit var viewmodel: JsonKeeperViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(this)
            .get(JsonKeeperViewModel::class.java)

        binding.rvJsonKeeper.layoutManager = LinearLayoutManager(context)
        adapter = JsonKeeperAdapter(arrayListOf(), object : OnItemClickListener {
            override fun onClick(v: View?, data: JsonKeeperItem) {
                viewmodel.setJsonKeeperItem(data)

                val action = ListFragmentDirections.actionListFragmentToDetailsFragment(data)
                findNavController().navigate(action)

            }
        })
        binding.rvJsonKeeper.adapter = adapter

        viewmodel.getJsonKeeper()

        viewmodel.livedataResponse.observe(requireActivity(), Observer { jsonKeeperList ->
            adapter.setNewList(jsonKeeperList)
        })
    }
}