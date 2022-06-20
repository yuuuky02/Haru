package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
<<<<<<< Updated upstream

        db.execSQL("CREATE TABLE marker (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, latitude INTEGER, longitude INTEGER)");
=======
>>>>>>> Stashed changes
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS memo");
<<<<<<< Updated upstream
        db.execSQL("DROP TABLE IF EXISTS marker");
=======
>>>>>>> Stashed changes
        onCreate(db);

    }
}
