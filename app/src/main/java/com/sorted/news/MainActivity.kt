package com.sorted.news

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sorted.news.api.Interface
import com.sorted.news.clases.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    val TAG = "myLog"

    val BASE_URL = "http://newsapi.org/v2/"
    val LANGUAGE = "ru"
    val API_KEY = "c5bc275364534b0793db86efd2c932d7" // - запасной 100 запросов в сутки
    // val API_KEY = "421501f8d37543ec834392520c8b2e36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNews("")

    }

    private fun getNews(keyword: String) {
        Log.d(TAG, "start fun getNews")
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.d(TAG, "start create api #49")
        val api = retrofit.create(Interface::class.java)
        val call: Call<ArticlesList>
        Log.d(TAG, "start call #52")
        if (keyword.isNotEmpty()) {
            call =  api.getNewsSearch(keyword, LANGUAGE, "publishedAt", API_KEY)
            Log.d(TAG, "call #53 is DONE")
        } else {
            call = api.getArticles(LANGUAGE, API_KEY)
            Log.d(TAG, "call #56 is DONE")
        }

        call.enqueue(object : Callback<ArticlesList> {
            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<ArticlesList>, response: Response<ArticlesList>) {
                Log.d(TAG, "${response.code()}")
                if (response.code() == 200) {
                    val feedback = response.body()
                    Log.d(TAG, "${feedback?.status}")
                    val article = feedback?.articles
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)
                    val adapter = Adapter(article!!)
                    recyclerView.adapter = adapter
                }
                else {
                    val feedback = response.body()
                    Log.d(TAG, "${feedback?.status}")
//                    val error = feedback?.status
//                    Toast.makeText(this@MainActivity, error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ArticlesList>, t: Throwable) {
                Log.d("Error", t.toString())
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        val searchMenuItem = menu.findItem(R.id.action_search)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search latest news..."
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.length < 2) {
                        getNews(query)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    getNews(newText)
                }
                return false
            }
        })
        searchMenuItem.icon.setVisible(false, false)
        return true
    }

}

