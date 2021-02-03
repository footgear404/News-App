package com.sorted.news

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sorted.news.adapter.Adapter
import com.sorted.news.interfaces.Interface
import com.sorted.news.clases.*
import com.sorted.news.clases.models.ArticleResponse
import com.sorted.news.clases.models.ArticlesList
import com.sorted.news.clases.models.ArticleEntity
import com.sorted.news.data.NewsDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val TAG = "myLog"
const val BASE_URL = "http://newsapi.org/v2/"
const val LANGUAGE = "ru"

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    Adapter.OnItemClickListener {
    // val API_KEY = "c5bc275364534b0793db86efd2c932d7" // - запасной 100 запросов в сутки foot4040
     val API_KEY = "1a6fb5e756684298b67fbd7e9d8ffd77" // - чужой api
    //val API_KEY = "421501f8d37543ec834392520c8b2e36" // - треш сток

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNews("")
        swipe_refresh_layout.setOnRefreshListener(this)
    }

    @SuppressLint("WrongConstant")
    private fun getNews(keyword: String) {
        swipe_refresh_layout.isRefreshing = true

        if (isOnline(this@MainActivity)){
            Log.d(TAG, "Internet connection result: ${isOnline(this@MainActivity)}")
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
                override fun onResponse(call: Call<ArticlesList>, response: Response<ArticlesList>) {
                    Log.d(TAG, "Response code: ${response.code()}")
                    if (response.code() == 200) {
                        val feedback = response.body()
                        Log.d(TAG, "Status: ${feedback?.status}")
                        val articles = feedback?.articles!!
                        Log.d(TAG, "Article list size: ${articles.size}")

                        recyclerViewBuilderFromArticleResponse(articles)
                        addToDb(articles)
                        swipe_refresh_layout.isRefreshing = false

                    } else {
                        val type = object : TypeToken<ArticlesList>() {}.type
                        val errorResponse: ArticlesList? = Gson().fromJson(response.errorBody()!!.charStream(), type)
                        Log.e(TAG, "${errorResponse?.message}")
                        Toast.makeText(this@MainActivity, errorResponse?.message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ArticlesList>, t: Throwable) {
                    Log.e(TAG, "onFailure response: $t")
                    recyclerViewBuildFromDb(keyword)
                }
            })
        } else {
            swipe_refresh_layout.isRefreshing = false
            Log.d(TAG, "Check your internet connection.")
            Toast.makeText(this, "Offline mode: \nCheck your internet connection.", Toast.LENGTH_SHORT).show()
            recyclerViewBuildFromDb(keyword)
        }
    }

    @SuppressLint("WrongConstant")
    private fun recyclerViewBuildFromDb(keyword: String) {
        var data: List<ArticleEntity>
        runBlocking(Dispatchers.IO) {
            val db = NewsDatabase(this@MainActivity)
            data = db.articleDao().search(keyword)
//            data.forEach {
//                Log.d(TAG, it.toString())
//            }
        }
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)
            val adapter = Adapter(data, this)
            recyclerView.adapter = adapter
    }

    @SuppressLint("WrongConstant")
    private fun recyclerViewBuilderFromArticleResponse(articles: List<ArticleResponse>) {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)
        val adapter = Adapter(ArticleMapper().returnArticleListEntity(articles), this)
        recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        val searchMenuItem = menu.findItem(R.id.action_search)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Введите запрос..."

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
                    if (newText.length > 2 || newText.isEmpty()) {
                        getNews(newText)
                    }
                }
                return false
            }
        })
        searchMenuItem.icon.setVisible(false, false)
        return true
    }

    fun addToDb(article: List<ArticleResponse>) {
        GlobalScope.launch {
            val db = NewsDatabase(this@MainActivity)
            var data = db.articleDao().getAll()
            if (data.size > 100) {
                db.articleDao().deleteAll()
            }
            db.articleDao().insert(ArticleMapper().returnArticleListEntity(article))
            data = db.articleDao().getAll()
            data.forEach { Log.i(TAG, "$it") }
            Log.i(TAG, "Article size: ${data.size}")
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onRefresh() {
        getNews("")
    }

    override fun onItemClick(item: ArticleEntity, position: Int) {
        // Toast.makeText(this, item.url, Toast.LENGTH_LONG).show()
        val browserIntent = Intent(this, WebViewActivity::class.java)
        browserIntent.putExtra("articleUrl", item.url)
        startActivity(browserIntent)
    }
}

