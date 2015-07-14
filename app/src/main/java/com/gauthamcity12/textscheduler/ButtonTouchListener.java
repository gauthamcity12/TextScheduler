package com.gauthamcity12.textscheduler;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gauthamcity12 on 7/13/15.
 */
public class ButtonTouchListener implements View.OnTouchListener {
    public boolean onTouch(View v, MotionEvent e){
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            v.setBackgroundColor(Color.parseColor("#4DB6AC")); // increases the transparency of the color when touched
        }
        else if(e.getAction() == MotionEvent.ACTION_UP){
            v.setBackgroundColor(Color.parseColor("#009688")); // returns button to original color
        }
        return false;
    }

}
