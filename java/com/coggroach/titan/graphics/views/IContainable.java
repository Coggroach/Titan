package com.coggroach.titan.graphics.views;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TARDIS on 08/12/2014.
 */
public interface IContainable
{
    public boolean contains(int x, int y);
    public void onTouch(View v, MotionEvent event);
}
