package com.coggroach.titan.tile;

/**
 * Created by TARDIS on 06/12/2014.
 */
public enum TileDirection
{
    FRONT(0),
    RIGHT(1),
    BACK(2),
    LEFT(3),
    TOP(4),
    BOTTOM(5);

    private int id;

    TileDirection(int i)
    {
        this.id = i;
    }

    public int getId()
    {
        return this.id;
    }
}
