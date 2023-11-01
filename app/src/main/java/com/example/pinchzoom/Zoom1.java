package com.example.pinchzoom;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import androidx.activity.ComponentActivity;

import com.bumptech.glide.Glide;

public class Zoom1 extends ComponentActivity {

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 2.0f;
    private ImageView mImageView;


    String url  = "https://d3gh927sycat3x.cloudfront.net/tenants/4/productimages/P2IN-BKXX-035XX/1611615309_6668_01_1.jpg@Thumbnail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the view and the gesture detector
        mImageView = findViewById(R.id.imageView);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());



        mImageView = (ImageView)findViewById(R.id.imageView);
        Glide.with(this).load(url).into(mImageView);

    }

    // this redirects all touch events in the activity to the gesture detector
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        // when a scale gesture is detected, use it to resize the image
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mImageView.setScaleX(mScaleFactor);
            mImageView.setScaleY(mScaleFactor);
            return true;
        }
    }
}