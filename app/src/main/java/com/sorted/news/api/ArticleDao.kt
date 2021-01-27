package com.sorted.news.api

import androidx.room.*
import com.sorted.news.clases.ArticleEntity
import com.sorted.news.clases.ArticleResponse

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articleentity")
    fun getAll(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: ArticleEntity)

    @Delete
    fun delete(article: ArticleEntity)

    @Update
    fun updateTodo(article: ArticleEntity)

}