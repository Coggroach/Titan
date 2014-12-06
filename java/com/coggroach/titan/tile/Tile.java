package com.coggroach.titan.tile;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * Created by TARDIS on 20/11/2014.
 */
public class Tile
{
    private TileColour[] colour;
    private TileStats stats;
    private TileAnimation animation;
<<<<<<< HEAD
    private int[] textureId;
=======
    private int textureId;

>>>>>>> origin/ColourPalette


    private static final FloatBuffer[] mModelPositions;
    private static final FloatBuffer[] mModelNormals;
    private static final FloatBuffer[] mModelTextureCoordinates;

/*  private static final int mPositionOffset = 18;
    private static final int mNormalOffset = 18;
    private static final int mTextureOffset = 12;
*/
    private static final int mPositionsLength;
    private static final int mNormalsLength;
    private static final int mTextureCoordinatesLength;

    private static final int mBytesPerFloat = 4;

    static {
        final float[][] tilePositions = new float[][]
                {
                        {
                                // Front face
                                -1.0f, 1.0f, 1.0f,
                                -1.0f, -1.0f, 1.0f,
                                1.0f, 1.0f, 1.0f,
                                -1.0f, -1.0f, 1.0f,
                                1.0f, -1.0f, 1.0f,
                                1.0f, 1.0f, 1.0f
                        },
                        {
                                // Right face
                                1.0f, 1.0f, 1.0f,
                                1.0f, -1.0f, 1.0f,
                                1.0f, 1.0f, -1.0f,
                                1.0f, -1.0f, 1.0f,
                                1.0f, -1.0f, -1.0f,
                                1.0f, 1.0f, -1.0f
                        },
                        {
                                // Back face
                                1.0f, 1.0f, -1.0f,
                                1.0f, -1.0f, -1.0f,
                                -1.0f, 1.0f, -1.0f,
                                1.0f, -1.0f, -1.0f,
                                -1.0f, -1.0f, -1.0f,
                                -1.0f, 1.0f, -1.0f
                        },
                        {
                                // Left face
                                -1.0f, 1.0f, -1.0f,
                                -1.0f, -1.0f, -1.0f,
                                -1.0f, 1.0f, 1.0f,
                                -1.0f, -1.0f, -1.0f,
                                -1.0f, -1.0f, 1.0f,
                                -1.0f, 1.0f, 1.0f
                        },
                        {
                                // Top face
                                -1.0f, 1.0f, -1.0f,
                                -1.0f, 1.0f, 1.0f,
                                1.0f, 1.0f, -1.0f,
                                -1.0f, 1.0f, 1.0f,
                                1.0f, 1.0f, 1.0f,
                                1.0f, 1.0f, -1.0f
                        },
                        {
                                // Bottom face
                                1.0f, -1.0f, -1.0f,
                                1.0f, -1.0f, 1.0f,
                                -1.0f, -1.0f, -1.0f,
                                1.0f, -1.0f, 1.0f,
                                -1.0f, -1.0f, 1.0f,
                                -1.0f, -1.0f, -1.0f
                        }
                };

        final float[][] tileNormals = new float[][]
                {
                        {
                                // Front face
                                0.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f
                        },
                        {
                                // Right face
                                1.0f, 0.0f, 0.0f,
                                1.0f, 0.0f, 0.0f,
                                1.0f, 0.0f, 0.0f,
                                1.0f, 0.0f, 0.0f,
                                1.0f, 0.0f, 0.0f,
                                1.0f, 0.0f, 0.0f
                        },
                        {
                                // Back face
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f
                        },
                        {
                                // Left face
                                -1.0f, 0.0f, 0.0f,
                                -1.0f, 0.0f, 0.0f,
                                -1.0f, 0.0f, 0.0f,
                                -1.0f, 0.0f, 0.0f,
                                -1.0f, 0.0f, 0.0f,
                                -1.0f, 0.0f, 0.0f
                        },
                        {
                                // Top face
                                0.0f, 1.0f, 0.0f,
                                0.0f, 1.0f, 0.0f,
                                0.0f, 1.0f, 0.0f,
                                0.0f, 1.0f, 0.0f,
                                0.0f, 1.0f, 0.0f,
                                0.0f, 1.0f, 0.0f
                        },
                        {
                                // Bottom face
                                0.0f, -1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f
                        }
                };
        final float[][] tileTextureCoordinates = new float[][]
                {
                        {
                                // Front face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f
                        },
                        {
                                // Right face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f
                        },
                        {
                                // Back face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f
                        },
                        {
                                // Left face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f
                        },
                        {
                                // Top face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f
                        },
                        {
                                // Bottom face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f
                        }
                };

        mPositionsLength = tilePositions[0].length;
        mNormalsLength = tileNormals[0].length;
        mTextureCoordinatesLength = tileTextureCoordinates[0].length;

        mModelPositions = new FloatBuffer[6];
        mModelNormals = new FloatBuffer[6];
        mModelTextureCoordinates = new FloatBuffer[6];

        for(int i=0; i<6; i++) {
            mModelPositions[i] = ByteBuffer.allocateDirect(mPositionsLength * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
            mModelNormals[i] = ByteBuffer.allocateDirect(mNormalsLength * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
            mModelTextureCoordinates[i] = ByteBuffer.allocateDirect(mTextureCoordinatesLength * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();

            mModelPositions[i].put(tilePositions[i]).position(0);
            mModelNormals[i].put(tileNormals[i]).position(0);
            mModelTextureCoordinates[i].put(tileTextureCoordinates[i]).position(0);
        }
    }

    public Tile(int i, TileColour c)
    {
        this(i, c, 0);
    }

    public Tile(int i, TileColour c, int texId)
    {
        this.stats = new TileStats(i);
<<<<<<< HEAD
        this.colour = new TileColour[6];
        this.textureId = new int[6];
        for(int j = 0; j < 6; j++)
        {
            this.colour[j] = c;
            this.textureId[j] = texId;
        }
=======
        this.colour = c;
        this.textureId = texId;
>>>>>>> origin/ColourPalette
        this.animation = new TileAnimation();
    }

    public static FloatBuffer getModelPositions(int i)
    {
        return mModelPositions[i];
    }

    public static FloatBuffer getModelTextureCoordinates(int i)
    {
        return mModelTextureCoordinates[i];
    }

    public static FloatBuffer getModelNormals(int i)
    {
        return mModelNormals[i];
    }
/*
    public static int getPositionOffset() {
        return mPositionOffset;
    }

    public static int getNormalOffset() {
        return mNormalOffset;
    }

    public static int getTextureOffset() {
        return mTextureOffset;
    }
*/
    public static int getPositionsLength() {
        return mPositionsLength;
    }

    public static int getNormalsLength() {
        return mNormalsLength;
    }

    public static int getTextureCoordinatesLength() {
        return mTextureCoordinatesLength;
    }

    public float[] getDrawingColour(int index)
    {
        return (stats.isPressed()) ? getColour(index).toFloatArray() : TileColour.white.toFloatArray();
    }

    public TileColour getColour(int index)
    {
        return colour[index];
    }

    public void setColour(TileColour colour, int index)
    {
        this.colour[index] = colour;
    }

    public TileStats getStats()
    {
        return stats;
    }

    protected void setStats(TileStats stats)
    {
        this.stats = stats;
    }

<<<<<<< HEAD
    public int getTextureId(int index)
    {
        return textureId[index];
    }

    public void setTextureId(int textureId, int index)
    {
        this.textureId[index] = textureId;
=======
    public int getTextureId()
    {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
>>>>>>> origin/ColourPalette
    }

    public TileAnimation getAnimation()
    {
        return animation;
    }

}
