package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MarkerDBHelper extends SQLiteOpenHelper {
    public MarkerDBHelper(Context context) {
        super(context, "marker.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE marker (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, latitude INTEGER, longitude INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS marker");
        onCreate(db);
    }
}
