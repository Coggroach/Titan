package com.coggroach.titan.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.coggroach.titan.R;
import com.coggroach.titan.game.Game;
import com.coggroach.titan.gamemodes.MultiGoesGame;
import com.coggroach.titan.game.Options;
import com.coggroach.titan.gamemodes.RainbowGame;
import com.coggroach.titan.gamemodes.SudokuGame;
import com.coggroach.titan.gamemodes.TicTakToeGame;
import com.coggroach.titan.graphics.views.OptionsView;
import com.coggroach.titan.graphics.renderer.RenderSettings;
import com.coggroach.titan.graphics.renderer.TileRenderer;

/**
 * Created by TARDIS on 20/11/2014.
 */
public class GameActivity extends Activity implements View.OnTouchListener
{
    private Game game;
    private GLSurfaceView mGLView;
    private TileRenderer mGLRender;
    private Options options;
    private OptionsView view;

    private boolean isOptionsFocused = false;
    private String[] gameModes = {"Restart", "Rainbow", "MultiGoes", "TicTakToe"};
    private long CURRENT_TIME = System.currentTimeMillis();

    private Thread renderThread = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            while(true) {
                long dt = System.currentTimeMillis() - CURRENT_TIME;
                if (dt < RenderSettings.PERIOD) {
                    try {
                        Thread.sleep(RenderSettings.PERIOD / 1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                CURRENT_TIME = System.currentTimeMillis();

                mGLView.requestRender();
                if(game != null)
                    game.tick();
            }
        }
    });


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        boolean settingsCheck = (event.getX() > metrics.widthPixels * 0.6F && event.getY() < metrics.widthPixels * 0.15F);
        boolean UICheck = (event.getY() < (metrics.heightPixels - metrics.widthPixels)/2);

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
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
            case 3:
                return new SudokuGame();
            default:
                return  new RainbowGame();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        options = new Options();
        options.load(this);
        view = new OptionsView(this, gameModes);

        mGLView = new GLSurfaceView(this);
        mGLRender = new TileRenderer(this);

        mGLView.setEGLContextClientVersion(2);
        mGLView.setRenderer(mGLRender);
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        mGLView.setOnTouchListener(this);
        view.setOnTouchListener(this);
        renderThread.start();
    }

    public void initGame()
    {
        this.initGame(options.GAMEMODE);
    }

    public void initGame(int i)
    {
        game = null;
        game = getGameFromId(i);
        game.initUIElements(this);
        game.updateView(true);
        game.generate();
        game.initTextureList();
        game.invalidate();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        this.game.getUILayout().removeAllViews();
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


