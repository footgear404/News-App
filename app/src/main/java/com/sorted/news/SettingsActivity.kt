package com.sorted.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.sorted.news.clases.ArticleMapper
import com.sorted.news.clases.models.ArticleEntity
import com.sorted.news.data.NewsDatabase
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        title = "Настройки"

        var data: List<ArticleEntity>

        runBlocking(Dispatchers.IO) {
            val db = NewsDatabase(this@SettingsActivity)
            data = db.articleDao().getAll()
        }
        tvDbSizeCount.text = data.size.toString()


        btClearCache.setOnClickListener{
            runBlocking(Dispatchers.IO) {
                val db = NewsDatabase(this@SettingsActivity)
                db.articleDao().deleteAll()
            }

            tvDbSizeCount.text = "0"
            Toast.makeText(this, "Записи успешно удалены", Toast.LENGTH_SHORT).show()
        }




    }
}