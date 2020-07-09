package com.aslanovaslan.newskotlin.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslanovaslan.newskotlin.R
import com.aslanovaslan.newskotlin.adapter.newsrespons.ListSourceAdapter
import com.aslanovaslan.newskotlin.common.Coomon
import com.aslanovaslan.newskotlin.interfaces.NewsService
import com.aslanovaslan.newskotlin.model.website.WebSiteResponse
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var mService: NewsService
    private lateinit var adapter: ListSourceAdapter
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Paper.init(this)

        mService = Coomon.newsService

        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteSource(true)
        }
        recycler_view_source.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source.layoutManager = layoutManager

        dialog=SpotsDialog(this)

        loadWebSiteSource(false)

    }

    private fun loadWebSiteSource(isRefresh: Boolean) {
        if (!isRefresh) {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null") {
                //Read cache
                val wbSiteResponse =
                    Gson().fromJson<WebSiteResponse>(cache, WebSiteResponse::class.java)
                adapter =
                    ListSourceAdapter(
                        baseContext,
                        wbSiteResponse
                    )
                adapter.notifyDataSetChanged()
                recycler_view_source.adapter = adapter
            } else {
                //Load website and rite cache
                 dialog.show()

                //Fetch new data
                mService.getvalue.enqueue(object : retrofit2.Callback<WebSiteResponse> {

                    override fun onFailure(call: Call<WebSiteResponse>, t: Throwable) {
                        Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<WebSiteResponse>,
                        response: Response<WebSiteResponse>
                    ) {
                        adapter =
                            ListSourceAdapter(
                                baseContext,
                                response.body()!!
                            )
                        adapter.notifyDataSetChanged()
                        recycler_view_source.adapter = adapter

                        //Save to cashe
                        Paper.book().write("cache", Gson().toJson(response.body()))
                          dialog.cancel()
                    }

                })
            }
        } else {
            swipe_to_refresh.isRefreshing = true

            //Fetchin data
            mService.getvalue.enqueue(object : retrofit2.Callback<WebSiteResponse> {

                override fun onFailure(call: Call<WebSiteResponse>, t: Throwable) {
                    Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(
                    call: Call<WebSiteResponse>,
                    response: Response<WebSiteResponse>
                ) {
                    adapter =
                        ListSourceAdapter(
                            baseContext,
                            response.body()!!
                        )
                    adapter.notifyDataSetChanged()
                    recycler_view_source.adapter = adapter

                    //Save to cashe
                    Paper.book().write("cache", Gson().toJson(response.body()))
                     dialog.cancel()
                }

            })
            swipe_to_refresh.isRefreshing = false
        }
    }
    //30f4de7ec5a34948b78b62dda745d3c7
}