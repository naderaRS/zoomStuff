package com.example.pinchzoom

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide
import java.net.URL

class Test1 : ComponentActivity() {
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var imageView: ImageView? = null
    private var scale = 1.0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var isDragging = false
    private var isZoomed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag)
        imageView = findViewById(R.id.imageView)
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
       // val url =
            "https://d3gh927sycat3x.cloudfront.net/tenants/4/productimages/P2IN-BKXX-035XX/1611615309_6668_01_1.jpg@Thumbnail"
      //  Glide.with(this).load(url).into(imageView!!)

        val url = URL("https://en.wikipedia.org/wiki/Fred_Johnson_%28offensive_lineman%29#/media/File:Fred_Johnson_(offensive_lineman).jpg")
        val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        // val url = "https://d3gh927sycat3x.cloudfront.net/tenants/4/productimages/P2IN-BKXX-035XX/1611615309_6668_01_1.jpg@Thumbnail"

        imageView!!.setImageBitmap(bmp);



    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector!!.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
                isDragging = false
            }

            MotionEvent.ACTION_MOVE -> {
                if (!isZoomed) {
                    val deltaX = event.x - lastTouchX
                    val deltaY = event.y - lastTouchY
                    imageView!!.translationX = imageView!!.translationX + deltaX
                    imageView!!.translationY = imageView!!.translationY + deltaY
                    isDragging = true
                }
                lastTouchX = event.x
                lastTouchY = event.y
            }

            MotionEvent.ACTION_UP -> if (!isDragging) {
                // Handle tap or click event here
            }
        }
        return true
    }

    private inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            Log.d("ScaleListener", "onScaleBegin")
            isZoomed = true
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
            Log.d("ScaleListener", "onScaleEnd")
            isZoomed = false
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            Log.d("ScaleListener", "onScale")
            scale *= detector.scaleFactor
            scale = Math.max(0.1f, Math.min(scale, 5.0f))
            imageView!!.scaleX = scale
            imageView!!.scaleY = scale

            // Check if the scale is exactly 1.0 to enable dragging
            if (Math.abs(scale - 1.0f) < 0.01f) {
                isZoomed = false
                Log.d("ScaleListener", "isZoomed = false")
            } else {
                isZoomed = true
                Log.d("ScaleListener", "isZoomed = true")
            }
            return true
        }
    }
}