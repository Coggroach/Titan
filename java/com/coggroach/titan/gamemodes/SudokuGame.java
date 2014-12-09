package com.coggroach.titan.gamemodes;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coggroach.titan.game.Game;
import com.coggroach.titan.game.GameHelper;
import com.coggroach.titan.graphics.renderer.TileRenderer;
import com.coggroach.titan.graphics.views.UIView;
import com.coggroach.titan.tile.Tile;
import com.coggroach.titan.tile.TileColour;

import java.util.ArrayList;

/**
 * Created by richarja on 05/12/14.
 */
public class SudokuGame extends Game
{
    private boolean isGenerated;
    private boolean canRestart;
    private boolean isGameOn;
    private boolean isRendering = false;
    private TileColour defaultColour = TileColour.white;
    private View.OnClickListener endGameListener;
    private int player;

    public SudokuGame()
    {
        this.name = "Sudoku";
        this.initTextureList();
        this.start(9, 9);
    }

    @Override
    public boolean isRendering() {
        return isRendering;
    }

    @Override
    public void initUIElements(Context c) {
        this.UILayout = new UIView(c);
    }

    @Override
    public void start(int w, int h) {
        this.isGenerated = false;
        this.canRestart = true;
        this.isRendering = true;
        this.isGameOn = true;
        this.player = 1;

        this.height = w;
        this.width = h;
        this.tiles = new Tile[w * h];
        for(int i = 0; i < tiles.length; i++)
        {
            tiles[i] = new Tile(i, defaultColour);
        }
    }

    @Override
    public void restart() {
        if(canRestart)
        {
            this.isGenerated = false;
            this.isGameOn = true;
            this.player = 1;
            for(int i = 0; i < tiles.length; i++)
            {
                tiles[i] = new Tile(i, defaultColour);
                tiles[i].setTextureId(0, 0);
            }
        }
    }

    @Override
    public void generate() {

    }

    @Override
    public boolean isGameOn() {
        return isGameOn;
    }

    @Override
    public void setGameOn(boolean b) {
        this.isGameOn = b;
    }

    @Override
    public void onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if((this.isGameOn()))
            {
                float[] worldPos = TileRenderer.getWorldPosFromProjection(event.getX(), event.getY(), v.getWidth(), v.getHeight());

                int iTile = GameHelper.getTileIndexFromWorld(this, worldPos[0], worldPos[1]);

                if (iTile != Integer.MIN_VALUE)
                {
                    if (!this.getTile(iTile).getStats().isPressed())
                    {
                        this.getTile(iTile).getStats().setPressed(true);
                        this.getTile(iTile).setTextureId(player, 0);
                        v.playSoundEffect(SoundEffectConstants.CLICK);

                        if(hasGameFinished(player, iTile))
                        {
                            this.isGameOn = false;
                            //updateUIElement(1, player + " has Won.");
                        }
                        if(hasGameFinished())
                        {
                            this.isGameOn = false;
                            //updateUIElement(1, "Draw");
                        }

                        player = (player == 1) ? 2 : 1;
                    }
                }
            }
        }
    }

    public boolean hasGameFinished()
    {
        for(int i = 0; i < tiles.length; i++)
        {
            if(tiles[i].getTextureId(0) == 0)
                return false;
        }
        return true;
    }

    @Override
    public boolean isAlwaysSquare() {
        return true;
    }

    @Override
    public boolean removeOverlay() {
        return false;
    }

    @Override
    public void tick() {

    }

    public boolean hasGameFinished(int player, int tile)
    {
        int[] square = {8, 1, 6, 3, 5, 7, 4, 9, 2};
        int y = (tile/this.getWidth());
        int x = tile - y * this.getWidth();

        int total = 0;
        for(int i = 0; i < this.getWidth(); i++)
        {
            if(tiles[i + y * this.getWidth()].getTextureId(0) == player)
            {
                total += square[i + y * this.getWidth()];
            }
        }
        if(total == 15)
            return true;

        total = 0;
        for(int i = 0; i < this.getHeight(); i++)
        {
            if(tiles[x + i * this.getHeight()].getTextureId(0) == player)
            {
                total += square[x + i * this.getHeight()];
            }
        }
        if(total == 15)
            return true;

        return false;
    }

    @Override
    public void updateTextureBindings(boolean b) {

    }

    @Override
    public boolean getUpdateTextureBindings() {
        return false;
    }

    @Override
    public void initTextureList()
    {
        this.TextureList = new ArrayList<String>();

        this.TextureList.add("tiles/BorderedWhiteTile.jpg");
        this.TextureList.add("tiles/BorderedCrossTile.jpg");
        this.TextureList.add("tiles/BorderedNoughtTile.jpg");
    }

    @Override
    public void invalidate() {

    }
}
