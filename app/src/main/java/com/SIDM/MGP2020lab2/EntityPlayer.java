package com.SIDM.MGP2020lab2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    private boolean hasTouched = false;

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
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.ship2_4); // works
        //bmp = ResourceManager.Instance.GetBitmap(R.drawable.ship2_4); --> doesn't work, crashes
        spritesheet = new Sprite(bmp, 1, 1, 30);
        xPos = 0;
        yPos = 0;
        isInit = true;
    }

    @Override
    public void Update(float _dt)
    {
        spritesheet.Update(_dt);
        xPos = 525;
        yPos = 525;
        //lifeTime -= _dt;
        /*if (TouchManager.Instance.HasTouch())
        {
            float imgRadius = spritesheet.GetWidth() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius) || hasTouched )
            {
                hasTouched = true;

                xPos = TouchManager.Instance.GetPosX();
                yPos = TouchManager.Instance.GetPosY();
            }
        }*/
    }

     @Override
    public void Render(Canvas _canvas)
     {
         _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);
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
        if (_other.GetType() == "NextEntity")
        {
            SetIsDone(true);
        }
    }
}










