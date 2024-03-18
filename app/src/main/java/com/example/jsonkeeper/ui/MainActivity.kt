package com.example.jsonkeeper.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jsonkeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listFragment = ListFragment().apply {
            onClickListener = { selectedInfo ->
                supportFragmentManager.beginTransaction()
                    .replace(binding.mainFragmentContainer.id, DetailsFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.mainFragmentContainer.id, listFragment)
            .commit()
    }
}