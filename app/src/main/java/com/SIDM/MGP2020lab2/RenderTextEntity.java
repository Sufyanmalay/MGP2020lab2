package com.SIDM.MGP2020lab2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

// Create a new entity class

public class RenderTextEntity implements EntityBase{

// This is a way to render text on screen using a font type so
// we need to use paint (in android context it is like ink or color)

    // Paint object has a name "paint"
    Paint paint = new Paint();
    // define red, green and blue
    private int red = 0, green = 0, blue = 0;

    private boolean isDone = false;

    int frameCount;
    long lastTime = 0;
    long lastFPSTime = 0;
    float fps;

    // Define the use of Typeface ... name is myfont
    Typeface myfont;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {

        // Use my own fonts
        // Standard font loading using android API
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    @Override
    public void Update(float _dt)
    {

        // get actual fps and print fps on screen
        frameCount++;
        long currentTime = System.currentTimeMillis();

        lastTime = currentTime;

        if(currentTime - lastFPSTime > 1000)
        {
            fps = (frameCount * 1000.f) / (currentTime - lastFPSTime);
            lastFPSTime = currentTime;
            frameCount = 0;
        }

    }

    @Override
    public void Render(Canvas _canvas)
    {
        Paint paint = new Paint(); // Use paint to render text on screen
        paint.setARGB(255, 0,0,0); // Alpha, R, G, B Can make it a variable
        paint.setStrokeWidth(200); // Stroke width is just the thickness of the appearance of the text
        paint.setTypeface(myfont); // using the type of font we defined
        paint.setTextSize(70);     // Text size
        _canvas.drawText("FPS: " + fps, 30, 80, paint); // To render text is drawText FPS: 60
        // drawText(String text, float x, float y, Paint paint)
        // Draw the text, with origin at (x,y), using the specified paint.
    }

    @Override
    public boolean IsInit() {
        return true;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.RENDERTEXT_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_TEXT;}

    public static RenderTextEntity Create()
    {
        RenderTextEntity result = new RenderTextEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_TEXT);
        return result;
    }

}









