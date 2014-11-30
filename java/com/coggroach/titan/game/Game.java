package com.coggroach.titan.game;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.coggroach.titan.tile.Tile;

import java.util.ArrayList;

/**
 * Created by TARDIS on 20/11/2014.
 */
public abstract class Game
{
    protected Tile[] tiles;
    protected int width, height;
    protected ArrayList<View> UIElements;
    protected ViewGroup UILayout;
    protected ArrayList<String> TextureList;
    protected boolean updateView;

    public abstract boolean isRendering();
    public abstract void initUIElements(Context c);
    public abstract void start(int i, int j);
    public abstract void restart();
    public abstract void generate();
    public abstract boolean isGameOn();
    public abstract void setGameOn(boolean b);
    public abstract void onTouch(View v, MotionEvent event);
    public abstract void updateTextureBindings(boolean b);
    public abstract boolean getUpdateTextureBindings();
    public abstract void initTextureList();

    public Tile getTile(int x, int y)
    {
        return (x + y*height < tiles.length) ? tiles[x + y*height] : null;
    }

    public boolean isInBound(int x, int y)
    {
        return (float) width > (float) (x/height + y); //Divide Both sides by Height; Less Calculations;
    }

    public Tile[] getTiles()
    {
        return tiles;
    }

    public Tile getTile(int i)
    {
        return (tiles.length > i) ? tiles[i] : null;
    }

    public void setTile(Tile t, int i)
    {
        this.tiles[i] = t;
    }

    public int getTilesLength()
    {
        return tiles.length;
    }

    public void setTiles(Tile[] tiles)
    {
        this.tiles = tiles;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<View> getUIElements()
    {
        return UIElements;
    }

    public ViewGroup getUILayout()
    {
        return UILayout;
    }

    public void addUIElement(View v)
    {
        this.UIElements.add(v);
    }

    public ArrayList<String> getTextureList()
    {
        return TextureList;
    }

    public void updateView(boolean b)
    {
        this.updateView = b;
    }

    public boolean getUpdateView()
    {
        return this.updateView;
    }

}
