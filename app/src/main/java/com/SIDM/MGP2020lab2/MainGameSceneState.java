package com.SIDM.MGP2020lab2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import java.util.Random;

// Created by TanSiewLan2020

public class MainGameSceneState implements StateBase {
    public static float rampUpTimer = 0.0f;
    public static float rampUpMax = 5.f;

    private float trashTimer = 0.0f;
    private float wallTimer = 0.0f;

    public static int playerLives = 3;
    public static int playerScore = 0;

    private int[] wallArray = {0, 0, 0};
    public static float wallBaseSpawnRate = 3.f;
    public static float wallSpawnRate = wallBaseSpawnRate;
    private int tmpWall = 0;

    private int[] trashArray = {0, 0, 0};
    private float trashSpawnRate = 1.5f;
    private int tmpTrash = 0;

    private int laneNumber = 0;

    private float regenTimer = 0.0f;
    private float regenTimerMax = 10.0f;

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
        // Reset's game whenever player enters
        ResetGame();
        // Example to include another Renderview for Pause Button
        RenderBackground.Create();
        EntityPlayer.Create();
        PauseButton.Create();
        EndState.Create();

        renderTextEntity = RenderTextEntity.Create();

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        // live icons
        bmpLives = BitmapFactory.decodeResource(_view.getResources(), R.drawable.live);
        bmpLives = Bitmap.createScaledBitmap(bmpLives, (int)(screenWidth) / 12, (int)(screenHeight) / 7, false);

        // font
        myFont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/myFont.ttf");

