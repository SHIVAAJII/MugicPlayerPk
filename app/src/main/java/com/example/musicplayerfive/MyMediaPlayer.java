package com.example.musicplayerfive;

import android.media.MediaPlayer;

public class MyMediaPlayer
{

    static MediaPlayer instance;

    public static MediaPlayer getInstance()
    {
        if (instance==null)
        {
            instance = new MediaPlayer();
        }
        return instance;
    }

    public static int currentIdex = -1;

}
