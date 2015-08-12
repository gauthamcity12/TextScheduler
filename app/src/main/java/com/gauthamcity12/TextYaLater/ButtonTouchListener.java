package com.gauthamcity12.TextYaLater;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gauthamcity12 on 7/13/15.
 */
public class ButtonTouchListener implements View.OnTouchListener {
    public boolean onTouch(View v, MotionEvent e){
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            v.setBackgroundColor(Color.parseColor("#64B5F6")); // increases the transparency of the color when touched
        }
        else if(e.getAction() == MotionEvent.ACTION_UP){
            v.setBackgroundColor(Color.parseColor("#2196F3")); // returns button to original color
        }
        return false;
    }

}
