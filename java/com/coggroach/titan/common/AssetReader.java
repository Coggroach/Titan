package com.coggroach.titan.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by TARDIS on 29/11/2014.
 */
public class AssetReader
{
    public static String getString(Context c, String fileName)
    {
        String s = "";

        try
        {
            InputStreamReader iStreamReader = new InputStreamReader( c.getResources().getAssets().open(fileName));
            BufferedReader reader = new BufferedReader(iStreamReader);

            String line;

            while( (line = reader.readLine()) != null)
            {
                s += line;
                s += "\n";
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return s;
    }

    public static int loadTexture(final Context c, final String fileName)
    {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;	// No pre-scaling

            try
            {
                // Read in the resource
                final Bitmap bitmap = BitmapFactory.decodeStream(c.getResources().getAssets().open(fileName));

                // Bind to the texture in OpenGL
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

                // Set filtering
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

                // Load the bitmap into the bound texture.
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

                // Recycle the bitmap, since its data has been loaded into OpenGL.
                bitmap.recycle();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }
}
