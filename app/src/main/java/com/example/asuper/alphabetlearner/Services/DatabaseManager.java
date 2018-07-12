package com.example.asuper.alphabetlearner.Services;

/**
 * Created by Super on 6/23/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.asuper.alphabetlearner.Objects.Letter;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "Alphabet";
    public static final String ID_FIELD = "_id";
    public static final String LETTER_FIELD = "letter";
    public static final String CORRECT_FIELD = "correct";
    public static final String INCORRECT_FIELD = "incorrect";
    public static final String RATIO_FIELD = "ratio";
    public DatabaseManager(Context context) {
        super(context,
                /*db name=*/ "alpha_db",
                /*cursorFactory=*/ null,
                /*db version=*/1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("db", "onCreate");
        String sql = "CREATE TABLE " + TABLE_NAME
                + " (" + ID_FIELD + " INTEGER, "
                + LETTER_FIELD + " TEXT,"
                + CORRECT_FIELD + " INTEGER,"
                + INCORRECT_FIELD + " INTEGER,"
                + RATIO_FIELD + " TEXT,"
                + " PRIMARY KEY (" + ID_FIELD + "));";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        Log.d("db", "onUpdate");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // re-create the table
        onCreate(db);
    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public Letter addLetter(Letter letter) {
        if (letter.getId() != null) {
            updateLetter(letter);
        } else {
            Log.d("db", "addLetter");
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(LETTER_FIELD, letter.getLetter());
            values.put(CORRECT_FIELD, letter.getCorrect());
            values.put(INCORRECT_FIELD, letter.getIncorrect());
            values.put(RATIO_FIELD, letter.getRatioString());
            long id = db.insert(TABLE_NAME, null, values);
            letter.setId(id);
            db.close();
        }
        return letter;
    }

    Letter getLetter(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] {
                        ID_FIELD, LETTER_FIELD, CORRECT_FIELD,
                        INCORRECT_FIELD }, ID_FIELD + "=?",
                new String[] { String.valueOf(id) }, null,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Letter letter = new Letter(
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3));
            letter.setId(cursor.getLong(0));
            return letter;
        }
        return null;
    }

    public List<Letter> getAllLetters() {
        List<Letter> letters = new ArrayList<Letter>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Letter letter = new Letter();
            letter.setId(Integer.parseInt(cursor.getString(0)));
            letter.setLetter(cursor.getString(1));
            letter.setCorrect(cursor.getInt(2));
            letter.setIncorrect(cursor.getInt(3));
            letters.add(letter);
        }
        return letters;
    }

    public Cursor getLettersCursor() {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public int updateLetter(Letter letter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LETTER_FIELD, letter.getLetter());
        values.put(CORRECT_FIELD, letter.getCorrect());
        values.put(INCORRECT_FIELD, letter.getIncorrect());
        values.put(RATIO_FIELD, letter.getRatioString());

        return db.update(TABLE_NAME, values, ID_FIELD + " = ?",
                new String[] { String.valueOf(letter.getId()) });
    }

    public void deleteLetter(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_FIELD + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

}