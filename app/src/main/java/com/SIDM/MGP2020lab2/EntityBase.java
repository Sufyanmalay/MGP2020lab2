package com.SIDM.MGP2020lab2;

import android.graphics.Canvas;
import android.view.SurfaceView;

// Created by TanSiewLan2020

public interface EntityBase
{
 	 //used for entities such as background
    enum ENTITY_TYPE{
        ENT_BACKGROUND,
        ENT_TEXT,
        ENT_PAUSE,
        //ENT_TEXT,
        //ENT_NEXT,
        ENT_DEFAULT,
    }

    boolean IsDone();
    void SetIsDone(boolean _isDone);

    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);

    boolean IsInit();

    int GetRenderLayer();
    void SetRenderLayer(int _newLayer);

	 ENTITY_TYPE GetEntityType();
}
