package com.example.musicplayerfive;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MugicPlayerActivity extends AppCompatActivity {


    TextView titleTv, currentTimeTv, totalTimeTv;
    ImageView pausePlay, nextBtn, previousBtn, musicIcon;
    SeekBar seekBar;

    ArrayList<AudioModel> songsList;
    AudioModel currentSong;

    int x = 0;

    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mugic_player);


        titleTv=(TextView) findViewById(R.id.song_title);
        currentTimeTv=(TextView) findViewById(R.id.current_time);
        totalTimeTv=(TextView) findViewById(R.id.total_time);

        pausePlay=(ImageView) findViewById(R.id.pause);
        nextBtn=(ImageView) findViewById(R.id.next);
        previousBtn=(ImageView) findViewById(R.id.previous);
        musicIcon=(ImageView) findViewById(R.id.music_icon_big);

        seekBar =(SeekBar) findViewById(R.id.seek_bar);
        seekBar =(SeekBar) findViewById(R.id.seek_bar);




        titleTv.setSelected(true);




        songsList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");

        setResourceWithMusic();


        MugicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mediaPlayer!=null)
                {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());

                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));

                    if (mediaPlayer.isPlaying())
                    {
                        pausePlay.setImageResource(R.drawable.baseline_block_flipped_24);

                        musicIcon.setRotation(x++);

                    }
                    else
                    {
                        pausePlay.setImageResource(R.drawable.baseline_brightness_1_24);

                        musicIcon.setRotation(0);

                    }

                }

                new Handler().postDelayed(this, 100);

            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                if (mediaPlayer != null && fromUser)
                {

                    mediaPlayer.seekTo(progress);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }





    void setResourceWithMusic()
    {


        currentSong = songsList.get(MyMediaPlayer.currentIdex);

        titleTv.setText(currentSong.getTitle());

        totalTimeTv.setText(convertToMMSS(currentSong.getDuration()));



        pausePlay.setOnClickListener(v -> pausePlay());
        nextBtn.setOnClickListener(v -> playNextSong());
        previousBtn.setOnClickListener(v -> playPreviousSong());


        playMusic();


    }

    private void playMusic()
    {
        mediaPlayer.reset();

        try {



            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void playNextSong()
    {

        if (MyMediaPlayer.currentIdex==songsList.size()-1)
            return;


        MyMediaPlayer.currentIdex +=1;
        mediaPlayer.reset();
        setResourceWithMusic();
    }

    private void playPreviousSong()
    {
        if (MyMediaPlayer.currentIdex==0)
            return;


        MyMediaPlayer.currentIdex -=1;
        mediaPlayer.reset();
        setResourceWithMusic();
    }

    private void pausePlay()
    {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
        }
        else
        {
            mediaPlayer.start();
        }
    }


    public static String convertToMMSS(String duration)
    {

        Long millis = Long.parseLong(duration);
        return String.format("%03d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.HOURS.toSeconds(1));


    }
}