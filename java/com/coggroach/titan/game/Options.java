package com.coggroach.titan.game;

import android.content.Context;

import com.coggroach.titan.R;
import com.coggroach.titan.common.AssetReader;

/**
 * Created by TARDIS on 20/11/2014.
 */
public class Options
{
    public static int PALETTE = 1;
    public static String TEXTURE = "metal_texture_bordered.png";
    public static int GAMEMODE = 0;
    public static boolean SOUND = false;
    public static boolean MUSIC = false;
    public static String ANIMATION = "Normal";

    public Options(Context c)
    {
        String[] lines = AssetReader.getString(c, "Options.txt").split("\n");

        for(int i = 0; i < lines.length; i++)
        {
            if(lines[i].contains("TEXTURE"))
                TEXTURE = getStringValue(lines[i]);
            if(lines[i].contains("GAMEMODE"))
                GAMEMODE = getIntegerValue(lines[i]);
            if(lines[i].contains("SOUND"))
                SOUND = getBooleanValue(lines[i]);
            if(lines[i].contains("MUSIC"))
                MUSIC = getBooleanValue(lines[i]);
            if(lines[i].contains("PALETTE"))
                PALETTE = getIntegerValue(lines[i]);
            if(lines[i].contains("ANIMATION"))
                ANIMATION = getStringValue(lines[i]);
        }
    }

    public static String getStringValue(String s)
    {
        if(s.contains("S:"))
        {
            return s.substring( s.indexOf("=") + 1,  s.indexOf(";"));
        }
        return null;
    }

    public static boolean getBooleanValue(String s)
    {
        if(s.contains("B:"))
        {
            String sub = s.substring( s.indexOf("=") + 1,  s.indexOf(";"));
            boolean b = Boolean.valueOf(sub);
            return b;
        }
        return false;
    }

    public static int getIntegerValue(String s)
    {
        if(s.contains("I:"))
        {
            String sub = s.substring( s.indexOf("=") + 1,  s.indexOf(";"));
            int i = Integer.valueOf(sub);
            return i;
        }
        return Integer.MIN_VALUE;
    }
}
