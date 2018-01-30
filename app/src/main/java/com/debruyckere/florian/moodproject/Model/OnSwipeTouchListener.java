package com.debruyckere.florian.moodproject.Model;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Debruyckère Florian on 16/01/2018.
 */

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context pContext) {
        gestureDetector = new GestureDetector(pContext, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
    public final class GestureListener extends GestureDetector.SimpleOnGestureListener{

        public static final int SWIPE_THRESHOLD = 100;
        public static final int SWIPE_VELOCITY_THRESHOLD = 100;

        /**
         * Detect the slide and called the property method
         * @param e1
         * @param e2
         * @param velocityX
         * @param velocityY
         * @return
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try{
                float diffY = e2.getY() - e1.getY();

                if( Math.abs(diffY)> SWIPE_THRESHOLD && Math.abs(velocityY)>SWIPE_VELOCITY_THRESHOLD){
                    Log.i("LISTENER","Y Swipe");
                    if(diffY>0){
                        Log.i("LISTENER","Go Bottom");
                        onSwipeBottom();
                    } else {
                        Log.i("LISTENER","Go top");
                        onSwipeTop();
                    }
                    result = true;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    /**
     * Slide to bottom
     */
    public void onSwipeBottom(){}

    /**
     * Slide to top
     */
    public void onSwipeTop(){}
}
