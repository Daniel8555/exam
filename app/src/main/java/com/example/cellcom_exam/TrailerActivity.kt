package com.example.cellcom_exam

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView


class TrailerActivity : Activity() {
    private lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trailer)

        webView = findViewById(R.id.webView)

        val trailerUrl = intent.getStringExtra("TRAILER_URL") ?: ""

        if (trailerUrl.isNotEmpty()) {
            playTrailer(trailerUrl)
        }
    }

    private fun playTrailer(url: String) {



        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true


        val videoUrl = url
        webView.loadUrl(videoUrl)


    }
}
