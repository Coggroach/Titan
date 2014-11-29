package com.coggroach.titan.game;

import android.content.Context;

import com.coggroach.titan.R;
import com.coggroach.titan.common.AssetReader;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by TARDIS on 20/11/2014.
 */
public class Options
{
    public int PALETTE = 1;
    public String TEXTURE = "metal_texture_bordered.png";
    public int GAMEMODE = 0;
    public boolean SOUND = false;
    public boolean MUSIC = false;
    public String ANIMATION = "Normal";

    private String optionsFileName = "Options.txt";

    public Options(Context c)
    {
        String[] lines = null;
        try
        {
             lines = c.openFileInput(optionsFileName).toString().split("\n");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        if(lines != null)
        {
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains("TEXTURE"))
                    TEXTURE = getStringValue(lines[i]);
                if (lines[i].contains("GAMEMODE"))
                    GAMEMODE = getIntegerValue(lines[i]);
                if (lines[i].contains("SOUND"))
                    SOUND = getBooleanValue(lines[i]);
                if (lines[i].contains("MUSIC"))
                    MUSIC = getBooleanValue(lines[i]);
                if (lines[i].contains("PALETTE"))
                    PALETTE = getIntegerValue(lines[i]);
                if (lines[i].contains("ANIMATION"))
                    ANIMATION = getStringValue(lines[i]);
            }
        }
    }

    public void save(Context c)
    {
        String output = "--MineKeeper Options--\n";

        output += "S:TEXTURE=" + TEXTURE + ";\n";
        output += "I:GAMEMODE=" + GAMEMODE + ";\n";
        output += "B:SOUND=" + SOUND + ";\n";
        output += "B:MUSIC=" + MUSIC + ";\n";
        output += "I:PALETTE=" + PALETTE + ";\n";
        output += "I:ANIMATION=" + ANIMATION + ";\n";

        try
        {
            FileOutputStream oStream = c.openFileOutput(optionsFileName, Context.MODE_PRIVATE);
            oStream.write(output.getBytes());
            oStream.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
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
