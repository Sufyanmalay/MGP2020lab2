package com.SIDM.MGP2020lab2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

// Created by TanSiewLan2020

public class Settingsmenu extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private ImageButton btn_back;
    private Switch Switch2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.settingsmenu);

        Switch2 = (Switch) findViewById ( R.id.switch2 );
        Switch2.setChecked(true);
        Boolean switchState = Switch2.isChecked();

        if (switchState)
        {
            Splashpage.MusiC = true;
        }
        else
        {
            Splashpage.MusiC = false;
        }
        Switch2.setOnClickListener(this);

        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        StateManager.Instance.AddState(new Aboutmenu());
    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent
        String text = " ";
        Intent intent = new Intent();
        if (v == btn_back)
        {
            //Splashpage.MusiC = false;
            intent.setClass(this, Mainmenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (v == Switch2)
        {
            if (Switch2.isChecked())
            {
                Splashpage.MusiC = true;
            }
            else
            {
                Splashpage.MusiC = false;
            }
        }
    }

    @Override
    public void Render(Canvas _canvas) {
    }

    @Override
    public void OnEnter(SurfaceView _view) {
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override
    public String GetName() {
        return "Settingsmenu";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
