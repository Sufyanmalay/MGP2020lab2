package com.SIDM.MGP2020lab2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.SurfaceView;
import android.util.DisplayMetrics;
import android.graphics.Typeface;
import android.graphics.Paint;
import android.graphics.Canvas;

import java.util.HashMap;

public class EntityPlayer implements EntityBase, Collidable
{
    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos, yPos;
    private boolean isInit = false;
    private int renderLayer = 0;
    private Sprite spritesheet = null;
    private int screenHeight;
    //public int lives;
    //MainGameSceneState mainGameSceneState = null;

    private boolean hasTouched = false;

    private int position = 0;

    @Override
    public boolean IsDone()
    {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone)
    {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view)
    {
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.player_character); // works
        //bmp = ResourceManager.Instance.GetBitmap(R.drawable.ship2_4); --> doesn't work, crashes
        spritesheet = new Sprite(bmp, 2, 5, 30);
        xPos = 400;
        yPos = 525;
        position = 2;
        isInit = true;
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenHeight = metrics.heightPixels;
        //lives = 3;
    }

    @Override
    public void Update(float _dt)
    {
        spritesheet.Update(_dt);
        if (position == 1)
        {
            yPos = screenHeight / 3.f - 175.f;
        }
        else if (position == 2)
        {
            yPos = screenHeight / 3.f * 2.f - 175.f;
        }
        else if (position == 3)
        {
            yPos = screenHeight / 3.f * 3.f - 175.f;
        }
        xPos = xPos;
        if (!TouchManager.Instance.IsDown())
        {
            hasTouched = false;
        }

        if (TouchManager.Instance.IsDown() && !hasTouched)
        {
            hasTouched = true;
            if (hasTouched)
            {
                if (TouchManager.Instance.GetPosX() >= -100 && TouchManager.Instance.GetPosX() <= 625)
                {
                    AudioManager.Instance.PlayAudio(R.raw.move, 1.0f, 1.0f); // move sound
                    if (TouchManager.Instance.GetPosY() >= 0 && TouchManager.Instance.GetPosY() <= 525)
                    {
                        changeUp();
                    }
                    else if (TouchManager.Instance.GetPosY() > 525 && TouchManager.Instance.GetPosY() <= 1000)
                    {
                        changeDown();
                    }
                }
            }
        }
    }

    public void changeUp()
    {
        if (position == 2)
        {
            position = 1;
        }
        else if (position == 3)
        {
            position = 2;
        }
        else
        {
            position = 1;
        }
    }

    public void changeDown()
    {
        if (position == 2)
        {
            position = 3;
        }
        else if (position == 1)
        {
            position = 2;
        }
        else
        {
            position = 3;
        }
    }

     @Override
    public void Render(Canvas _canvas)
     {
         //_canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);
         spritesheet.Render(_canvas, (int)xPos, (int)yPos);
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

    public static EntityPlayer Create()
    {
        EntityPlayer result = new EntityPlayer();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLAYER);
        return result;
    }

    public static EntityPlayer Create(int _layer)
    {
        EntityPlayer result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType()
    {
        return ENTITY_TYPE.ENT_PLAYER;
    }

    @Override
    public String GetType()
    {
        return "PlayerEntity";
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
    public float GetRadius() {
        return 0;
    }

    @Override
    public void OnHit(Collidable _other)
    {
        if (_other.GetType() == "WallEntity")
        {
            MainGameSceneState.playerLives--;

            // Reset game speed when player is hit
            RenderBackground.speed = RenderBackground.baseSpeed;
            EntityWall.xSpeed = EntityWall.xBaseSpeed;
            EntityTrash.xSpeed = EntityTrash.xBaseSpeed;
            MainGameSceneState.wallSpawnRate = MainGameSceneState.wallBaseSpawnRate;
            MainGameSceneState.playerScore = MainGameSceneState.playerScore / 2;
            xPos = 300;

            if (MainGameSceneState.playerLives <= 0)
            {
                SetIsDone(true);
            }
        }
        if (_other.GetType() == "TrashEntity")
        {
            if (xPos >= 650)
            {
                xPos = 650;
            }
            if (xPos == 300 || xPos == 350)
            {
                MainGameSceneState.playerScore += 5;
            }
            else if (xPos == 400 || xPos == 450)
            {
                MainGameSceneState.playerScore += 10;
            }
            else if (xPos == 500 || xPos == 550)
            {
                MainGameSceneState.playerScore += 15;
            }
            else if (xPos == 600 || xPos == 650)
            {
                MainGameSceneState.playerScore += 20;
            }
            xPos += 50;
        }
    }
}










