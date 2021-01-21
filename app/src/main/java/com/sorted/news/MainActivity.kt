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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sorted.news.api.Interface
import com.sorted.news.clases.*
import com.sorted.news.data.NewsDatabase
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    val TAG = "myLog"
    val BASE_URL = "http://newsapi.org/v2/"
    val LANGUAGE = "ru"
    // val API_KEY = "c5bc275364534b0793db86efd2c932d7" // - запасной 100 запросов в сутки foot4040
    // val API_KEY = "1a6fb5e756684298b67fbd7e9d8ffd77" // - чужой api
    val API_KEY = "421501f8d37543ec834392520c8b2e36" // - треш сток

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNews("")

    }

    private fun getNews(keyword: String) {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(Interface::class.java)
        val call: Call<ArticlesList>
        call = if (keyword.isNotEmpty()) {
            api.getNewsSearch(keyword, LANGUAGE, "publishedAt", API_KEY)
        } else {
            api.getNews(LANGUAGE, API_KEY)
        }

        call.enqueue(object : Callback<ArticlesList> {
            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<ArticlesList>, response: Response<ArticlesList>) {
                Log.d(TAG, "${response.code()}")
                if (response.code() == 200) {

                    val feedback = response.body()
                    Log.d(TAG, "Статус: ${feedback?.status}")
                    Log.d(TAG, "Кол-во получено: ${feedback?.totalResults}")
                    val article = feedback?.articles
                    Log.d(TAG, "Всего записей: ${article!!.size}")
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)
                    val adapter = Adapter(article)
                    addToDb()
                    recyclerView.adapter = adapter
                }
                else {
                    val type = object : TypeToken<ArticlesList>() {}.type
                    val errorResponse: ArticlesList? = Gson().fromJson(response.errorBody()!!.charStream(), type)
                    Log.e(TAG, "${errorResponse?.message}")
                    Toast.makeText(this@MainActivity, errorResponse?.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ArticlesList>, t: Throwable) {
                Log.d(TAG, t.toString())
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
    fun addToDb(){
        GlobalScope.launch {
            Log.d(TAG, "GlobalScope -> addToDb")
//            val db = NewsDatabase(this@MainActivity)
//            db.articleDao().insert(Article(**********))
//            val data = db.articleDao().getAll()
//
//            data.forEach {
//                println(it)
//                Log.d("MyTag", "$it")
//            }
        }
    }
}

