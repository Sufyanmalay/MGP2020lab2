package com.SIDM.MGP2020lab2;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

// Created by TanSiewLan2020

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private float trashTimer = 0.0f;

    private int[] coinArray = {0, 0, 0};
    private float spawnRate = 2.5f;
    private int tmp = 0;

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
        //EntityPlayer.;
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {

        trashTimer += _dt;

        // Pause the game
        if (GameSystem.Instance.GetIsPaused())
            return;

        //Spawning of coins
        if (trashTimer > spawnRate)
        {
            // Further randomise spawning of coins
            // Coins should not spawn in the same lane repeatedly
            //
            // eg.       (1, 1, 2, 2, 3, 3, 3, 2) X
            // should be (1, 2, 1, 3, 2, 3, 1, 3)

            index = ranGen.nextInt(3);
            coinArray[tmp] = index;

            if (tmp > 0) //Index 1 and 2
            {
                // Check tmp value with previous tmp value in the array
                // if the same, re-roll
                while (coinArray[tmp] == coinArray[tmp - 1])
                {
                    index = ranGen.nextInt(3);
                    coinArray[tmp] = index;
                }
            }

            ++tmp;

            // Reset array once full
            if (tmp > 2)
            {
                for (int i = 0; i < coinArray.length; ++i)
                {
                    coinArray[i] = 0;
                }

                coinArray[0] = index;
                tmp = 1;
            }

            TrashEntity.Create(1, index);
            trashTimer = 0.0f;
        }

        EntityManager.Instance.Update(_dt);
        renderTextEntity.Update(_dt);

        if (TouchManager.Instance.IsDown()) {
			
            // Example of touch on screen in the main game to trigger back to Main menu
            // StateManager.Instance.ChangeState("Mainmenu");
        }
    }
}



