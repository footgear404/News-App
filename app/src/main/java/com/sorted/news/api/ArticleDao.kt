package com.sorted.news.api

import androidx.room.*
import com.sorted.news.clases.ArticleEntity
import com.sorted.news.clases.ArticleResponse

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articleentity")
    fun getAll(): List<ArticleEntity>

    @Query("DELETE FROM articleentity")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articleList: List<ArticleEntity>)

    @Delete
    fun delete(article: ArticleEntity)

    @Update
    fun updateTodo(article: ArticleEntity)

}