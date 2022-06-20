package com.example.termproject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MemoDBHelper extends SQLiteOpenHelper {

    public MemoDBHelper(Context context) {
        super(context, "memo.db", null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE memo (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, category TEXT, content TEXT," +
                "address TEXT, camera TEXT, album TEXT, emotion TEXT, etime INTEGER, edistance INTEGER);");



        db.execSQL("CREATE TABLE marker (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, latitude INTEGER, longitude INTEGER)");

        db.execSQL("CREATE TABLE marker (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, latitude TEXT, longitude TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS memo");

        db.execSQL("DROP TABLE IF EXISTS marker");

        onCreate(db);
    }

}
