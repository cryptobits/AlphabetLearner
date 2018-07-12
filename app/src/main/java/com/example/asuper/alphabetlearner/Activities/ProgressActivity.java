package com.example.asuper.alphabetlearner.Activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.asuper.alphabetlearner.Activities.MainActivity;
import com.example.asuper.alphabetlearner.R;
import com.example.asuper.alphabetlearner.Services.DatabaseManager;

// This activity allows you to view the number of correct answers per letter
// that have been given out of the number of times it has been displayed.

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ListView listView = (ListView) findViewById(R.id.listView);

        Cursor cursor = MainActivity.dbMgr.getLettersCursor();
        startManagingCursor(cursor);

        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.two_line_list_item,
                cursor,
                new String[] {DatabaseManager.LETTER_FIELD,
                        DatabaseManager.RATIO_FIELD},
                new int[] {android.R.id.text1, android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);



    }

    // Allows the user to reset the progress
    public void resetDatabase(View v) {
        MainActivity.dbMgr.dropTable();
    }

}
