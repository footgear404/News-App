package com.sorted.news.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ArticlesList {

        @SerializedName("status")
        @Expose
        var status: String = ""

        @SerializedName("articles")
        @Expose
        var articles: List<Article>? = null

        @SerializedName("totalResults")
        @Expose
        var totalResults = 0

        @SerializedName("code")
        @Expose
        var code = ""

        @SerializedName("message")
        @Expose
        var message = ""

    }
