package com.example.pinchzoom

import android.graphics.RectF
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity


class Test2 : ComponentActivity() {

    var myImage: ImageView? = null
    private var scaleGestureDetector: ScaleGestureDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myImage = findViewById<View>(R.id.myimage) as ImageView
        scaleGestureDetector = ScaleGestureDetector(
            this, MySimpleOnScaleGestureListener(myImage)
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector!!.onTouchEvent(event)
        return true
        //return super.onTouchEvent(event);
    }

    private inner class MySimpleOnScaleGestureListener(

        var viewMyImage: ImageView?
    ) :
        SimpleOnScaleGestureListener() {
        var factor = 0f
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            factor = 1.0f
            return true
            //return super.onScaleBegin(detector);
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor - 1
            factor += scaleFactor


            viewMyImage!!.scaleX = factor
            viewMyImage!!.scaleY = factor
            return true
            //return super.onScale(detector);
        }
    }
}