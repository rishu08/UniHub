package com.example.rishabh.unihub;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Alarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        final MediaPlayer mp = MediaPlayer.create(this, R.raw.reminder);
        mp.start();

    }
}
