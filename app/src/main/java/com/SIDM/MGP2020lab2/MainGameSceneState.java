package com.SIDM.MGP2020lab2;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

// Created by TanSiewLan2020

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private RenderTextEntity renderTextEntity;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        // Example to include another Renderview for Pause Button
        RenderBackground.Create();
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

        // Pause the game
        if (GameSystem.Instance.GetIsPaused())
            return;

        EntityManager.Instance.Update(_dt);
        renderTextEntity.Update(_dt);

        if (TouchManager.Instance.IsDown()) {
			
            // Example of touch on screen in the main game to trigger back to Main menu
            // StateManager.Instance.ChangeState("Mainmenu");
        }
    }
}



