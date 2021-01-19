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
    var url = ""

    @SerializedName("urlToImage")
    @Expose
    var urlToImage = ""

    var content = ""
    @SerializedName("description")
    @Expose
    var description = ""

    var publishedAt = ""
}