package com.sorted.news.clases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ArticleResponse {

    @SerializedName("title")
    @Expose
    var title:String = ""

    @SerializedName("source")
    @Expose
    var source: Source? = null

    @SerializedName("author")
    @Expose
    var author = ""

    @SerializedName("urlToImage")
    @Expose
    var urlToImage = ""

    @SerializedName("description")
    @Expose
    var description = ""

    @SerializedName("publishedAt")
    @Expose
    var publishedAt = ""
}