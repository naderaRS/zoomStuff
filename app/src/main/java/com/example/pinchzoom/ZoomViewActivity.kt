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
        webView.loadUrl("lineItem.product?.image?.originalImage?.url")


    }


}