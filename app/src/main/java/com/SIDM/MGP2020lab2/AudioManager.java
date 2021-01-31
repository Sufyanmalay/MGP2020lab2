package com.SIDM.MGP2020lab2;

import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.util.HashMap;

public class AudioManager
{
    public final static AudioManager Instance = new AudioManager();
    public static final int STREAM_MUSIC = 0;

    //Still a singleton
    private SurfaceView view = null;
    private HashMap<Integer, MediaPlayer> audioMap = new HashMap<Integer, MediaPlayer>();

    //Protect the singleton
    private AudioManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        view = _view;
        Exit(); // Create our own to release the media loaded and clear audio map
    }

    public void Exit()
    {
        for (HashMap.Entry<Integer, MediaPlayer> entry : audioMap.entrySet())
        {
            entry.getValue().stop();
            entry.getValue().reset();
            entry.getValue().release();
        }
        audioMap.clear();
    }

    //After loading, adjust volume or set volume
    public void PlayAudio(int _id, float _volumeLeft, float _volumeRight)
    {
        //Check if audio is loaded or not
        if (audioMap.containsKey(_id)) //Audio clip is present
        {
            //Clip is not NULL
            MediaPlayer curr = audioMap.get(_id);
            curr.seekTo(0); //Where in the clip to start
            curr.setVolume(_volumeLeft, _volumeRight); //Set the volume of the clip (Left and right volume)
            curr.start();
        }
        else //Place audio clip into hash map
        {
            //Load the audio immediately
            MediaPlayer curr = MediaPlayer.create(view.getContext(), _id);
            audioMap.put(_id, curr); //User can change the background music and sfx manually here too
            curr.start();
        }
    }

    public void ChangeVolume(int _id, float _volumeLeft, float _volumeRight) // change vol of music when paused
    {
        if (audioMap.containsKey(_id)) //Audio clip is present
        {
            //Clip is not NULL
            MediaPlayer curr = audioMap.get(_id);
            curr.setVolume(_volumeLeft, _volumeRight); //Set the volume of the clip (Left and right volume)
        }
    }

    //Stopping the audio
    public void StopAudio(int _id)
    {
        MediaPlayer Audio = audioMap.get(_id);
        Audio.pause();
    }
}
