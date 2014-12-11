package com.coggroach.titan.graphics.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by TARDIS on 08/12/2014.
 */
public class UIView extends View implements View.OnTouchListener
{
    private int width, height;
    private ArrayList<View> UIElements;
    private boolean isVisible;

    public UIView(Context context)
    {
        super(context);
        UIElements = new ArrayList<View>();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        this.isVisible = true;
        //this.setOnTouchListener(this);
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    public void setVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
        this.invalidate();
    }

    public int getContextWidth() {
        return width;
    }

    public int getContextHeight() {
        return height;
    }

    public View getUIElementAt(int i)
    {
        return UIElements.get(i);
    }

    public View getViewWithContains(int x, int y)
    {
        return UIElements.get(getIndexOfViewWithContains(x, y));
    }

    public int getIndexOfViewWithContains(int x, int y)
    {
        int i = 0;
        Iterator<View> iterator = UIElements.iterator();
        while (iterator.hasNext())
        {
            View temp = iterator.next();
            if(temp instanceof IContainable)
            {
                if(((IContainable) temp).contains(x, y))
                    return i;
                i++;
            }
        }
        return Integer.MIN_VALUE;
    }

    public void addView(View view)
    {
        if(view instanceof IContainable && UIElements != null)
        {
            UIElements.add(view);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(isVisible)
        {
            Iterator<View> iterator = UIElements.iterator();
            while (iterator.hasNext()) {
                iterator.next().draw(canvas);
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        Iterator<View> iterator = UIElements.iterator();
        while(iterator.hasNext())
        {
            View temp = iterator.next();
            if(temp instanceof IContainable)
                if (((IContainable) temp).contains((int) motionEvent.getX(), (int) motionEvent.getY()))
                {
                    ((IContainable) temp).onTouch(view, motionEvent);
                }
        }
        return false;
    }
}
