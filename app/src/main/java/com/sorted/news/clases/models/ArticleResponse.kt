package com.sorted.news.clases.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sorted.news.clases.models.Source

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

    @SerializedName("url")
    @Expose
    var url = ""
}