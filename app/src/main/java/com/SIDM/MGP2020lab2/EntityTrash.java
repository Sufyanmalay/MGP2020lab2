package com.SIDM.MGP2020lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Random;

public class EntityTrash implements EntityBase , Collidable {
    private Sprite spriteSheet = null;
    private boolean isDone = false;

    private int chosenLane, screenWidth, screenHeight;
    private float xPos;
    private float yPos;

    public static float xBaseSpeed = 400.f;
    public static float xSpeed = xBaseSpeed;

    private boolean isInit = false;

    private int renderLayer = 1;

    private Bitmap bmp = null;
    private Bitmap scaledBmp = null;

    @Override
    public boolean IsDone(){
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone)
    {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view){
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.trash);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        scaledBmp = Bitmap.createScaledBitmap(bmp, (int)(screenWidth) / 12, (int)(screenHeight) / 7, true);
        spriteSheet = new Sprite(bmp, 1, 1, 30);

        if (chosenLane == 0)
        {
            yPos = screenHeight / 3.f - 175.f;
        }
        else if (chosenLane == 1)
        {
            yPos = screenHeight / 3.f * 2.f - 175.f;
        }
        else if (chosenLane == 2)
        {
            yPos = screenHeight / 3.f * 3.f - 175.f;
        }

        xPos = screenWidth - 150;

        isInit = true;
    }

    @Override
    public void Update (float _dt)
    {
        spriteSheet.Update(_dt);

        xPos -=  xSpeed * _dt;
        if(xPos > screenWidth)
        {
            SetIsDone(true);
        }

    }

    @Override
    public void Render (Canvas _canvas)
    {
        spriteSheet.Render(_canvas, (int)xPos, (int)yPos);
    }

    @Override
    public boolean IsInit()
    {
        return isInit;
    }

    @Override
    public int GetRenderLayer()
    {
        return renderLayer;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        renderLayer = _newLayer;
    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return null;
    }

    public void SetChosenLane(int index) {chosenLane = index;}

    public static EntityTrash Create()
    {
        EntityTrash result = new EntityTrash();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_TRASH);
        return result;
    }

    public static EntityTrash Create(int _Layer, int index)
    {
        EntityTrash result = Create();
        result.SetRenderLayer(_Layer);
        result.SetChosenLane(index);
        return result;
    }

    @Override
    public String GetType()
    {
        return "TrashEntity";
    }

    @Override
    public float GetPosX()
    {
        return xPos;
    }

    @Override
    public float GetPosY()
    {
        return yPos;
    }

    @Override
    public float GetRadius()
    {
        return spriteSheet.GetHeight() * 0.5f;
    }

    @Override
    public void OnHit(Collidable _other)
    {
        if(_other.GetType() == "PlayerEntity")
        {
            SetIsDone(true);
        }
    }
}
