package com.sorted.news.clases
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class ErrorResponse {


    @SerializedName("status")
    @Expose
    var status = ""

    @SerializedName("code")
    @Expose
    var code = ""

    @SerializedName("message")
    @Expose
    var message = ""

}