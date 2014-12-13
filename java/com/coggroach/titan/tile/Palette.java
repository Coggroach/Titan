package com.coggroach.titan.tile;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by TARDIS on 29/11/2014.
 */
public class Palette
{
    private static ArrayList<TileColour> palette = new ArrayList<TileColour>();

    public TileColour getTileColour(int palette, int id)
    {
        return null;
    }

    public static ArrayList<TileColour> main (TileColour RGBinit, TileColour RGBmid, TileColour RGBfinal, int steps)
    {
        int i = 0;
        ArrayList<TileColour> list = new ArrayList<TileColour>();
        int Rinc = (int) (255*(RGBinit.R - RGBmid.R))/(steps/2);
        int Binc = (int) (255*(RGBinit.B - RGBmid.B))/(steps/2);
        int Ginc = (int) (255*(RGBinit.G - RGBmid.G))/(steps/2);
        while(i < (steps/2)){
            TileColour colour = new TileColour(RGBinit.getIntegerR() + (Rinc*i), RGBinit.getIntegerB() + (Binc*i), RGBinit.getIntegerG() + (Ginc*i), 255);
            list.add(colour);
            i++;
        }
        i = 0;
        Rinc = (int) (255*(RGBmid.R - RGBfinal.R))/(steps/2);
        Binc = (int) (255*(RGBmid.B - RGBfinal.B))/(steps/2);
        Ginc = (int) (255*(RGBmid.G - RGBfinal.G))/(steps/2);
        while(i < (steps/2)){
            TileColour colour = new TileColour(RGBmid.getIntegerR() + (Rinc*i), RGBmid.getIntegerB() + (Binc*i), RGBmid.getIntegerG() + (Ginc*i), 255);
            list.add(colour);
            i++;
        }
        return list;
    }

    public static ArrayList<TileColour> getGradientPalette(TileColour c1, TileColour c2, int steps, int codeKillColour, TileColour extrema)
    {
        ArrayList<TileColour> palette = new ArrayList<TileColour>();

        palette.addAll(getGradientPalette(c1, extrema, steps, codeKillColour));

        ArrayList<TileColour> temp = getGradientPalette(extrema, c2, steps, codeKillColour);
        temp.remove(0);

        palette.addAll(temp);
        return palette;
    }

    public static ArrayList<TileColour> getGradientPalette(TileColour c1, TileColour c2, int steps, int codeKillColour)
    {
        ArrayList<TileColour> palette = new ArrayList<TileColour>();
        float RStep = (codeKillColour == 0) ? 0 : (c1.R - c2.R)/steps;
        float GStep = (codeKillColour == 1) ? 0 : (c1.G - c2.G)/steps;
        float BStep = (codeKillColour == 2) ? 0 : (c1.B - c2.B)/steps;

        float RTotal = RStep;
        float GTotal = GStep;
        float BTotal = BStep;


        palette.add(c1);
        for(int i = 1; i < steps - 1; i++)
        {
            palette.add(new TileColour(c1.R + RTotal, c1.G + GTotal, c1.B + BTotal, c1.A));
            RTotal += RStep;
            GTotal += GStep;
            BTotal += BStep;
        }
        palette.add(c2);

        return palette;
    }
}
