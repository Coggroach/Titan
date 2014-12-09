package com.coggroach.titan.graphics.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TARDIS on 09/12/2014.
 */
public class StringView extends View implements IContainable
{
    private String text;
    private int pointX, pointY;
    private Paint paint;
    private int scale;
    private int width, height;
    private OnButtonViewListener listener;

    public StringView(Context context, String s, float x, float y, int scale)
    {
        super(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        this.width = metrics.widthPixels;
        this.height = metrics.heightPixels;
        this.text = s;
        this.pointX = (int) (this.width * x);
        this.pointY = (int) (this.height *y);
        this.scale = scale;
        this.paint = new Paint();
        this.paint.setTextSize(scale);
        this.paint.setARGB(255, 255, 255, 255);
    }

    private int getTextWidth()
    {
        return (text.length() + 1) * scale;
    }

    private int getTextHeight()
    {
        return (scale);
    }

    @Override
    public boolean contains(int x, int y)
    {
        return (x > pointX && x <= pointX + getTextWidth() && y > pointY && y <= pointY + getTextHeight());
    }

    @Override
    public void onTouch(View v, MotionEvent event) {
        if(listener != null)
            listener.onTouch(v, event);
    }

    public void setPaint(int a, int r, int g, int b) {
        this.paint.setARGB(a, r, g, b);
    }

    public void setText(String text) {
        this.text = text;
        this.invalidate();

    }

    public void setOnButtonViewListener(OnButtonViewListener listener)
    {
        this.listener = listener;
    }

    private void onDrawText(Canvas canvas){
        canvas.drawText(text, pointX, pointY, paint);
    }

    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        this.onDrawText(canvas);
    }
}
