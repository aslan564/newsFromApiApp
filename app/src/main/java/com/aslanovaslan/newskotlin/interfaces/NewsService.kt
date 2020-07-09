package com.aslanovaslan.newskotlin.interfaces

import com.aslanovaslan.newskotlin.model.news.News
import com.aslanovaslan.newskotlin.model.website.WebSiteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    //https://newsapi.org/v2/sources?apiKey=30f4de7ec5a34948b78b62dda745d3c7

    @get:GET("v2/sources?apiKey=30f4de7ec5a34948b78b62dda745d3c7")
    val getvalue:Call<WebSiteResponse>

    @GET
    fun getNewsFromSources(@Url url: String):retrofit2.Call<News>
}