package com.coggroach.titan.game;

import com.coggroach.titan.tile.Palette;
import com.coggroach.titan.tile.Spectrum;
import com.coggroach.titan.tile.Tile;
import com.coggroach.titan.tile.TileColour;

import java.util.ArrayList;

/**
 * Created by TARDIS on 27/11/2014.
 */
public class GameHelper
{
    public static int getTileIndexFromWorld(Game game, float xWorld, float yWorld)
    {
        if(Math.abs(yWorld) > 1.0F)
            return Integer.MIN_VALUE;

        float tileWidth = 2.0F/game.getWidth();
        float tileHeight = 2.0F/game.getHeight();

        int index = 0;
        for(float j = 1.0F; j >= -1.0F; j = j - tileHeight)
            for(float i = 0.999999F ; i >= -1.0F; i = i - tileWidth)
            {
                boolean lessThanI = xWorld < i;
                boolean lessThanJ = yWorld < j;
                boolean greaterThanIMinus = xWorld > i - tileWidth;
                boolean greaterThanJMinus = yWorld > j - tileHeight;

                if(lessThanI && greaterThanIMinus && lessThanJ && greaterThanJMinus)
                {
                    return index;
                }
                index++;
            }

        return index;
    }

    public static Tile getTileFromWorld(Game game, float xWorld, float yWorld)
    {
        return game.getTile(getTileIndexFromWorld(game, xWorld, yWorld));
    }

    public static void generateGrid(Game game, int x, int y, int r)
    {
        for(int i = 0; i <= r; i++)//height
        {
            drawLine(game,  1, -1, x, y + i, i, i);
            drawLine(game, -1, -1, x, y + i, i, i);
            drawLine(game, -1, 1, x, y - i, i, i);
            drawLine(game, 1, 1, x, y - i, i, i);
        }
    }

    protected static void drawLine(Game game, int m, int n, int x, int y, int r, int c)
    {
        for(int i = 0; i <= r; i++)
        {
            setColourWithinBounds(game, x + i * m, y + i * n, c);
        }
    }

    private static ArrayList<TileColour> palette = Palette.main(TileColour.cyan, TileColour.grey, TileColour.red, 10);

    protected static boolean setColourWithinBounds(Game game, int x, int y, int i)
    {
        if(i >= palette.size())
           return setColourWithinBounds(game, x, y, TileColour.black);

        return setColourWithinBounds(game, x, y, //new TileColour(700 - i * 50));
                palette.get(i));
    }

    protected static boolean setColourWithinBounds(Game game, int x, int y, TileColour c)
    {
        if(x >= 0 && x < game.getWidth() && y >= 0 && y < game.getHeight())
        {
            game.getTile(x, y).setColour(c, 3);

            return true;
        }
        return false;
    }

    public static TileColour getIndexedTileColour(int i)
    {
        if(i > 8)
        {
            i = 8;
        }
        //i += (Options.PALETTE*9);

        switch (i)
        {
            case 0:
                return TileColour.grey;
            case 1:
                return TileColour.red;
            case 2:
                return TileColour.orange;
            case 3:
                return TileColour.yellow;
            case 4:
                return TileColour.green;
            case 5:
                return TileColour.blue;
            case 6:
                return TileColour.purple;
            case 7:
                return TileColour.pink;
            case 8:
                return TileColour.black;
            case 9:
                return TileColour.thermalBomb;
            case 10:
                return TileColour.thermalYellow;
            case 11:
                return TileColour.thermalGold;
            case 12:
                return TileColour.thermalOrange;
            case 13:
                return TileColour.thermalRed;
            case 14:
                return TileColour.thermalPink;
            case 15:
                return TileColour.thermalPurple;
            case 16:
                return TileColour.thermalBlue;
            case 17:
                return TileColour.black;
            default:
                return TileColour.white;
        }
    }
}
