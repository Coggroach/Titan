package com.coggroach.titan.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.coggroach.titan.graphics.views.ButtonView;
import com.coggroach.titan.graphics.views.OnButtonViewListener;
import com.coggroach.titan.graphics.views.OptionsView;
import com.coggroach.titan.graphics.renderer.RenderSettings;
import com.coggroach.titan.graphics.renderer.TileRenderer;
import com.coggroach.titan.graphics.views.StringView;
import com.coggroach.titan.graphics.views.UIView;

/**
 * Created by TARDIS on 20/11/2014.
 */
public class GameActivity extends Activity implements View.OnTouchListener
{
    private Game game;
    private GLSurfaceView mGLView;
    private TileRenderer mGLRender;
    private Options options;
    private UIView listView;
    private UIView settings;
    private String[] gameModes = {"Restart", "Classic", "TicTakToe", "Debug"};
    private long CURRENT_TIME = System.currentTimeMillis();

    private int FOCUSED_VIEW = 0;
    private final int FOCUS_OPTIONS = 1;
    private final int FOCUS_UI = 2;
    private final int FOCUS_GAME = 3;
    private boolean IS_FOCUS_OPTIONS = false;

    private Thread renderThread = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            while(true) {
                long dt = System.currentTimeMillis() - CURRENT_TIME;
                if (dt < RenderSettings.PERIOD) {
                    try {
                        Thread.sleep((RenderSettings.PERIOD - dt), 0);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                CURRENT_TIME = System.currentTimeMillis();

                mGLView.requestRender();
                if(game != null)
                {
                    game.tick();
                }
            }
        }
    });

    private OnButtonViewListener settingsListener = new OnButtonViewListener()
    {
        @Override
        public void onTouch(View view, MotionEvent event)
        {
            int i = (listView.getIndexOfViewWithContains(1, (int) event.getX(), (int) event.getY()) - 1);
            Log.i("MODE", String.valueOf(i));
            if(i != Integer.MIN_VALUE)
            {
                if(i == 0)
                    onRestart();
                else
                {
                    options.GAMEMODE = (i - 1);
                    onRestart();
                }
            }

        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        boolean settingsCheck = (event.getX() > metrics.widthPixels * 0.6F && event.getY() < metrics.widthPixels * 0.15F);
        boolean UICheck = (event.getY() < (metrics.heightPixels - metrics.widthPixels)/2);

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(UICheck && !settingsCheck && !this.IS_FOCUS_OPTIONS)
            {
                this.FOCUSED_VIEW = FOCUS_UI;
            }
            else if(settingsCheck && !this.IS_FOCUS_OPTIONS)
            {
                this.FOCUSED_VIEW = FOCUS_OPTIONS;
                this.IS_FOCUS_OPTIONS = true;
                mGLRender.setGamma(0.5F);
                listView.requestFocus();
                listView.setVisible(true);
                game.setRendering(false);
                return true;
            }
            else if(settingsCheck && this.IS_FOCUS_OPTIONS)
            {
                this.FOCUSED_VIEW = FOCUS_GAME;
                this.IS_FOCUS_OPTIONS = false;
                mGLRender.setGamma(1.0F);
                mGLView.requestFocus();
                listView.setVisible(false);
                game.setRendering(true);
                return true;
            }
            else if(!UICheck && !settingsCheck && !this.IS_FOCUS_OPTIONS)
            {
                this.FOCUSED_VIEW = FOCUS_GAME;
            }

            switch (FOCUSED_VIEW)
            {
                case FOCUS_GAME:
                    game.onTouch(v, event);
                    break;
                case FOCUS_OPTIONS:
                    listView.onTouch(v, event);
                    break;
                case FOCUS_UI:
                    game.getUILayout().onTouch(v, event);
                    break;
            }
        }
        return true;
    }

    protected Game getGameFromId(int i)
    {
        switch (i)
        {
            case 2:
                return new RainbowGame();
            case 0:
                return new MultiGoesGame();
            case 1:
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
        listView = new UIView(this);
        settings = new UIView(this);

        this.initSettings();

        mGLView = new GLSurfaceView(this);
        mGLRender = new TileRenderer(this);

        mGLView.setEGLContextClientVersion(2);
        mGLView.setRenderer(mGLRender);
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        mGLView.setOnTouchListener(this);
        listView.setOnTouchListener(this);
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
        game.updateView(true);
        game.initUIElements(this);
        game.generate();
        game.initTextureList();
        game.invalidate();
        game.updateView(false);
    }

    public void initSettings()
    {
        settings.addView(new ButtonView(this, "interface/ButtonGear.png", 0.8F, 0.0125F, 0.15F, 0.15F/1.778F));
        settings.addView(new ButtonView(this, "interface/Palette.png", 0F, 0.775F, 1F, 0.3F/1.778F));

        float xPoint = 0.2F;
        float yPoint = 0.25F;
        float scale = 0.20F;
        float offset = 0.02F;

        listView.addView(new ButtonView(this, "interface/BackgroundSettings.png", 0F, 0.2F, 1F, 1F/1.778F));
        for(int i = 0; i < this.gameModes.length; i++)
        {
            ButtonView tempButton = new ButtonView(this, "interface/Button" + gameModes[i] +  ".png", xPoint, yPoint, 0.60F, 0.1F);
           // StringView tempString = new StringView(this, this.gameModes[i], xPoint + 0.12F, yPoint + 0.05F, scale);

            tempButton.setOnButtonViewListener(settingsListener);
           // tempString.setOnButtonViewListener(settingsListener);

            listView.addView(tempButton);
           // listView.addView(tempString);

            yPoint += 0.1F + offset;

        }
        listView.setVisible(false);
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
        this.addContentView(settings, params);
        this.addContentView(listView, params);
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


