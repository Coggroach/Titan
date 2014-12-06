package com.coggroach.titan.tile;

/**
 * Created by TARDIS on 29/11/2014.
 */
public class Spectrum
{
    static private double Gamma = 0.80;
    static private double IntensityMax = 1.0D;

    public static float[] WaveLengthToRGB(float Wavelength)
    {
        float factor;
        float Red,Green,Blue;

        if((Wavelength >= 380) && (Wavelength < 440))
        {
            Red = -(Wavelength - 440) / (440 - 380);
            Green = 0.0F;
            Blue = 1.0F;
        }
        else if((Wavelength >= 440) && (Wavelength < 490))
        {
            Red = 0.0F;
            Green = (Wavelength - 440) / (490 - 440);
            Blue = 1.0F;
        }
        else if((Wavelength >= 490) && (Wavelength<510))
        {
            Red = 0.0F;
            Green = 1.0F;
            Blue = -(Wavelength - 510) / (510 - 490);
        }
        else if((Wavelength >= 510) && (Wavelength<580))
        {
            Red = (Wavelength - 510) / (580 - 510);
            Green = 1.0F;
            Blue = 0.0F;
        }
        else if((Wavelength >= 580) && (Wavelength<645))
        {
            Red = 1.0F;
            Green = -(Wavelength - 645) / (645 - 580);
            Blue = 0.0F;
        }
        else if((Wavelength >= 645) && (Wavelength<781))
        {
            Red = 1.0F;
            Green = 0.0F;
            Blue = 0.0F;
        }
        else
        {
            Red = 0.0F;
            Green = 0.0F;
            Blue = 0.0F;
        };

        // Let the intensity fall off near the vision limits

        if((Wavelength >= 380) && (Wavelength < 420))
        {
            factor = 0.3F + 0.7F * (Wavelength - 380) / (420 - 380);
        }
        else if((Wavelength >= 420) && (Wavelength < 701))
        {
            factor = 1.0F;
        }
        else if((Wavelength >= 701) && (Wavelength < 781))
        {
            factor = 0.3F + 0.7F * (780 - Wavelength) / (780 - 700);
        }
        else
        {
            factor = 0.0f;
        };

        float[] rgb = new float[3];

        // Don't want 0^x = 1 for x <> 0
        rgb[0] = Red == 0.0 ? 0 : (float)(IntensityMax * Math.pow(Red * factor, Gamma));
        rgb[1] = Green == 0.0 ? 0 : (float)(IntensityMax * Math.pow(Green * factor, Gamma));
        rgb[2] = Blue == 0.0 ? 0 : (float)(IntensityMax * Math.pow(Blue * factor, Gamma));

        return rgb;
    }
}
