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
    private int[] textureId;

    private static final FloatBuffer mModelPositions;
    private static final FloatBuffer mModelNormals;
    private static final FloatBuffer mModelTextureCoordinates;

    private static final int mPositionsLength;
    private static final int mNormalsLength;
    private static final int mTextureCoordinatesLength;

    private static final int mBytesPerFloat = 4;

    static
    {
        final float[] tilePositions = new float[]
                {
                                // Front face
                                -1.0f, 1.0f, 1.0f,
                                -1.0f, -1.0f, 1.0f,
                                1.0f, 1.0f, 1.0f,
                                -1.0f, -1.0f, 1.0f,
                                1.0f, -1.0f, 1.0f,
                                1.0f, 1.0f, 1.0f,

                                // Right face
                                1.0f, 1.0f, 1.0f,
                                1.0f, -1.0f, 1.0f,
                                1.0f, 1.0f, -1.0f,
                                1.0f, -1.0f, 1.0f,
                                1.0f, -1.0f, -1.0f,
                                1.0f, 1.0f, -1.0f,

                                // Back face
                                1.0f, 1.0f, -1.0f,
                                1.0f, -1.0f, -1.0f,
                                -1.0f, 1.0f, -1.0f,
                                1.0f, -1.0f, -1.0f,
                                -1.0f, -1.0f, -1.0f,
                                -1.0f, 1.0f, -1.0f,

                                // Left face
                                -1.0f, 1.0f, -1.0f,
                                -1.0f, -1.0f, -1.0f,
                                -1.0f, 1.0f, 1.0f,
                                -1.0f, -1.0f, -1.0f,
                                -1.0f, -1.0f, 1.0f,
                                -1.0f, 1.0f, 1.0f,

                                // Top face
                                -1.0f, 1.0f, -1.0f,
                                -1.0f, 1.0f, 1.0f,
                                1.0f, 1.0f, -1.0f,
                                -1.0f, 1.0f, 1.0f,
                                1.0f, 1.0f, 1.0f,
                                1.0f, 1.0f, -1.0f,

                                // Bottom face
                                1.0f, -1.0f, -1.0f,
                                1.0f, -1.0f, 1.0f,
                                -1.0f, -1.0f, -1.0f,
                                1.0f, -1.0f, 1.0f,
                                -1.0f, -1.0f, 1.0f,
                                -1.0f, -1.0f, -1.0f
                };

        final float[] tileNormals = new float[]
                {
                                // Front face
                                0.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f,
                                0.0f, 0.0f, 1.0f,

                                // Right face
                                1.0f, 0.0f, 0.0f,
                                1.0f, 0.0f, 0.0f,
                                1.0f, 0.0f, 0.0f,
                                1.0f, 0.0f, 0.0f,
                                1.0f, 0.0f, 0.0f,
                                1.0f, 0.0f, 0.0f,

                                // Back face
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,
                                0.0f, 0.0f, -1.0f,

                                // Left face
                                -1.0f, 0.0f, 0.0f,
                                -1.0f, 0.0f, 0.0f,
                                -1.0f, 0.0f, 0.0f,
                                -1.0f, 0.0f, 0.0f,
                                -1.0f, 0.0f, 0.0f,
                                -1.0f, 0.0f, 0.0f,

                                // Top face
                                0.0f, 1.0f, 0.0f,
                                0.0f, 1.0f, 0.0f,
                                0.0f, 1.0f, 0.0f,
                                0.0f, 1.0f, 0.0f,
                                0.0f, 1.0f, 0.0f,
                                0.0f, 1.0f, 0.0f,

                                // Bottom face
                                0.0f, -1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f
                };
        final float[] tileTextureCoordinates = new float[]
                {
                                // Front face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f,

                                // Right face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f,

                                // Back face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f,

                                // Left face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f,

                                // Top face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f,

                                // Bottom face
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f
                };

        mPositionsLength = tilePositions.length;
        mNormalsLength = tileNormals.length;
        mTextureCoordinatesLength = tileTextureCoordinates.length;

        mModelPositions = ByteBuffer.allocateDirect(mPositionsLength * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mModelNormals = ByteBuffer.allocateDirect(mNormalsLength * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mModelTextureCoordinates = ByteBuffer.allocateDirect(mTextureCoordinatesLength * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();

        mModelPositions.put(tilePositions).position(0);
        mModelNormals.put(tileNormals).position(0);
        mModelTextureCoordinates.put(tileTextureCoordinates).position(0);
    }

    public Tile(int i, TileColour c)
    {
        this(i, c, 0);
    }

    public Tile(int i, TileColour c, int texId)
    {
        this.stats = new TileStats(i);
        this.colour = new TileColour[6];
        this.textureId = new int[6];
        for(int j = 0; j < 6; j++)
        {
            this.colour[j] = c;
            this.textureId[j] = texId;
        }
        this.animation = new TileAnimation();
    }

    public static FloatBuffer getModelPositions()
    {
        return mModelPositions;
    }

    public static FloatBuffer getModelTextureCoordinates()
    {
        return mModelTextureCoordinates;
    }

    public static FloatBuffer getModelNormals()
    {
        return mModelNormals;
    }

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

    public int getTextureId(int index)
    {
        return textureId[index];
    }

    public void setTextureId(int textureId, int index)
    {
        this.textureId[index] = textureId;

    }

    public TileAnimation getAnimation()
    {
        return animation;
    }

}
