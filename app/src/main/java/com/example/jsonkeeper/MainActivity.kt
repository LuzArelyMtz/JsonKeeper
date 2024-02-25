package com.example.jsonkeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.jsonkeeper.api.JsonKeeperAPIImpl
import com.example.jsonkeeper.api.model.JsonKeeperResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var tvJsonResponse: TextView
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvJsonResponse = findViewById(R.id.tvJsonResponse)

        disposable.add(
            JsonKeeperAPIImpl().getResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonKeeperResponse>() {
                    override fun onSuccess(response: JsonKeeperResponse) {
                        tvJsonResponse.text = response.toString()
                    }

                    override fun onError(e: Throwable) {
                        println(e.printStackTrace())
                    }
                })
        )
    }
}