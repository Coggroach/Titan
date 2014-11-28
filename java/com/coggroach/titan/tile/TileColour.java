package com.coggroach.titan.tile;

/**
 * Created by TARDIS on 20/11/2014.
 */
public class TileColour
{
    public float R, G, B, A;

    public static TileColour red = new TileColour(1.0F, 0.0F, 0.0F, 1.0F);
    public static TileColour orange = new TileColour(1.0F, 0.5F, 0.0F, 1.0F);
    public static TileColour yellow = new TileColour(1.0F, 1.0F, 0.0F, 1.0F);
    public static TileColour green = new TileColour(0.5F, 1.0F, 0.0F, 1.0F);
    public static TileColour blue = new TileColour(0.0F, 0.0F, 1.0F, 1.0F);
    public static TileColour purple = new TileColour(0.5F, 0.0F, 1.0F, 1.0F);
    public static TileColour pink = new TileColour(1.0F, 0.0F, 1.0F, 1.0F);

    public static TileColour cyan = new TileColour(0.0F, 1.0F, 1.0F, 1.0F);
    public static TileColour white = new TileColour(1.0F, 1.0F, 1.0F, 1.0F);
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
        int min = WaveLengthMin;
        boolean slope = false;
        for(int i = 0; i < 6; i++)
        {
            if(withinRange(WaveLength, min, WaveLengthRanges[i]))
            {
                B = (BlueRanges[i] == 2) ? getColourFromWaveLength(WaveLength, slope, min, WaveLengthRanges[i]) : BlueRanges[i];
                G = (GreenRanges[i] == 2) ? getColourFromWaveLength(WaveLength, slope, min, WaveLengthRanges[i]) : GreenRanges[i];
                R = (RedRanges[i] == 2) ? getColourFromWaveLength(WaveLength, slope, min, WaveLengthRanges[i]) : RedRanges[i];
            }
            min += WaveLengthRanges[i];
            slope = !slope;
        }
    }

    //NanoMeters
    private int[] WaveLengthRanges = {60, 50, 20, 70, 65, 35};
    private int[] BlueRanges = {1, 1, 2, 0, 0, 0};
    private int[] GreenRanges = {0, 2, 1, 1, 2, 0};
    private int[] RedRanges = {2, 0, 0, 2, 1, 1};
    private int WaveLengthMin = 380;

    private boolean withinRange(int i, int min, int range)
    {
        return (i >= min && i < min + range);
    }

    private float getColourFromWaveLength(int WaveLength, boolean slope, int min, int range)
    {
        int m = slope ? 1 : -1;
        min = slope ? min : min + range;

        return (float) (m * (WaveLength - min)/range);
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

}
