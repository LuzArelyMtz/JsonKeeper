package com.example.jsonkeeper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsonkeeper.api.model.JsonKeeperItem
import com.example.jsonkeeper.databinding.ListFragmentBinding
import com.example.jsonkeeper.ui.adapter.JsonKeeperAdapter
import com.example.jsonkeeper.ui.adapter.OnItemClickListener
import com.example.jsonkeeper.viewmodel.JsonKeeperViewModel

class ListFragment : Fragment() {

    var onClickListener: (JsonKeeperItem) -> Unit = {}

    private lateinit var binding: ListFragmentBinding
    private lateinit var adapter: JsonKeeperAdapter
    private val sharedViewModel: JsonKeeperViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvJsonKeeper.layoutManager = LinearLayoutManager(context)
        adapter = JsonKeeperAdapter(arrayListOf(), object : OnItemClickListener {
            override fun onClick(v: View?, data: JsonKeeperItem) {
                sharedViewModel.setJsonKeeperItem(data)
                onClickListener(data)
            }
        })
        binding.rvJsonKeeper.adapter = adapter

        sharedViewModel.getJsonKeeper()

        sharedViewModel.livedataResponse.observe(requireActivity(), Observer { jsonKeeperList ->
            adapter.setNewList(jsonKeeperList)
        })
    }
}