package com.SIDM.MGP2020lab2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2020

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public static final String SHARED_PREF_IDEA = "GameSaveFile";

    // Game stuff
    private boolean isPaused = false;
    private boolean isEnd = false;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        // Get our shared pref
        sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_IDEA,0);

        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new Aboutmenu());
        StateManager.Instance.AddState(new Creditsmenu());
        StateManager.Instance.AddState(new Settingsmenu());
        StateManager.Instance.AddState(new MainGameSceneState());
        StateManager.Instance.AddState(new Highscore());
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

    public void SaveEditBegin()
    {
        if (editor != null)
            return;

        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        if (editor != null)
            return;

        editor.commit();
        editor = null;
    }

    public void SetIntInSave(String _key, int _value)
    {
        if (editor == null)
            return;

        editor.putInt(_key,_value);
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key,10);
    }
}
