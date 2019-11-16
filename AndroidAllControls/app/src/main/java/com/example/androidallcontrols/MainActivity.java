package com.example.androidallcontrols;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.provider.Settings;
import android.content.Intent;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    AudioManager audioManager;
    // for playing the music
    public void play(View view){
        player.start();
    }

    // for pausing the music
    public void pause(View view){
        player.pause();
    }

    // for stopping the music
    public void stop(View view){
        player.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int streamCurVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int ringMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        int ringCurVol = audioManager.getStreamVolume(AudioManager.STREAM_RING);

        SeekBar seekBright = findViewById(R.id.seekBright);
        //Getting Current screen brightness.



        seekBright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Context context = getApplicationContext();


                boolean canWriteSettings = Settings.System.canWrite(context);

                if(canWriteSettings) {

                    // Because max screen brightness value is 255
                    // But max seekbar value is 100, so need to convert.
                    int screenBrightnessValue = progress*255/100;

                    // Set seekbar adjust screen brightness value in the text view.


                    // Change the screen brightness change mode to manual.
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
                    // It will also change the screen brightness for the device.
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue);
                }else
                {
                    // Show Can modify system settings panel to let user add WRITE_SETTINGS permission for this app.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_SETTINGS}, 0);

                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }

            }



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });
        int currBrightness = Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0);

        // Set current screen brightness value to seekbar progress.
        seekBright.setProgress(currBrightness);
        SeekBar seekVol = findViewById(R.id.seekVol);
        seekVol.setMax(streamMaxVolume);
        seekVol.setProgress(streamCurVol);

        seekVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_PLAY_SOUND);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });

        SeekBar seekVol2 = findViewById(R.id.seekVol2);
        seekVol2.setMax(ringMaxVolume);
        seekVol2.setProgress(ringCurVol);

        seekVol2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(AudioManager.STREAM_RING, progress, AudioManager.FLAG_PLAY_SOUND);
                audioManager.adjustStreamVolume(AudioManager.STREAM_RING,
                        AudioManager.ADJUST_SAME, AudioManager.FLAG_PLAY_SOUND);
              //  audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });









    }
}

