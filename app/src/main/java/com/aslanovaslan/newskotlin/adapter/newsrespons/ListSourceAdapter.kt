package com.aslanovaslan.newskotlin.adapter.newsrespons

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aslanovaslan.newskotlin.R
import com.aslanovaslan.newskotlin.activity.ListNews
import com.aslanovaslan.newskotlin.interfaces.ItemClickListener
import com.aslanovaslan.newskotlin.model.website.WebSiteResponse

class ListSourceAdapter(val context: Context, private val webSiteResponse: WebSiteResponse) :
    RecyclerView.Adapter<ListSourceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.source_news_layout, parent, false)
        return ListSourceViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return webSiteResponse.sources!!.count()
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {
        holder.source_title.text = webSiteResponse.sources?.get(position)!!.name.toString()
        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context.applicationContext, ListNews::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("source", webSiteResponse.sources[position].id)
                context.startActivity(intent)
            }

        })
    }

}