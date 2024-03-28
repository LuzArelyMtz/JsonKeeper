package com.example.jsonkeeper.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsonkeeper.api.model.JsonKeeperItem
import com.example.jsonkeeper.databinding.ListFragmentBinding
import com.example.jsonkeeper.di.ViewModelFactory
import com.example.jsonkeeper.ui.adapter.JsonKeeperAdapter
import com.example.jsonkeeper.ui.adapter.OnItemClickListener
import com.example.jsonkeeper.viewmodel.JsonKeeperViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: ListFragmentBinding
    private lateinit var adapter: JsonKeeperAdapter
    private lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewmodel by lazy {
        ViewModelProvider(requireActivity(), this.viewModelFactory)[JsonKeeperViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

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
        adapter = JsonKeeperAdapter(object : OnItemClickListener {
            override fun onClick(v: View?, data: JsonKeeperItem) {
                val action = ListFragmentDirections.actionListFragmentToDetailsFragment(data)
                findNavController().navigate(action)
            }
        })
        binding.rvJsonKeeper.adapter = adapter

        viewmodel.getJsonKeeper()
        viewmodel.livedataResponse.observe(requireActivity(), Observer { jsonKeeperList ->
            adapter.submitList(jsonKeeperList)
        })
    }
}