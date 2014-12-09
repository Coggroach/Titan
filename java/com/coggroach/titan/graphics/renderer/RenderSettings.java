package com.coggroach.titan.graphics.renderer;

import com.coggroach.titan.tile.TileDirection;

/**
 * Created by TARDIS on 23/11/2014.
 */
public class RenderSettings
{
    public static float NEAR_Z = 1.0F;
    public static float OBJECT_POSITION_Z = 5.0F;
    public static float OBJECT_LENGTH_Z = 2.0F;
    public static int FPS = 60;
    public static long PERIOD = 1000 / FPS;
    public static TileDirection CAMERA_DIRECTION = TileDirection.BACK;

}
