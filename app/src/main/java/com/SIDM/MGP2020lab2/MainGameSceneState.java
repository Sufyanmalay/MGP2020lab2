package com.SIDM.MGP2020lab2;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

// Created by TanSiewLan2020

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private float trashTimer = 0.0f;
    private float wallTimer = 0.0f;


    private int[] wallArray = {0, 0, 0};
    private float wallSpawnRate = 5.f;
    private int tmpWall = 0;

    private int[] trashArray = {0, 0, 0};
    private float trashSpawnRate = 2.5f;
    private int tmpTrash = 0;

    private int index = 0;

    private RenderTextEntity renderTextEntity;
    private EntityPlayer Player;

    Random ranGen = new Random();

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
        renderTextEntity = RenderTextEntity.Create();
        PauseButton.Create();
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
    }

    @Override
    public void Update(float _dt) {

        trashTimer += _dt;
        wallTimer += _dt;

        // Pause the game
        if (GameSystem.Instance.GetIsPaused())
            return;

        // Spawning of trash
        if (trashTimer > trashSpawnRate)
        {
            // Further randomise spawning of trash
            // trash should not spawn in the same lane repeatedly
            //
            // eg.       (1, 1, 2, 2, 3, 3, 3, 2) X
            // should be (1, 2, 1, 3, 2, 3, 1, 3)

            index = ranGen.nextInt(3);
            trashArray[tmpTrash] = index;

            if (tmpTrash > 0) //Index 1 and 2
            {
                // Check tmp value with previous tmp value in the array
                // if the same, re-roll
                while (trashArray[tmpTrash] == trashArray[tmpTrash - 1])
                {
                    index = ranGen.nextInt(3);
                    trashArray[tmpTrash] = index;
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

                trashArray[0] = index;
                tmpTrash = 1;
            }

            EntityTrash.Create(1, index);
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

            index = ranGen.nextInt(3);
            wallArray[tmpWall] = index;

            if (tmpWall > 0) //Index 1 and 2
            {
                // Check tmp value with previous tmp value in the array
                // if the same, re-roll
                while (wallArray[tmpWall] == wallArray[tmpWall - 1])
                {
                    index = ranGen.nextInt(3);
                    wallArray[tmpWall] = index;
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

                wallArray[0] = index;
                tmpWall = 1;
            }

            EntityWall.Create(1, index);
            wallTimer = 0.0f;
        }

        EntityManager.Instance.Update(_dt);
        renderTextEntity.Update(_dt);

        if (TouchManager.Instance.IsDown()) {
			
            // Example of touch on screen in the main game to trigger back to Main menu
            // StateManager.Instance.ChangeState("Mainmenu");
        }
    }
}



