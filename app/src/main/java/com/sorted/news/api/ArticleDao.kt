package com.sorted.news.api

import androidx.room.*
import com.sorted.news.clases.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAll(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Delete
    fun delete(article: Article)

    @Update
    fun updateTodo(article: Article)

}