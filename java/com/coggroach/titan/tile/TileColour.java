package com.coggroach.titan.tile;

import android.graphics.Color;

/**
 * Created by TARDIS on 20/11/2014.
 */
public class TileColour
{
    public float R, G, B, A;

    public static TileColour New1 = new TileColour(0.8980392157F, 0.011F, 0.0F, 1.0F);
    public static TileColour New2 = new TileColour(0.8078F, 0.0823F, 0.0745F, 1.0F);
    public static TileColour New3 = new TileColour(0.7176F, 0.1568F, 0.1490F, 1.0F);
    public static TileColour New4 = new TileColour(0.6274F, 0.2274F, 0.1843F, 1.0F);
    public static TileColour New5 = new TileColour(0.4471F, 0.3725F, 0.3725F, 1.0F);
    public static TileColour New6 = new TileColour(0.3568F, 0.4471F, 0.4471F, 1.0F);
    public static TileColour New7 = new TileColour(0.2667F, 0.5176F, 0.5215F, 1.0F);
    public static TileColour New8 = new TileColour(0.1765F, 0.5921F, 0.5971F, 1.0F);
    public static TileColour New9 = new TileColour(0.0862F, 0.6627F, 0.6705F, 1.0F);
    public static TileColour New10 = new TileColour(0.0F, 0.7372F, 0.7500F, 1.0F);

    public static TileColour red = new TileColour(1.0F, 0.0F, 0.0F, 1.0F);
    public static TileColour orange = new TileColour(1.0F, 0.5F, 0.0F, 1.0F);
    public static TileColour yellow = new TileColour(1.0F, 1.0F, 0.0F, 1.0F);
    public static TileColour green = new TileColour(0.5F, 1.0F, 0.0F, 1.0F);
    public static TileColour blue = new TileColour(0.0F, 0.0F, 1.0F, 1.0F);
    public static TileColour purple = new TileColour(0.5F, 0.0F, 1.0F, 1.0F);
    public static TileColour pink = new TileColour(1.0F, 0.0F, 1.0F, 1.0F);

    public static TileColour cyan = new TileColour(0.0F, 1.0F, 1.0F, 1.0F);
    public static TileColour white = new TileColour(253, 246, 220, 255);
    public static TileColour grey = new TileColour(0.45F, 0.45F, 0.45F, 1.0F);
    public static TileColour black = new TileColour(0.1F, 0.1F, 0.1F, 0.5F);

    public static TileColour thermalBomb = new TileColour(1.0F, 1.0F, 0.0F, 0.5F);
    public static TileColour thermalYellow = new TileColour(0.99609375F, 0.8671875F, 0.12890625F, 1.0F);
    public static TileColour thermalGold = new TileColour(0.97265625F, 0.62109375F, 0.0078125F, 1.0F);
    public static TileColour thermalOrange = new TileColour(0.90625F, 0.32421875F, 0.01171875F, 1.0F);
    public static TileColour thermalRed = new TileColour(0.886718F, 0.234375F, 0.1640625F, 1.0F);
    public static TileColour thermalPink = new TileColour(0.75390625F, 0.03125F, 0.53125F, 1.0F);
    public static TileColour thermalPurple = new TileColour(0.3671875F, 0.0F, 0.58203125F, 1.0F);
    public static TileColour thermalBlue = new TileColour(0.1328125F, 0.0F, 0.44921875F, 1.0F);

    public TileColour(int i, int j, int k, int l)
    {
        this((float) i/255, (float) j/255, (float) k/255, (float) l/255);
    }

    public TileColour(int WaveLength)
    {
        float[] colours = Spectrum.WaveLengthToRGB(WaveLength);

        this.R = colours[0];
        this.G = colours[1];
        this.B = colours[2];
        this.A = 1.0F;
    }


    public TileColour(float i, float j, float k, float l)
    {
        this.R = i;
        this.G = j;
        this.B = k;
        this.A = l;
    }

    public boolean isEqual(TileColour c)
    {
        return this.R == c.R && this.G == c.G && this.B == c.B;
    }

    public float[] toFloatArray()
    {
        return new float[] {R, G, B, A};
    }

    public int getIntegerR()
    {
        return (int) (R*255);
    }
    public int getIntegerG()
    {
        return (int) (G*255);
    }
    public int getIntegerB()
    {
        return (int) (B*255);
    }
    public int getIntegerA()
    {
        return (int) (A*255);
    }
    public int getColorValue()
    {
        return Color.argb(this.getIntegerA(), this.getIntegerR(), this.getIntegerG(), this.getIntegerB());
    }

}
