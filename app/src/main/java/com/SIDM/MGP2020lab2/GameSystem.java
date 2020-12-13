package com.SIDM.MGP2020lab2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2020

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();

    // Game stuff
    private boolean isPaused = false;
    private boolean isEnd = false;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new Aboutmenu());
        StateManager.Instance.AddState(new Creditsmenu());
        StateManager.Instance.AddState(new Settingsmenu());
        StateManager.Instance.AddState(new MainGameSceneState());
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public void SetIsEnd(boolean _newIsEnd)
    {
        isEnd = _newIsEnd;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

    public boolean GetIsEnd()
    {
        return isEnd;
    }
}
