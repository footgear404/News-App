package com.sorted.news.clases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class Article {

    @SerializedName("title")
    @Expose
    @PrimaryKey var title:String = ""

    @SerializedName("source")
    @Expose
    @ColumnInfo(name = "source") var source: Source? = null

    @SerializedName("author")
    @Expose
    @ColumnInfo(name = "author") var author = ""

    @SerializedName("urlToImage")
    @Expose
    @ColumnInfo(name = "urlToImage") var urlToImage = ""

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description") var description = ""

    @SerializedName("publishedAt")
    @Expose
    @ColumnInfo(name = "publishedAt") var publishedAt = ""
}