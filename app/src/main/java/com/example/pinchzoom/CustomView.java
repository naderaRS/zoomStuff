package com.example.pinchzoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.NonNull;

public class CustomView extends View {
    Bitmap image;
    int screenHeight;
    int screenWidth;
    Paint paint;
    GestureDetector gestures;
    ScaleGestureDetector scaleGesture;
    float scale = 1.0f;
    float horizontalOffset, verticalOffset;

    int NORMAL = 0;
    int ZOOM = 1;
    int DRAG = 2;
    boolean isScaling = false;
    float touchX, touchY;
    int mode = NORMAL;

    public CustomView(Context context) {
        super(context);
        //initializing variables
        image = BitmapFactory.decodeResource(getResources(),
                R.drawable.cancel);
        //This is a full screen view
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(Color.WHITE);

        scaleGesture = new ScaleGestureDetector(getContext(),
                new ScaleListener());
        gestures = new GestureDetector(getContext(), new GestureListener());
        mode = NORMAL;
        initialize();
    }

    //Best fit image display on canvas
    private void initialize() {
        float imgPartRatio = image.getWidth() / (float) image.getHeight();
        float screenRatio = (float) screenWidth / (float) screenHeight;

        if (screenRatio > imgPartRatio) {
            scale = ((float) screenHeight) / (float) (image.getHeight()); // fit height
            horizontalOffset = ((float) screenWidth - scale
                    * (float) (image.getWidth())) / 2.0f;
            verticalOffset = 0;
        } else {
            scale = ((float) screenWidth) / (float) (image.getWidth()); // fit width
            horizontalOffset = 0;
            verticalOffset = ((float) screenHeight - scale
                    * (float) (image.getHeight())) / 2.0f;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawColor(Color.WHITE);
        if(mode == DRAG || mode == NORMAL) {
            //This works perfectly as expected
            canvas.translate(horizontalOffset, verticalOffset);
            canvas.scale(scale, scale);
            canvas.drawBitmap(image, getMatrix(), paint);
        }
        else if (mode == ZOOM) {
            //PROBLEM AREA - when applying pinch zoom,
            //the image jumps to a position abruptly
            canvas.scale(scale, scale, touchX, touchY);
            canvas.drawBitmap(image, getMatrix(), paint);
        }
        canvas.restore();
        canvas.drawBitmap(image, drawMatrix, paint);
    }
    Matrix drawMatrix;
    float lastFocusX;
    float lastFocusY;
    public class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Matrix transformationMatrix = new Matrix();
            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();

            //Zoom focus is where the fingers are centered,
            transformationMatrix.postTranslate(-focusX, -focusY);

            transformationMatrix.postScale(detector.getScaleFactor(), detector.getScaleFactor());

            /* Adding focus shift to allow for scrolling with two pointers down. Remove it to skip this functionality. This could be done in fewer lines, but for clarity I do it this way here */
            //Edited after comment by chochim
            float focusShiftX = focusX - lastFocusX;
            float focusShiftY = focusY - lastFocusY;
            transformationMatrix.postTranslate(focusX + focusShiftX, focusY + focusShiftY);
            drawMatrix.postConcat(transformationMatrix);
            lastFocusX = focusX;
            lastFocusY = focusY;
            invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            lastFocusX = detector.getFocusX();
            lastFocusY = detector.getFocusY();
            return true;
        }


        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mode = NORMAL;
            isScaling = false;
        }

    }

    public class GestureListener implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent e) {
            isScaling = false;
            return true;
        }

        @Override
        public void onShowPress(@NonNull MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent downEvent, MotionEvent currentEvent,
                                float distanceX, float distanceY) {
            drawMatrix.postTranslate(-distanceX, -distanceY);
            invalidate();
            return true;
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {

        }

        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(@NonNull MotionEvent e) {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGesture.onTouchEvent(event);
        gestures.onTouchEvent(event);
        return true;
    }
}