package com.coggroach.titan.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.SoundPool;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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
import com.coggroach.titan.graphics.TileRenderer;
import com.coggroach.titan.tile.Tile;
import com.coggroach.titan.tile.TileColour;

import java.util.Random;

/**
 * Created by TARDIS on 20/11/2014.
 */
public class GameActivity extends Activity
{
    private Game game;
    private GLSurfaceView mGLView;
    private TileRenderer mGLRender;
    private Options options;

    private View.OnTouchListener listener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            game.onTouch(v, event);
            return true;
        }
    };

    protected Game getGameFromId(int i)
    {
        switch (i)
        {
            case 0:
                return new RainbowGame();
            case 1:
                return new MultiGoesGame();
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

        mGLView = new GLSurfaceView(this);
        options = new Options(this);

        game = getGameFromId(options.GAMEMODE);

        mGLRender = new TileRenderer(this);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mGLView.setEGLContextClientVersion(2);
        mGLView.setRenderer(mGLRender);
        mGLView.setOnTouchListener(listener);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        game.generate();
        game.initUIElements(this);

        this.setContentView(mGLView);
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
        options.GAMEMODE=1;
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
