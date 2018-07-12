package com.example.asuper.alphabetlearner.Activities;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import com.example.asuper.alphabetlearner.R;


public class StarActivity extends AppCompatActivity {

    MediaPlayer greatJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        greatJob = MediaPlayer.create(StarActivity.this, R.raw.great_job);
        greatJob.start();
    }

    public void continueButton(View v) {
        if (greatJob != null && greatJob.isPlaying()) {
            // do nothing
        } else {
            greatJob.release();
            finish();
        }
    }


}
