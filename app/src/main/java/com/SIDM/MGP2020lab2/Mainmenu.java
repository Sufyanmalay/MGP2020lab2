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
import android.widget.ImageButton;

// Created by TanSiewLan2020

public class Mainmenu extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_start;
    private Button btn_about;
    private Button btn_credits;
    private ImageButton btn_settings;
    private ImageButton btn_exit;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mainmenu);

        btn_settings = (ImageButton)findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(this);

        btn_exit = (ImageButton)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        btn_about = (Button)findViewById(R.id.btn_about);
        btn_about.setOnClickListener(this);

        btn_credits = (Button)findViewById(R.id.btn_credits);
        btn_credits.setOnClickListener(this);

        StateManager.Instance.AddState(new Mainmenu());
    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();
        if (v == btn_start)
        {
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
 				 StateManager.Instance.ChangeState("Default"); // Default is like a loading page
        }
        else if (v == btn_about)
        {
            intent.setClass(this, Aboutmenu.class);
            StateManager.Instance.ChangeState("Aboutmenu"); // Default is like a loading page
        }
        else if (v == btn_credits)
        {
           intent.setClass(this, Highscore.class);
            StateManager.Instance.ChangeState("Creditsmenu"); // Default is like a loading page
        }
        else if (v == btn_settings)
        {
           intent.setClass(this, Settingsmenu.class);
            StateManager.Instance.ChangeState("Settingsmenu"); // Default is like a loading page
        }
        else if (v == btn_exit)
        {
            System.exit(0);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
        return "Mainmenu";
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
