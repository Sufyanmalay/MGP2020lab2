package com.SIDM.MGP2020lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import org.w3c.dom.Entity;

import java.util.Random;

// Created by TanSiewLan2020

public class MainGameSceneState implements StateBase {
    public static float rampUpTimer = 0.0f;
    public static float rampUpMax = 2.f;

    private float trashTimer = 0.0f;
    private float wallTimer = 0.0f;

    private int[] wallArray = {0, 0, 0};
    public static float wallBaseSpawnRate = 5.f;
    public static float wallSpawnRate = wallBaseSpawnRate;
    private int tmpWall = 0;

    private int[] trashArray = {0, 0, 0};
    private float trashSpawnRate = 1.5f;
    private int tmpTrash = 0;

    private int laneNumber = 0;

    public static int playerLives = 3;
    public static int playerScore = 0;

    private float regenTimer = 0.0f;
    private float regenTimerMax = 5.0f;

    private Bitmap bmpLives = null;
    private int screenWidth, screenHeight;

    private RenderTextEntity renderTextEntity;

    Random ranGen = new Random();

    Typeface myFont;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        // Example to include another Renderview for Pause Button
        RenderBackground.Create();
        EntityPlayer.Create();
        PauseButton.Create();

        renderTextEntity = RenderTextEntity.Create();

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        // lifes icons
        bmpLives = BitmapFactory.decodeResource(_view.getResources(), R.drawable.live);
        bmpLives = Bitmap.createScaledBitmap(bmpLives, (int)(screenWidth) / 12, (int)(screenHeight) / 7, false);

        // font
        myFont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/myFont.ttf");

    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);

        // Score Display
        Paint scoreCount = new Paint();
        scoreCount.setARGB(255, 0, 255, 0);
        scoreCount.setStrokeWidth(150);
        scoreCount.setTypeface(myFont);
        scoreCount.setTextSize(100);

        Paint scoreDisplay = new Paint();
        scoreDisplay.setARGB(255, 0, 255, 0);
        scoreDisplay.setStrokeWidth(200);
        scoreDisplay.setTypeface(myFont);
        scoreDisplay.setTextSize(100);
        String text = "Score: ";

        if (playerScore < 10) {
            _canvas.drawText(text, screenWidth - 500.f, screenHeight - 200, scoreDisplay);
            _canvas.drawText(String.valueOf(playerScore), screenWidth - 190.f, screenHeight - 200, scoreCount);
        }
        else if (playerScore < 100) {
            _canvas.drawText(text, screenWidth - 500.f, screenHeight - 200, scoreDisplay);
            _canvas.drawText(String.valueOf(playerScore), screenWidth - 200.f, screenHeight - 200, scoreCount);
        }
        else {
            _canvas.drawText(text, screenWidth - 500.f, screenHeight - 200, scoreDisplay);
            _canvas.drawText(String.valueOf(playerScore), screenWidth - 210.f, screenHeight - 200, scoreCount);
        }

        // Lives icon
        for (int i = 0; i < playerLives; ++i)
        {
            _canvas.drawBitmap(bmpLives, screenWidth - 200.f - (i * 175.f), screenHeight - 200.f,null);
        }

    }

    @Override
    public void Update(float _dt) {

        // Pause the game
        if (GameSystem.Instance.GetIsPaused())
            return;

        if (playerLives != 3)
            regenTimer += _dt;

        trashTimer += _dt;
        wallTimer += _dt;
        rampUpTimer += _dt;

        if (regenTimer >= regenTimerMax)
        {
            playerLives += 1;
            regenTimer = 0.f;
        }
        if (rampUpTimer >= rampUpMax)
        {
            if (wallSpawnRate > 1.f)
                wallSpawnRate -= 0.5f;

            if (EntityWall.xSpeed <= 800.f)
            {
                EntityWall.xSpeed += 100.f;
            }
            if (RenderBackground.speed <= 800.f)
            {
                RenderBackground.speed += 50.f;
            }
            if (EntityTrash.xSpeed <= 800.f)
            {
                EntityTrash.xSpeed += 50.f;
            }

            rampUpTimer = 0.f;
        }

        // Spawning of trash
        if (trashTimer > trashSpawnRate)
        {
            // Further randomise spawning of trash
            // trash should not spawn in the same lane repeatedly
            //
            // eg.       (1, 1, 2, 2, 3, 3, 3, 2) X
            // should be (1, 2, 1, 3, 2, 3, 1, 3)

            laneNumber = ranGen.nextInt(3);
            trashArray[tmpTrash] = laneNumber;

            if (tmpTrash > 0)
            {
                // Check tmp value with previous tmp value in the array
                // if the same, re-roll
                while (trashArray[tmpTrash] == trashArray[tmpTrash - 1])
                {
                    laneNumber = ranGen.nextInt(3);
                    trashArray[tmpTrash] = laneNumber;
                }
            }

            ++tmpTrash;

            // Reset array once full
            if (tmpTrash > 2)
            {
                for (int i = 0; i < trashArray.length; ++i)
                {
                    trashArray[i] = 0;
                }

                trashArray[0] = laneNumber;
                tmpTrash = 1;
            }

            EntityTrash.Create(1, laneNumber);
            trashTimer = 0.0f;
        }

        // Spawning of wall
        if (wallTimer > wallSpawnRate)
        {
            // Further randomise spawning of walls
            // walls should not spawn in the same lane repeatedly
            //
            // eg.       (1, 1, 2, 2, 3, 3, 3, 2) X
            // should be (1, 2, 1, 3, 2, 3, 1, 3)

            laneNumber = ranGen.nextInt(3);
            wallArray[tmpWall] = laneNumber;

            if (tmpWall > 0)
            {
                while (wallArray[tmpWall] == wallArray[tmpWall - 1])
                {
                    laneNumber = ranGen.nextInt(3);
                    wallArray[tmpWall] = laneNumber;
                }
            }

            ++tmpWall;

            // Reset array once full
            if (tmpWall > 2)
            {
                for (int i = 0; i < wallArray.length; ++i)
                {
                    wallArray[i] = 0;
                }

                wallArray[0] = laneNumber;
                tmpWall = 1;
            }

            EntityWall.Create(1, laneNumber);
            wallTimer = 0.0f;
        }

        EntityManager.Instance.Update(_dt);
        renderTextEntity.Update(_dt);

        if (TouchManager.Instance.IsDown()) {

        }
    }
}



