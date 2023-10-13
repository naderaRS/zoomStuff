package com.example.pinchzoom;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.ComponentActivity;

public class Test1 extends ComponentActivity {

    TextView textMsg;
    ImageView myImage;

    private ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textMsg = (TextView)findViewById(R.id.msg);
        myImage = (ImageView)findViewById(R.id.myimage);

        scaleGestureDetector = new ScaleGestureDetector(
                this, new MySimpleOnScaleGestureListener(textMsg, myImage));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
        //return super.onTouchEvent(event);
    }

    private class MySimpleOnScaleGestureListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener{

        TextView viewMessage;
        ImageView viewMyImage;

        float factor;

        public MySimpleOnScaleGestureListener(TextView v, ImageView iv) {
            super();
            viewMessage = v;
            viewMyImage = iv;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            factor = 1.0f;
            return true;
            //return super.onScaleBegin(detector);
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float scaleFactor = detector.getScaleFactor() - 1;
            factor += scaleFactor;
            viewMessage.setText(String.valueOf(scaleFactor)
                    + "\n" + String.valueOf(factor));
            viewMyImage.setScaleX(factor);
            viewMyImage.setScaleY(factor);
            return true;
            //return super.onScale(detector);
        }
    }
}