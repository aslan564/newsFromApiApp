package com.aslanovaslan.newskotlin.adapter.newslist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aslanovaslan.newskotlin.R
import com.aslanovaslan.newskotlin.activity.NewsDetail
import com.aslanovaslan.newskotlin.common.ISO8601Parser
import com.aslanovaslan.newskotlin.interfaces.ItemClickListener
import com.aslanovaslan.newskotlin.model.news.Article
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.util.*

class ListNewsAdapter(private val articleList: MutableList<Article>, val context: Context) :
    RecyclerView.Adapter<ListNewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_layout, parent, false)
        return ListNewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        // Picasso.get().load(articleList[position].urlToImage).into(holder.article_image);
        Picasso.Builder(context)
            .build().load(articleList[position].urlToImage).into(holder.article_image)
        if (articleList[position].title.length > 65) {
            holder.article_title.text = articleList[position].title.substring(0, 65) + "..."
        } else
            holder.article_title.text = articleList[position].title

        if (articleList[position].publishedAt.isNotEmpty()) {
            var date: Date? = null
            try {
                date = ISO8601Parser.parse(articleList[position].publishedAt)
            } catch (ex: ParseException) {
                ex.printStackTrace()
            }
            holder.articli_time.setReferenceTime(date!!.time)
        }
        holder.setItemOnClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val detail = Intent(context.applicationContext, NewsDetail::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                detail.putExtra("webUrl",articleList[position].url)
                context.startActivity(detail)
            }

        })

    }
}