package com.example.shubhamtripathi.addingaudio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

  MediaPlayer mplayer;

    int[] array = {1};

    public void playAudio(View view){



        Button play =(Button) findViewById(R.id.playButton);



        if(array[0]==1) {

            mplayer.start();

            play.setText("Pause");

            array[0]=2;
        }else
        {
            mplayer.pause();
            array[0]=1;
            play.setText("Play");

        }

    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mplayer = MediaPlayer.create(this, R.raw.demo);

        final SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);


        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxvolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        int currentvolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeControl.setMax(maxvolume);
        volumeControl.setProgress(currentvolume);







        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seekbar Value:-",Integer.toString(progress));

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }
        });


        final SeekBar scrubber = (SeekBar) findViewById(R.id.scrubber);

        scrubber.setMax(mplayer.getDuration());
       new Timer().scheduleAtFixedRate(new TimerTask() {
           @Override
           public void run() {
              scrubber.setProgress(mplayer.getCurrentPosition());
           }
       },0,1500);


        scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              mplayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
             mplayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            mplayer.start();
            }
        });


    }
}
