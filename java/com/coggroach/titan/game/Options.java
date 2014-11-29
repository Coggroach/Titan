package com.coggroach.titan.game;

import android.content.Context;
import android.util.Log;

import com.coggroach.titan.R;
import com.coggroach.titan.common.AssetReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by TARDIS on 20/11/2014.
 */
public class Options
{
    public int PALETTE = 1;
    public int GAMEMODE = 1;
    public boolean SOUND = false;
    public boolean MUSIC = false;
    public String ANIMATION = "Normal";
    public String TEXTURE = "metal_texture_bordered.png";

    public boolean hasReadIn = false;
    private String optionsFileName = "Options.txt";

    public Options(Context c)
    {
        String[] lines = null;
        try
        {
            FileInputStream fileInputStream = c.openFileInput(optionsFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            String input = "";
            String line = "";

            while((line = reader.readLine()) != null)
                input += line;

            reader.close();
            fileInputStream.close();

            Log.i("Options Input", input);

            lines = input.split("\n");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        if(lines != null)
        {
            for (int i = 0; i < lines.length; i++)
            {
                if (lines[i].contains("TEXTURE"))
                    TEXTURE = getStringValue(lines[i]);
                else if (lines[i].contains("GAMEMODE"))
                    GAMEMODE = getIntegerValue(lines[i]);
                else if (lines[i].contains("SOUND"))
                    SOUND = getBooleanValue(lines[i]);
                else if (lines[i].contains("MUSIC"))
                    MUSIC = getBooleanValue(lines[i]);
                else if (lines[i].contains("PALETTE"))
                    PALETTE = getIntegerValue(lines[i]);
                else if (lines[i].contains("ANIMATION"))
                    ANIMATION = getStringValue(lines[i]);
            }
            hasReadIn = true;
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

        Log.i("Options Output", output);

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
