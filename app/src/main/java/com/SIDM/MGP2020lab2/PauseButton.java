package com.SIDM.MGP2020lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class PauseButton implements EntityBase
{
    private boolean isInit = false;

    private Bitmap bmpP = null;
    private Bitmap scaledbmpP = null;

    private int renderLayer = 0;

    private int xPos = 0;
    private int yPos = 0;

    private int screenWidth, screenHeight;

    private boolean Paused = false;

    @Override
    public boolean IsDone()
    {
        return false;
    }

    @Override
    public void SetIsDone(boolean _isDone)
    {
        //Nothing happens here
    }

    @Override
    public void Init(SurfaceView _view)
    {
        bmpP = BitmapFactory.decodeResource(_view.getResources(), R.drawable.pause);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        scaledbmpP = Bitmap.createScaledBitmap(bmpP, (int)(screenWidth) / 12, (int)(screenHeight) / 7, true);

        xPos = screenWidth - 150;
        yPos = 150;

        isInit = true;
    }

    @Override
    public void Update(float _dt)
    {
        //Use to check if finger is touching the button = pause button
        if (TouchManager.Instance.HasTouch())
        {
            if (TouchManager.Instance.IsDown() && !Paused)
            {
                //Check collision
                float imgRadius = scaledbmpP.getHeight() * 0.5f;

                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius))
                {
                    AudioManager.Instance.PlayAudio(R.raw.buttonone, 1.0f, 1.0f); // button sound
                    Paused = true;

                    if (PauseDialogFragment.IsShown)
                        return;

                    PauseDialogFragment PauseBox = new PauseDialogFragment();
                    PauseBox.show(GamePage.Instance.getFragmentManager(), "Pause Screen");
                }
            }
        }
        else
            Paused = false;
    }

    @Override
    public void Render(Canvas _canvas)
    {
        _canvas.drawBitmap(scaledbmpP, xPos - scaledbmpP.getWidth() * 0.5f, yPos - scaledbmpP.getHeight() * 0.5f, null);
    }

    @Override
    public boolean IsInit()
    {
        return isInit;
    }

    @Override
    public int GetRenderLayer()
    {
        return LayerConstants.PAUSEBUTTON_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer)
    {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return null;
    }

    public static PauseButton Create()
    {
        PauseButton result = new PauseButton();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PAUSE);
        return result;
    }

}
