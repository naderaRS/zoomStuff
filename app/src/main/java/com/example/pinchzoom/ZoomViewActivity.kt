package com.example.pinchzoom

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity


class ZoomViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zoom_view)
        val webView: WebView = findViewById(R.id.image)
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("https://d3gh927sycat3x.cloudfront.net/tenants/4/productimages/P2IN-BKXX-035XX/1611615309_6668_01_1.jpg@Thumbnail")
    }


}