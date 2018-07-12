package com.example.asuper.alphabetlearner.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.asuper.alphabetlearner.R;
import com.example.asuper.alphabetlearner.Services.DatabaseManager;

public class MainActivity extends AppCompatActivity {

    public static DatabaseManager dbMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbMgr = new DatabaseManager(this);
    }

    public void startGame(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void checkProgress(View v) {
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }

}
