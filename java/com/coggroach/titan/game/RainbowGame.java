package com.coggroach.titan.game;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coggroach.titan.graphics.TileRenderer;
import com.coggroach.titan.tile.Tile;
import com.coggroach.titan.tile.TileColour;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TARDIS on 23/11/2014.
 */
public class RainbowGame extends Game
{
    private boolean isGenerated;
    private boolean canRestart;
    private boolean isGameOn;
    private boolean isRendering = false;
    private int score;
    private TileColour defaultColour = TileColour.white;
    private View.OnClickListener endGameListener;

    public int getScore()
    {
        return score;
    }

    public void incScore()
    {
        this.score++;
    }

    public RainbowGame()
    {
        this(7, 7);
    }

    protected RainbowGame(int w, int h)
    {
        this.initTextureList();
        this.start(w, h);
    }

    @Override
    public boolean isRendering()
    {
        return isRendering;
    }

    @Override
    public void initTextureList()
    {
        this.TextureList = new ArrayList<String>();

        this.TextureList.add("metal_texture_bordered.png");
        this.TextureList.add("bomb_texture.png");
    }

    @Override
    public void initUIElements(Context c)
    {
        this.UIElements = new ArrayList<View>();
        this.UILayout = new LinearLayout(c);

        TextView score = new TextView(c);
        TextView status = new TextView(c);

        endGameListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!(isGameOn()))
                {
                    updateStatus("New Game");
                    restart();
                    generate();
                }
            }
        };

        status.setOnClickListener(endGameListener);

        ((LinearLayout) UILayout).addView(score);
        ((LinearLayout) UILayout).addView(status);
        ((LinearLayout) UILayout).setOrientation(LinearLayout.VERTICAL);

        score.setTextSize(30);
        score.setTextColor(Color.WHITE);
        status.setTextSize(20);
        status.setTextColor(Color.WHITE);

        UIElements.add(score);
        UIElements.add(status);

        updateScore();
        updateStatus("New Game");
    }

    private void updateScore()
    {
        ((TextView) UIElements.get(0)).setText("Score: " + this.score);
    }

    private void updateStatus(String s)
    {
        ((TextView) UIElements.get(1)).setText(s);
    }

    @Override
    public void start(int w, int h)
    {
        this.isGenerated = false;
        this.canRestart = true;
        this.isRendering = true;
        this.isGameOn = true;
        this.score = 0;

        this.height = w;
        this.width = h;
        this.tiles = new Tile[w * h];
        for(int i = 0; i < tiles.length; i++)
        {
            tiles[i] = new Tile(i, defaultColour);
        }
    }

    @Override
    public void restart()
    {
        if(canRestart)
        {
            this.isGenerated = false;
            this.score = 0;
            this.isGameOn = true;
            for(int i = 0; i < tiles.length; i++)
            {
                tiles[i] = new Tile(i, defaultColour);
            }
        }
    }

    @Override
    public void generate()
    {
        if(!isGenerated)
        {
            Random rand = new Random();
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            this.getTile(x, y).getStats().setMine(true);
            GameHelper.generateGrid(this, x, y, getWidth()*2);
        }
    }

    @Override
    public boolean isGameOn()
    {
        return isGameOn;
    }

    @Override
    public void setGameOn(boolean b)
    {
        this.isGameOn = b;
    }

    @Override
    public void onTouch(View v, MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)//|| event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if((this.isGameOn()))
            {
                float[] worldPos = TileRenderer.getWorldPosFromProjection(event.getX(), event.getY(), v.getWidth(), v.getHeight());

                int iTile = GameHelper.getTileIndexFromWorld(this, worldPos[0], worldPos[1]);

                if (iTile != Integer.MIN_VALUE)
                {
                    if (!this.getTile(iTile).getStats().isPressed())
                    {
                        this.incScore();
                        this.getTile(iTile).getStats().setPressed(true);
                        this.updateScore();
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                    }
                    if(this.getTile(iTile).getStats().isMine())
                    {
                        this.getTile(iTile).setTextureId(1);
                        this.getTile(iTile).setColour(defaultColour);
                        this.setGameOn(false);
                        this.updateStatus("Congratz, Click me to Continue!");
                    }
                }
            }
        }
    }

    @Override
    public void updateView(boolean b) {}

    @Override
    public boolean getUpdateView() {
        return false;
    }

    @Override
    public void updateTextureBindings(boolean b) {}

    @Override
    public boolean getUpdateTextureBindings()
    {
        return false;
    }

    public boolean isGenerated()
    {
        return isGenerated;
    }
}
