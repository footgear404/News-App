package com.sorted.news.clases
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity (
    @PrimaryKey
    var title:String = "",

    @ColumnInfo(name = "source")
    var source: String? = "",

    @ColumnInfo(name = "author")
    var author: String? = "",

    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = "",

    @ColumnInfo(name = "description")
    var description: String? = "",

    @ColumnInfo(name = "publishedAt")
    var publishedAt: String? = ""
)