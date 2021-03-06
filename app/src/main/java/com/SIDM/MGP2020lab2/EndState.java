package com.SIDM.MGP2020lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class EndState implements EntityBase
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

        xPos = screenWidth - 300;
        yPos = 300;

        isInit = true;
    }

    @Override
    public void Update(float _dt)
    {
        if (MainGameSceneState.playerLives <= 0)
        {
            AudioManager.Instance.PlayAudio(R.raw.end, 1.0f, 1.0f); // end sound
            if (!Paused)
            {
                Paused = true;

                if (EndConfirmDialogFragment.IsShown)
                    return;

                EndConfirmDialogFragment EndState = new EndConfirmDialogFragment();
                EndState.show(GamePage.Instance.getFragmentManager(), "End Screen");
            }
        }
        else
        {
            Paused = false;
        }

        //Use to check if finger is touching the button = pause button
        /*if (TouchManager.Instance.HasTouch())
        {
            if (TouchManager.Instance.IsDown() && !Paused)
            {
                //Check collision
                float imgRadius = scaledbmpP.getHeight() * 0.5f;

                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius))
                {
                    Paused = true;

                    if (PauseConfirmDialogFragment.IsShown)
                        return;

                    PauseConfirmDialogFragment PauseBox = new PauseConfirmDialogFragment();
                    PauseBox.show(GamePage.Instance.getFragmentManager(), "Pause Screen");
                }
            }
        }
        else
            Paused = false;*/
    }

    @Override
    public void Render(Canvas _canvas)
    {
        //_canvas.drawBitmap(scaledbmpP, xPos - scaledbmpP.getWidth() * 0.5f, yPos - scaledbmpP.getHeight() * 0.5f, null);
    }

    @Override
    public boolean IsInit()
    {
        return isInit;
    }

    @Override
    public int GetRenderLayer()
    {
        return LayerConstants.ENDSTATE_LAYER;
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

    public static EndState Create()
    {
        EndState result = new EndState();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_END);
        return result;
    }
}
