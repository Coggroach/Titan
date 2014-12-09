package com.coggroach.titan.graphics.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.coggroach.titan.activities.GameActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by TARDIS on 30/11/2014.
 */
public class OptionsView extends View implements View.OnTouchListener
{
    private Context context;
    private Bitmap icon;
    private Bitmap options;
    private Bitmap palette;

    private ArrayList<OptionButton> optionsList;
    private int width, height;
    private boolean isVisible;

    public boolean isVisible()
    {
        return isVisible;
    }

    public void setVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
        this.invalidate();
    }

    private class OptionButton
    {
        Bitmap icon;
        String text;
        Point point;
        Paint paint;
        int scale;

        OptionButton(Bitmap icon, String text, Point p, int scale)
        {
            this.icon = Bitmap.createScaledBitmap(icon, scale, scale, false);
            this.text = text;
            this.point = p;
            this.scale = scale;
            paint = new Paint();
            paint.setTextSize(scale);
            paint.setARGB(255, 255, 255, 255);
        }

        public boolean contains(Point p)
        {
            return (p.x > point.x && p.x <= point.x + icon.getWidth() && p.y > point.y && p.y <= point.y + icon.getHeight());
        }

        public void draw(Canvas c)
        {
            c.drawBitmap(icon, point.x, point.y, paint);
            c.drawText(text, point.x + icon.getWidth(), point.y + icon.getHeight(), paint);
        }

        public void setPaint(Paint p)
        {
            this.paint = p;
        }

        public Bitmap getIcon() {
            return icon;
        }

        public String getText() {
            return text;
        }

        public Point getPoint() {
            return point;
        }
    }

    public OptionsView(Context context, String[] list)
    {
        super(context);
        this.context = context;
        optionsList = new ArrayList<OptionButton>();
        isVisible = false;

        try
        {
            icon = BitmapFactory.decodeStream(context.getResources().getAssets().open("interface/ButtonGamemodes.png"));
            options = BitmapFactory.decodeStream(context.getResources().getAssets().open("interface/ButtonGameOptions.png"));
            palette = BitmapFactory.decodeStream(context.getResources().getAssets().open("interface/Palette.png"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        if(icon != null && palette != null)
        {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            width = metrics.widthPixels;
            height = metrics.heightPixels;

            this.generateOptions(list, (int) (0.1F * width), (int) (0.2F * height), (int) (0.1F * width), (int) (0.05F * height));
            this.icon = Bitmap.createScaledBitmap(this.icon, (int) (width * 0.1F), (int) (width * 0.1F), false);
            this.options = Bitmap.createScaledBitmap(this.options, (int) (width * 0.4F), (int) (width * 0.15F), false);
            this.palette = Bitmap.createScaledBitmap(this.palette, (int) (1F*width), (int) (width * 0.3F), false);
        }
    }

    public boolean contains(Point p)
    {
        Iterator<OptionButton> iterator = optionsList.iterator();
        while(iterator.hasNext())
        {
            if(iterator.next().contains(p))
                return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if(v instanceof OptionsView)
        {
            int i = getIndexOfOptionsButton((int) event.getX(), (int) event.getY());
            if(i != Integer.MIN_VALUE)
            {
                if(i == 0)
                    ((GameActivity) context).onRestart();
                else
                {
                    ((GameActivity) context).getOptions().GAMEMODE = (i - 1);
                    ((GameActivity) context).onRestart();
                }
            }

            invalidate();
        }
        return true;
    }

    public int getIndexOfOptionsButton(int x, int y)
    {
        for(int i = 0; i < optionsList.size(); i++)
        {
             if(optionsList.get(i).contains(new Point(x, y)))
                return i;
        }
        return Integer.MIN_VALUE;
    }

    public void setGlobalPaint(Paint p)
    {
        Iterator<OptionButton> iterator = optionsList.iterator();
        while(iterator.hasNext())
        {
            iterator.next().setPaint(p);
        }
    }

    public void generateOptions(String[] list, int x, int y, int scale, int offset)
    {
        int currX = x;
        int currY = y;
        for(int i = 0; i < list.length; i++)
        {
            optionsList.add(new OptionButton(icon, list[i], new Point(currX, currY), scale));
            currY += icon.getHeight() + offset;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(isVisible)
        {
            Iterator<OptionButton> iterator = optionsList.iterator();
            while (iterator.hasNext()) {
                OptionButton t = iterator.next();
                t.draw(canvas);
            }
        }
        //canvas.drawBitmap(icon, (int)(width*0.9F), 0, null);
        canvas.drawBitmap(options, (int)(width*0.6F),(int) (height*0.0125), null);
        canvas.drawBitmap(palette, (int) (width*0F), (int) (height*0.775), null);
    }
}
