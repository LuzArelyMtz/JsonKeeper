package com.example.jsonkeeper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonkeeper.R
import com.example.jsonkeeper.api.model.JsonKeeperItem
import com.example.jsonkeeper.ui.adapter.JsonKeeperAdapter
import com.example.jsonkeeper.ui.adapter.OnItemClickListener
import com.example.jsonkeeper.viewmodel.JsonKeeperViewModel

class ListFragment : Fragment() {
    var onClickListener: (JsonKeeperItem) -> Unit = {}
    private val sharedViewModel: JsonKeeperViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvJsonKeeper = view.findViewById<RecyclerView>(R.id.rvJsonKeeper)
        rvJsonKeeper?.layoutManager = LinearLayoutManager(context)

        val adapter = JsonKeeperAdapter(arrayListOf(), object : OnItemClickListener {
            override fun onClick(v: View?, data: JsonKeeperItem) {
                sharedViewModel.setJsonKeeperItem(data)
                onClickListener(data)
            }
        })
        rvJsonKeeper.adapter = adapter

        sharedViewModel.getJsonKeeper()

        sharedViewModel.livedataResponse.observe(requireActivity(), Observer { jsonKeeperList ->
            adapter.setNewList(jsonKeeperList)
        })
    }
}