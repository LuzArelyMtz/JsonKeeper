package com.example.jsonkeeper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jsonkeeper.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listFragment = ListFragment().apply {
            onClickListener = { selectedInfo ->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, DetailsFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, listFragment)
            .commit()
    }
}