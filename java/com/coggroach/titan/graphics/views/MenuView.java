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
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ggunn on 05/12/14.
 */
public class MenuView extends View implements View.OnTouchListener
{
    private Bitmap background;
    private int width, height;
    private ArrayList<ButtonView> buttons;

    public MenuView(Context context)
    {
        super(context);
        try
        {
            background = BitmapFactory.decodeStream(context.getAssets().open("interface/Background.png"));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        if(background != null)
        {
            buttons = new ArrayList<ButtonView>();

            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            width = metrics.widthPixels;
            height = metrics.heightPixels;

            background = Bitmap.createScaledBitmap(background, width, height, false);
            this.setOnTouchListener(this);
        }
    }

    public int getContextWidth() {
        return width;
    }

    public int getContextHeight() {
        return height;
    }

    public void addButton(Context c, String s, float xF, float yF)
    {
        this.addButton(c, s, (int) (xF * width), (int) (yF * height));
    }

    public void addButton(Context c, String s, int x, int y)
    {
        buttons.add(new ButtonView(c, s, x, y, this));
    }

    public void addButton(ButtonView view)
    {
        buttons.add(view);
    }

    public void onDrawBackground(Canvas canvas)
    {
        canvas.drawBitmap(background, 0, 0, null);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        this.onDrawBackground(canvas);
        Iterator<ButtonView> iterator = buttons.iterator();
        while(iterator.hasNext())
        {
            iterator.next().draw(canvas);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        Iterator<ButtonView> iterator = buttons.iterator();
        while(iterator.hasNext())
        {
            ButtonView temp = iterator.next();
            if (temp.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                temp.onTouch(view, motionEvent);

            }
        }
        return false;
    }

    public void setOnButtonViewListener(int i, OnButtonViewListener listener)
    {
        if(!this.buttons.isEmpty())
            this.buttons.get(i).setOnButtonViewListener(listener);
    }
}