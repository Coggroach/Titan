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
import com.coggroach.titan.tile.ITileAnimation;
import com.coggroach.titan.tile.Tile;
import com.coggroach.titan.tile.TileColour;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TARDIS on 27/11/2014.
 */
    public class MultiGoesGame extends Game
{
    private boolean isGenerated;
    private boolean canRestart;
    private boolean isGameOn;
    private boolean hasWon = false;
    private boolean isRendering = false;

    private int score;
    private int lives;
    private static int startingLives = 20;
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
    private TileColour defaultColour = TileColour.white;
    private View.OnClickListener endGameListener;

    public MultiGoesGame()
    {
        this(3, 3);
    }

    @Override
    public boolean isAlwaysSquare() {
        return true;
    }

    @Override
    public boolean removeOverlay() {
        return false;
    }

    protected MultiGoesGame(int w, int h)
    {
        this.name = "MultiGoes";
        this.initTextureList();
        this.start(w, h);
    }

    public int getLives()
    {
        return lives;
    }

    public void incLives()
    {
        this.lives += 3 - this.score/15 + (this.width - 3)/3;
    }

    public void incDifficulty()
    {
        if(this.score%30 < 15)
        {
            this.width++;
            this.height++;
        }
        else
        {
            this.width--;
            this.height--;
        }
    }

    public void resetDifficulty()
    {
        this.width = 3;
        this.height = 3;
    }

    public void decLives()
    {
        this.lives--;
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
        this.TextureList.clear();

        this.TextureList.add("tiles/BorderedMetalTile.png");
        this.TextureList.add("tiles/NuclearBombTile.png");
    }

    @Override
    public void invalidate()
    {
        updateLives();
        updateScore();
        //updateStatus("New Game");
    }

    public void updateUIElement(int i, String s)
    {
        ((TextView) UIElements.get(i)).setText(s);
    }

    @Override
    public void initUIElements(Context c)
    {
        this.UIElements = new ArrayList<View>();
        this.UILayout = new LinearLayout(c);
        this.UIElements.clear();

        TextView name = new TextView(c);
        TextView lives = new TextView(c);
        TextView score = new TextView(c);
        //TextView status = new TextView(c);

        endGameListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!(isGameOn()))
                {
                    isRendering = false;
                    if(hasWon) {
                        incDifficulty();
                        //updateStatus(" ");
                    }
                    else {
                        resetDifficulty();
                        //updateStatus("New Game");
                    }
                    updateLives();
                    updateScore();
                    restart();
                    generate();

                }
            }
        };

        //status.setOnClickListener(endGameListener);

        LinearLayout line = new LinearLayout(c);

        line.addView(lives);
        line.addView(score);
        line.setOrientation(LinearLayout.HORIZONTAL);

        name.setTextSize(30);
        name.setTextColor(Color.WHITE);
        name.setText(this.name);
        lives.setTextSize(30);
        lives.setTextColor(Color.GREEN);
        score.setTextSize(30);
        score.setTextColor(Color.CYAN);
        //status.setTextSize(20);
        //status.setTextColor(Color.WHITE);

        UILayout.addView(name);
        UILayout.addView(line);
        //UILayout.addView(status);
        ((LinearLayout) UILayout).setOrientation(LinearLayout.VERTICAL);

        UIElements.add(name);
        UIElements.add(lives);
        //UIElements.add(status);
        UIElements.add(score);
    }

    private void updateLives()
    {
        ((TextView) UIElements.get(1)).setText("Lives: " + this.lives + "   ");
        if(this.lives <= 8)
            ((TextView) UIElements.get(1)).setTextColor(Color.YELLOW);
        if(this.lives <= 5)
            ((TextView) UIElements.get(1)).setTextColor(TileColour.orange.getColorValue());
        if(this.lives <= 3)
            ((TextView) UIElements.get(1)).setTextColor(Color.RED);
        if(this.lives > 8)
            ((TextView) UIElements.get(1)).setTextColor(Color.GREEN);
    }

    private void updateScore()
    {
        ((TextView) UIElements.get(2)).setText(" Score: " + this.score);
    }

    @Override
    public void start(int w, int h)
    {
        this.isGenerated = false;
        this.canRestart = true;
        this.isRendering = true;
        this.isGameOn = true;
        this.lives = startingLives;
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
            this.canRestart = true;
            //this.isRendering = true;
            this.isGameOn = true;

            this.tiles = new Tile[width * height];
            for(int i = 0; i < tiles.length; i++)
            {
                tiles[i] = new Tile(i, defaultColour);
            }
            this.updateView(true);
            this.isRendering = true;
        }
    }

    @Override
    public void generate()
    {
        if(!isGenerated)
        {
            this.isGenerated = true;
            Random rand = new Random();
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            this.getTile(x, y).getStats().setMine(true);
            GameHelper.generateGrid(this, x, y, 35);
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
        if(event.getAction() == MotionEvent.ACTION_DOWN)//) || event.getAction() == MotionEvent.ACTION_MOVE)
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
                        if(!this.getTile(iTile).getStats().isMine())
                            this.decLives();
                        this.getTile(iTile).getStats().setPressed(true);
                        this.updateLives();
                        v.playSoundEffect(SoundEffectConstants.CLICK);
                    }
                    if(this.getLives() <= 0)
                    {
                        hasWon = false;
                        //this.updateStatus("Hard Luck, Click me to Play Again");
                        this.lives = startingLives;
                        this.score = 0;
                        this.setGameOn(false);
                    }
                    if(this.getTile(iTile).getStats().isMine())
                    {
                        this.getTile(iTile).setTextureId(1, 3);
                        this.getTile(iTile).setColour(defaultColour, 3);

                        hasWon = true;
                        //this.updateStatus("Well Done! Click me to Keep Going");
                        this.incLives();
                        this.incScore();
                        this.setGameOn(false);
                    }
                }
            }
        }
    }

    @Override
    public void updateTextureBindings(boolean b) { }

    @Override
    public boolean getUpdateTextureBindings()
    {
        return false;
    }

    public boolean isGenerated()
    {
        return isGenerated;
    }

    public void incScore() {
        this.score++;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
