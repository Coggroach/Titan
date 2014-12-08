package com.coggroach.titan.graphics.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;

/**
 * Created by ggunn on 05/12/14.
 */
public class ButtonView extends View
{
    private Bitmap button;
    private int width, height;
    private int x, y;
    private OnButtonViewListener listener;

    public ButtonView(Context context, String s, float xF, float yF)
    {
        super(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        this.width = metrics.widthPixels;
        this.height = metrics.heightPixels;
        init(context, s, (int) (xF * width), (int) (yF * height), width, height);
    }

    public ButtonView(Context context, String s, int x, int y, MenuView parent)
    {
        super(context);
        init(context, s, x, y, parent.getContextWidth(), parent.getContextHeight());
    }

    public ButtonView(Context context, String s, int x, int y, int width, int height)
    {
        super(context);
        init(context, s, x, y, width, height);
    }

    public void init(Context context, String s, int x, int y, int width, int height)
    {
        try
        {
            this.button = BitmapFactory.decodeStream(context.getAssets().open(s));
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        this.width = metrics.widthPixels;
        this.height = metrics.heightPixels;
        this.x = x;
        this.y = y;

        if(this.button != null)
        {
            int bwidth = (int) ((double) button.getWidth() * this.width) / width;
            int bheight = (int) ((double) button.getHeight() * this.height) / height;

            this.button = Bitmap.createScaledBitmap(button, bwidth, bheight, false);
        }
    }

    public void onDrawButton(Canvas canvas){

        canvas.drawBitmap(button, x, y, null);
    }

    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        this.onDrawButton(canvas);
    }

    public boolean contains(int x, int y)
    {
        return (x > this.x && x <= this.x + this.button.getWidth() && y >  this.y &&  y <= this.y + this.button.getHeight());
    }

    public void onTouch(View v, MotionEvent motionEvent)
    {
        if(listener != null)
            listener.onTouch(v, motionEvent);
    }

    public void setOnButtonViewListener(OnButtonViewListener listener)
    {
        this.listener = listener;
    }
}
