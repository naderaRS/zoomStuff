package com.example.pinchzoom

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.activity.ComponentActivity


class Test3 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web)
        val myWebView: WebView = findViewById(R.id.webView1)
        myWebView.getSettings().setBuiltInZoomControls(true);

        myWebView.loadUrl("https://d3gh927sycat3x.cloudfront.net/tenants/4/productimages/P2IN-BKXX-035XX/1611615309_6668_01_1.jpg@Thumbnail")


    }


}