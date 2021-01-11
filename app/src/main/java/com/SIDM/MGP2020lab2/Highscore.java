package com.SIDM.MGP2020lab2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.ImageButton;

public class Highscore extends Activity implements View.OnClickListener, StateBase {

    private ImageButton btn_back;
    private int screenWidth, screenHeight;

    Typeface myFont;

    private RenderTextEntity renderTextEntity;

    @Override

    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.highscoremenu);

        btn_back = findViewById(R.id.btn_back_highscore);
        btn_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();

        if(v == btn_back)
        {
            intent.setClass(this, Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu");
        }
        startActivity(intent);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    @Override
    public String GetName() {
        return "Ranking";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        myFont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/myFont.ttf");

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        renderTextEntity = RenderTextEntity.Create();
    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {

        EntityManager.Instance.Render(_canvas);

        // "Score" Text
        Paint test = new Paint();
        test.setARGB(255, 0, 255, 0);
        test.setStrokeWidth(200);
        test.setTypeface(myFont);
        test.setTextSize(100);
        String text = "Score: ";

        // Score UI
        _canvas.drawText(text, 50, 150, test);
    }

    @Override
    public void Update(float _dt) {
        renderTextEntity.Update(_dt);
    }
}
