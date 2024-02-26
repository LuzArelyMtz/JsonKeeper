package com.example.jsonkeeper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonkeeper.R
import com.example.jsonkeeper.ui.adapter.JsonKeeperAdapter
import com.example.jsonkeeper.viewmodel.JsonKeeperViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: JsonKeeperViewModel

    private var adapter = JsonKeeperAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.rvJsonKeeper)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }

        viewModel = ViewModelProvider(this)[JsonKeeperViewModel::class.java]
        viewModel.getJsonKeeper()

        viewModel.livedataResponse.observe(this, Observer { jsonKeeperList ->
            //adapter= JsonKeeperAdapter(arrayListOf(jsonKeeperList.))
            adapter.setNewList(jsonKeeperList)
            recyclerView.adapter = adapter
        })
    }
}