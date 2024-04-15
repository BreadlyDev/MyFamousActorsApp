package com.example.famousactorsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.famousactorsapp.api.ApiInterface
import com.example.famousactorsapp.models.FamousActorsItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var actorAdapter: ActorAdapter

    private fun searchActors() {
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    delay(1000)
                    fetchActors(query!!)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actorAdapter = ActorAdapter()

        val rvActor = findViewById<RecyclerView>(R.id.rv_actor)
        rvActor.adapter = actorAdapter

        fetchActors("Michael Jordan")
        searchActors()
    }

    private fun fetchActors(actorName: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.api-ninjas.com/v1/")
            .build().create(ApiInterface::class.java)

        val response = retrofit.getActors("4BEKNfzDXVD2qp/dLXWl8A==dNaueGiJjouWRkO1", actorName)

        response.enqueue(object : Callback<List<FamousActorsItem>> {
            override fun onResponse(
                call: Call<List<FamousActorsItem>>,
                response: Response<List<FamousActorsItem>>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    actorAdapter.updateList(responseBody)
                }

                Log.d("data", responseBody.toString())
            }

            override fun onFailure(call: Call<List<FamousActorsItem>>, t: Throwable) {

            }
        })
    }
}