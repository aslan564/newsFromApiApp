package com.aslanovaslan.newskotlin.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslanovaslan.newskotlin.R
import com.aslanovaslan.newskotlin.adapter.newslist.ListNewsAdapter
import com.aslanovaslan.newskotlin.common.Coomon
import com.aslanovaslan.newskotlin.interfaces.NewsService
import com.aslanovaslan.newskotlin.model.news.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ListNews : AppCompatActivity() {

    private var sourceFromIntent = ""
    var webHotUrl: String? = ""

    lateinit var dialog: AlertDialog
    private lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Coomon.newsService
        dialog = SpotsDialog(this)
        swipe_to_refresh_news.setOnRefreshListener { loadNews(sourceFromIntent, true) }
        diaqonalLayout.setOnClickListener {
            val detail =
                Intent(this, NewsDetail::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            detail.putExtra("webUrl",webHotUrl)
            startActivity(detail)

        }
        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)



        if (intent != null) {
            this.sourceFromIntent = intent.getStringExtra("source")
            if (sourceFromIntent.isNotEmpty()) {
                loadNews(sourceFromIntent, false)
            }
        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {
        if (isRefreshed) {
            dialog.show()
            val url = Coomon.getNewsAPI(source!!)
            mService.getNewsFromSources(url)
                .enqueue(object : retrofit2.Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        Toast.makeText(
                            this@ListNews,
                            "refres response eror${call.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        t.printStackTrace()
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        // println("refres oldu *********${response.body().toString()} ")
                        // Toast.makeText(this@ListNews, "refres response", Toast.LENGTH_SHORT).show()

                        dialog.cancel()
                        Picasso.get().load(response.body()!!.articles[0].urlToImage).into(top_image)
                        top_title.text = response.body()!!.articles[0].title.toString()
                        top_author.text = response.body()!!.articles[0].author.toString()
                        webHotUrl = response.body()!!.articles[0].url.toString()

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem.removeAt(0)
                        adapter = ListNewsAdapter(removeFirstItem, baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }

                })

        } else {
            dialog.show()
            val url = Coomon.getNewsAPI(source!!)
            mService.getNewsFromSources(url)
                .enqueue(object : Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        t.printStackTrace()
                        Toast.makeText(this@ListNews, t.message.toString(), Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        swipe_to_refresh_news.isRefreshing = false
                        Picasso.get().load(response.body()!!.articles[0].urlToImage).into(top_image)
                        top_title.text = response.body()!!.articles[0].title.toString()
                        top_author.text = response.body()!!.articles[0].author.toString()
                        webHotUrl = response.body()!!.articles[0].url.toString()

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem.removeAt(0)
                        adapter = ListNewsAdapter(removeFirstItem, this@ListNews)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                        dialog.cancel()

                    }

                })
        }
    }
}