package com.coggroach.titan.gamemodes;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Matrix;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coggroach.titan.game.Game;
import com.coggroach.titan.game.GameHelper;
import com.coggroach.titan.graphics.renderer.TileRenderer;
import com.coggroach.titan.tile.Tile;
import com.coggroach.titan.tile.ITileAnimation;
import com.coggroach.titan.tile.TileColour;

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
    private int ANIMATION_LENGTH = 72/4;
    private ITileAnimation animation = new ITileAnimation()
    {
        @Override
        public float[] onAnimation(int i, float[] mMVPMatrix)
        {
            //i Limit to 8 Here
            Matrix.rotateM(mMVPMatrix, 0, (i * 5), 0.0F, 1.0F, 0.0F);
            return mMVPMatrix;
        }
    };

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
        this.name = "Rainbow";
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

        this.TextureList.add("tiles/BorderedWhiteTile.jpg");
        this.TextureList.add("tiles/NuclearBombTile.png");
    }

    @Override
    public void invalidate() {
        updateScore();
        updateStatus("New Game");
    }

    @Override
    public void initUIElements(Context c)
    {
        this.UIElements = new ArrayList<View>();
        this.UILayout = new LinearLayout(c);

        TextView name = new TextView(c);
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

        UILayout.addView(name);
        UILayout.addView(score);
        UILayout.addView(status);
        ((LinearLayout) UILayout).setOrientation(LinearLayout.VERTICAL);

        name.setTextSize(30);
        name.setTextColor(Color.WHITE);
        name.setText(this.name);
        score.setTextSize(30);
        score.setTextColor(Color.WHITE);
        status.setTextSize(20);
        status.setTextColor(Color.WHITE);

        UIElements.add(name);
        UIElements.add(score);
        UIElements.add(status);
    }

    private void updateScore()
    {
        ((TextView) UIElements.get(1)).setText("Score: " + this.score);
    }

    private void updateStatus(String s)
    {
        ((TextView) UIElements.get(2)).setText(s);
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
            GameHelper.generateGrid(this, x, y, getWidth() * 2);
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

        if(event.getAction() == MotionEvent.ACTION_DOWN|| event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if((this.isGameOn()))
            {
                float[] worldPos = TileRenderer.getWorldPosFromProjection(event.getX(), event.getY(), v.getWidth(), v.getHeight());

                int iTile = GameHelper.getTileIndexFromWorld(this, worldPos[0], worldPos[1]);

                if (iTile != Integer.MIN_VALUE)
                {
                    if (!this.getTile(iTile).getStats().isPressed())
                    {
                        this.getTile(iTile).getAnimation().setAnimation(this.animation);
                        this.getTile(iTile).getAnimation().setAnimationLength(this.ANIMATION_LENGTH);
                        this.getTile(iTile).getAnimation().setAnimationLoop(false);
                        this.getTile(iTile).getAnimation().setAnimationTickLength(1);
                        this.getTile(iTile).getAnimation().setSaveAnimation(true);
                        this.incScore();
                        this.getTile(iTile).getStats().setPressed(true);
                        this.updateScore();
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                    }
                    if(this.getTile(iTile).getStats().isMine())
                    {
                        this.getTile(iTile).setTextureId(1, 3);
                        this.getTile(iTile).setColour(defaultColour, 3);
                        this.setGameOn(false);
                        this.updateStatus("Congratz, Click me to Continue!");
                    }
                }
            }
        }
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
