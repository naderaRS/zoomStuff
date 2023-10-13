package com.example.pinchzoom

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity


class Test2 : ComponentActivity() {
    var textMsg: TextView? = null
    var myImage: ImageView? = null
    private var scaleGestureDetector: ScaleGestureDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textMsg = findViewById<View>(R.id.msg) as TextView
        myImage = findViewById<View>(R.id.myimage) as ImageView
        scaleGestureDetector = ScaleGestureDetector(
            this, MySimpleOnScaleGestureListener(textMsg, myImage)
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector!!.onTouchEvent(event)
        return true
        //return super.onTouchEvent(event);
    }

    private inner class MySimpleOnScaleGestureListener(
        var viewMessage: TextView?,
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
            viewMessage!!.text = """
                $scaleFactor
                $factor
                """.trimIndent()
            viewMyImage!!.scaleX = factor
            viewMyImage!!.scaleY = factor
            return true
            //return super.onScale(detector);
        }
    }
}