package com.example.pinchzoom
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide

class Test2 : ComponentActivity() {
    private var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag)
        imageView = findViewById(R.id.imageView)

        val url =
            "https://www.thevillagetrip.com/festival/wp-content/uploads/2023/08/Fred-Johnson.jpg"
        Glide.with(this).load(url).into(imageView!!)
    }
}