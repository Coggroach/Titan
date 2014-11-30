package com.coggroach.titan.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.SoundPool;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coggroach.titan.game.Game;
import com.coggroach.titan.game.MultiGoesGame;
import com.coggroach.titan.game.Options;
import com.coggroach.titan.game.RainbowGame;
import com.coggroach.titan.game.TicTakToeGame;
import com.coggroach.titan.graphics.GameOptionsView;
import com.coggroach.titan.graphics.TileRenderer;
import com.coggroach.titan.tile.Tile;
import com.coggroach.titan.tile.TileColour;

import java.util.Random;

/**
 * Created by TARDIS on 20/11/2014.
 */
public class GameActivity extends Activity implements View.OnTouchListener
{
    private Game game;
    private GLSurfaceView mGLView;
    private TileRenderer mGLRender;
    private Options options;
    private GameOptionsView view;
    private boolean isOptionsFocused = false;
    private String[] gameModes = {"Restart", "Rainbow", "MultiGoes", "TicTakToe"};

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        boolean settingsCheck = (event.getX() > this.getResources().getDisplayMetrics().widthPixels * 0.9F && event.getY() < this.getResources().getDisplayMetrics().heightPixels * 0.1F);

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(settingsCheck && isOptionsFocused)
            {
                isOptionsFocused = false;
                mGLRender.setGamma(1.0F);
                mGLView.requestFocus();
                view.setVisible(false);
                return true;
            }
            else if (settingsCheck && !isOptionsFocused) {
                isOptionsFocused = true;
                mGLRender.setGamma(0.5F);
                view.requestFocus();
                view.setVisible(true);
                return true;
            }
        }

        if(isOptionsFocused)
        {
            view.onTouch(v, event);
        }
        else if(!isOptionsFocused)
        {
            game.onTouch(v, event);
        }
        return true;
    }

    protected Game getGameFromId(int i)
    {
        switch (i)
        {
            case 0:
                return new RainbowGame();
            case 1:
                return new MultiGoesGame();
            case 2:
                return new TicTakToeGame();
            default:
                return  new RainbowGame();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        options = new Options();
        view = new GameOptionsView(this, gameModes);

        mGLView = new GLSurfaceView(this);
        mGLRender = new TileRenderer(this);

        mGLView.setEGLContextClientVersion(2);
        mGLView.setRenderer(mGLRender);
        mGLView.setOnTouchListener(this);
        view.setOnTouchListener(this);
    }

    public void initGame()
    {
        this.initGame(options.GAMEMODE);
    }

    public void initGame(int i)
    {
        game = getGameFromId(i);
        game.updateView(true);
        game.generate();
        game.initUIElements(this);
        game.initTextureList();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        this.onStart();
        this.mGLRender.loadTextureData();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        initGame();
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setContentView(mGLView);
        this.addContentView(view, params);
        this.addContentView(game.getUILayout(), params);
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        options.save(this);
    }


    public Game getGame()
    {
        return game;
    }

    public Options getOptions()
    {
        return options;
    }
}
