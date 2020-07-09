package com.aslanovaslan.newskotlin.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.aslanovaslan.newskotlin.R
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetail : AppCompatActivity() {
    lateinit var dialog: AlertDialog

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        dialog = SpotsDialog(this)
        dialog.show()

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                dialog.cancel()
            }
        }
        if (intent != null) {
            @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            if (intent.getStringExtra("webUrl").isNotEmpty()) {
                webView.loadUrl(intent.getStringExtra("webUrl"))
            }
        }

    }
}