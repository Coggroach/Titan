package com.coggroach.titan.graphics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.coggroach.titan.activities.GameActivity;
import com.coggroach.titan.common.AssetReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ggunn on 05/12/14.
 */
public class MenuView extends View implements View.OnTouchListener {

    Bitmap background;
    int width, height;

    ArrayList<ButtonView> buttons;

    public MenuView(Context context) {
        super(context);
        try {
            background = BitmapFactory.decodeStream(context.getAssets().open("Background.png"));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        buttons = new ArrayList<ButtonView>();

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        buttons.add(new ButtonView(context, "buttoncyan.png", (int) (0.1F*width), (int) (0.35F*height), background.getWidth(), background.getHeight()));
        buttons.add(new ButtonView(context, "buttonred.png", (int) (0.1F*width), (int) (0.65F*height), background.getWidth(), background.getHeight()));
        background = Bitmap.createScaledBitmap(background, width, height, false);
        this.setOnTouchListener(this);
    }

    public void onDrawBackground(Canvas canvas){

        canvas.drawBitmap(background, 0, 0, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
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
        Log.i("OnTouchMenuView", "DONT TOUCH ME!");
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