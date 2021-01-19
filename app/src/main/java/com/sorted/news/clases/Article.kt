package com.sorted.news.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Article {

    @SerializedName("source")
    @Expose
    var source: Source? = null

    @SerializedName("author")
    @Expose
    var author = ""

    @SerializedName("title")
    @Expose
    var title = ""

    @SerializedName("url")
    @Expose
    var url = ""

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