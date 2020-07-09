package com.aslanovaslan.newskotlin.common

import com.aslanovaslan.newskotlin.interfaces.NewsService
import com.aslanovaslan.newskotlin.remote.RetroftClient
import java.lang.StringBuilder

//30f4de7ec5a34948b78b62dda745d3c7
object Coomon {
    private const val BASE_URL = "https://newsapi.org/"
    private const val API_KEY = "30f4de7ec5a34948b78b62dda745d3c7"

    val newsService: NewsService
        get() = RetroftClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source: String): String {
        return StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
    }

}