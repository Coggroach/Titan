package com.coggroach.titan.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.coggroach.titan.graphics.views.MenuView;
import com.coggroach.titan.graphics.views.OnButtonViewListener;

/**
 * Created by ggunn on 05/12/14.
 */
public class MenuActivity extends Activity implements OnButtonViewListener
{
    MenuView mView;
    Intent intent = new Intent(this, GameActivity.class);


    public void onTouch(View view, MotionEvent motionEvent) {

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActionBar().hide();
        mView = new MenuView(this);

        mView.setOnButtonViewListener(0, this);

        this.setContentView(mView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}