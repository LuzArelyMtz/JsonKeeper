package com.example.jsonkeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.jsonkeeper.api.IJsonKeeperAPIClient
import com.example.jsonkeeper.api.JsonKeeperAPIImpl
import com.example.jsonkeeper.api.model.JsonKeeperResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    lateinit var tvJsonResponse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvJsonResponse = findViewById(R.id.tvJsonResponse)

        val call = JsonKeeperAPIImpl().getResponse()
        call.enqueue(object : Callback<JsonKeeperResponse> {
            override fun onResponse(
                call: Call<JsonKeeperResponse>,
                response: Response<JsonKeeperResponse>
            ) {
                tvJsonResponse.text = response.body().toString()
            }

            override fun onFailure(call: Call<JsonKeeperResponse>, t: Throwable) {
                println(t.printStackTrace().toString())
            }

        })

    }
}