        if (Splashpage.MusiC)
        {
            Splashpage.vol = 0.6f;
        }
        else
        {
            Splashpage.vol = 0.0f;
        }
        AudioManager.Instance.PlayAudio(R.raw.music, Splashpage.vol, Splashpage.vol); // play music when game start
    }

    public void ResetGame() {
        playerLives = 3;
        playerScore = 0;

        EntityWall.xSpeed = EntityWall.xBaseSpeed;
        EntityTrash.xSpeed = EntityTrash.xBaseSpeed;
        RenderBackground.speed = RenderBackground.baseSpeed;
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

        // Score counter
        Paint scoreCount = new Paint();
        scoreCount.setARGB(255, 0, 255, 0);
        scoreCount.setStrokeWidth(150);
        scoreCount.setTypeface(myFont);
        scoreCount.setTextSize(100);

        // "Score" Text
        Paint scoreDisplay = new Paint();
        scoreDisplay.setARGB(255, 0, 255, 0);
        scoreDisplay.setStrokeWidth(200);
        scoreDisplay.setTypeface(myFont);
        scoreDisplay.setTextSize(100);
        String text = "Score: ";

        Paint scoreCount2 = new Paint();
        scoreCount2.setARGB(255, 0, 255, 0);
        scoreCount2.setStrokeWidth(150);
        scoreCount2.setTypeface(myFont);
        scoreCount2.setTextSize(100);

        Paint scoreToBeat = new Paint();
        scoreToBeat.setARGB(255, 0, 255, 0);
        scoreToBeat.setStrokeWidth(200);
        scoreToBeat.setTypeface(myFont);
        scoreToBeat.setTextSize(100);
        String text1 = "Score to beat: ";

        // Score UI
        _canvas.drawText(text, 100.f, screenHeight - 200.f, scoreDisplay);
        _canvas.drawText(String.valueOf(playerScore), 350.f,  screenHeight - 200.f, scoreCount);

        _canvas.drawText(text1, 100.f, screenHeight - 100.f, scoreToBeat);
        _canvas.drawText(String.valueOf(GameSystem.Instance.GetIntFromSave("Score")), 600.f,  screenHeight - 100.f, scoreCount2);

        // Lives UI
        for (int i = 0; i < playerLives; ++i)
        {
            _canvas.drawBitmap(bmpLives, screenWidth - 200.f - (i * 175.f), screenHeight - 200.f,null);
        }
    }

    @Override
    public void Update(float _dt) {

        // Music
        if (GameSystem.Instance.GetIsPaused() || GameSystem.Instance.GetIsEnd())
        {
            if (Splashpage.MusiC)
            {
                Splashpage.vol = 0.4f;
            }
            else
            {
                Splashpage.vol = 0.0f;
            }
            AudioManager.Instance.ChangeVolume(R.raw.music, Splashpage.vol, Splashpage.vol); // decrease vol of music while paused
            return;
        }
        else {
            if (Splashpage.MusiC) {
                Splashpage.vol = 0.8f;
            } else {
                Splashpage.vol = 0.0f;
            }
            AudioManager.Instance.ChangeVolume(R.raw.music, Splashpage.vol, Splashpage.vol); // increase vol of music while playing
        }

        // Pause the game
        if (GameSystem.Instance.GetIsPaused() || GameSystem.Instance.GetIsEnd())
            return;

        if (playerLives != 3)
            regenTimer += _dt;

        trashTimer += _dt;
        wallTimer += _dt;
        rampUpTimer += _dt;

        // Life Regen
        if (regenTimer >= regenTimerMax)
        {
            playerLives += 1;
            regenTimer = 0.f;
        }

        // Ramp Up the game
        // Increase speed of the game
        if (rampUpTimer >= rampUpMax)
        {
            if (wallSpawnRate > 1.f)
                wallSpawnRate -= 1.f;

            if (EntityWall.xSpeed <= 800.f)
            {
                EntityWall.xSpeed += 100.f;
            }
            if (RenderBackground.speed <= 800.f)
            {
                RenderBackground.speed += 100.f;
            }
            if (EntityTrash.xSpeed <= 800.f)
            {
                EntityTrash.xSpeed += 100.f;
            }

            rampUpTimer = 0.f;
        }


        // Spawning of wall & trash
        if (wallTimer > wallSpawnRate)
        {
            // Further randomise spawning of walls
            // walls should not spawn in the same lane repeatedly
            //
            // eg.       (1, 1, 2, 2, 3, 3, 3, 2) X
            // should be (1, 2, 1, 3, 2, 3, 1, 3)

            laneNumber = ranGen.nextInt(3);
            int rngSpawn2Walls = ranGen.nextInt(2) + 1;
            int rng = ranGen.nextInt(2) + 1;

            // Spawns 2 walls
            if (rngSpawn2Walls == 1)
            {
                // Top wall
                if (laneNumber == 0)
                {
                    EntityWall.Create(1, laneNumber);

                    if (rng == 1)
                    {
                        EntityWall.Create(1, laneNumber + 1);
                        EntityTrash.Create(1, laneNumber + 2);
                    }
                    else
                    {
                        EntityTrash.Create(1, laneNumber + 1);
                        EntityWall.Create(1, laneNumber + 2);
                    }
                }
                // Middle wall
                else if (laneNumber == 1)
                {
                    EntityWall.Create(1, laneNumber);

                    if (rng == 1)
                    {
                        EntityTrash.Create(1, laneNumber - 1);
                        EntityWall.Create(1, laneNumber + 1);
                    }
                    else
                    {
                        EntityWall.Create(1, laneNumber - 1);
                        EntityTrash.Create(1, laneNumber + 1);
                    }
                }
                // Bottom wall
                else if (laneNumber == 2)
                {
                    EntityWall.Create(1, laneNumber);

                    if (rng == 1)
                    {
                        EntityWall.Create(1, laneNumber - 1);
                        EntityTrash.Create(1, laneNumber - 2);
                    }
                    else
                    {
                        EntityTrash.Create(1, laneNumber - 1);
                        EntityWall.Create(1, laneNumber - 2);
                    }
                }
            }
            // Spawns only 1 wall
            else
            {
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

                int ran = ranGen.nextInt(2);

                if (laneNumber == 0)
                {
                    if (rng == 0)
                    {
                        EntityTrash.Create(1,laneNumber + 1);
                    }
                    else
                    {
                        EntityTrash.Create(1,laneNumber + 2);
                    }
                }
                else if (laneNumber == 1)
                {
                    if (rng == 0)
                    {
                        EntityTrash.Create(1,laneNumber + 1);
                    }
                    else
                    {
                        EntityTrash.Create(1,laneNumber - 1);
                    }
                }
                else
                {
                    if (rng == 0)
                    {
                        EntityTrash.Create(1,laneNumber - 1);
                    }
                    else
                    {
                        EntityTrash.Create(1,laneNumber - 2);
                    }
                }
            }

            wallTimer = 0.0f;
        }

        EntityManager.Instance.Update(_dt);
        renderTextEntity.Update(_dt);

        // When player dies, update high score
        // Change gamestate
        if (playerLives <= 0)
        {
            if (playerScore > GameSystem.Instance.GetIntFromSave("Score"))
            {
                int tempScore = playerScore;
                GameSystem.Instance.SaveEditBegin();
                GameSystem.Instance.SetIntInSave("Score", tempScore);
                GameSystem.Instance.SaveEditEnd();
            }
        }
    }
}



