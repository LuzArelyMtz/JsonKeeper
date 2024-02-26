package com.example.jsonkeeper.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsonkeeper.databinding.ActivityMainBinding
import com.example.jsonkeeper.ui.adapter.JsonKeeperAdapter
import com.example.jsonkeeper.viewmodel.JsonKeeperViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: JsonKeeperViewModel

    private var adapter = JsonKeeperAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvJsonKeeper.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }

        viewModel = ViewModelProvider(this)[JsonKeeperViewModel::class.java]
        viewModel.getJsonKeeper()

        viewModel.livedataResponse.observe(this, Observer { jsonKeeperList ->
            //adapter= JsonKeeperAdapter(arrayListOf(jsonKeeperList.))
            adapter.setNewList(jsonKeeperList)
            binding.rvJsonKeeper.adapter = adapter
        })
    }
}