package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class Stats extends AppCompatActivity {

    MemoDBHelper memoHelper;
    Cursor cursor;
    int count;
    String result0, result1, result2, result3 = "";
//    String[] result = new String[4];

    NumberPicker yearPicker, monthPicker;
    private static final int MAX_YEAR = 2024;
    private static final int MIN_YEAR = 2020;

    Button btn1_st;
    TextView tv1_st, tv3_st, tv4_st, tv5_st, tv6_st, tv7_st, tv8_st, tv9_st, tv10_st, tv17_st, tv19_st;
    View datedialog;

    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        memoHelper = new MemoDBHelper(this);
        btn1_st = findViewById(R.id.btn1_st);
        tv1_st = findViewById(R.id.tv1_st);
        tv3_st = findViewById(R.id.tv3_st);
        tv4_st = findViewById(R.id.tv4_st);
        tv5_st = findViewById(R.id.tv5_st);
        tv6_st = findViewById(R.id.tv6_st);
        tv7_st = findViewById(R.id.tv7_st);
        tv8_st = findViewById(R.id.tv8_st);
        tv9_st = findViewById(R.id.tv9_st);
        tv10_st = findViewById(R.id.tv10_st);
        tv17_st = findViewById(R.id.tv17_st);
        tv19_st = findViewById(R.id.tv19_st);



        btn1_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datedialog = View.inflate(Stats.this, R.layout.datedialog, null);
                yearPicker = datedialog.findViewById(R.id.yearPicker_date);
                monthPicker = datedialog.findViewById(R.id.monthPicker_date);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                yearPicker.setMinValue(MIN_YEAR);
                yearPicker.setMaxValue(MAX_YEAR);

                SQLiteDatabase db;
                db = memoHelper.getReadableDatabase();
                cursor = db.rawQuery("SELECT count(id) FROM memo WHERE date='" + tv1_st.getText().toString()
                        + "'and category='"+tv3_st.getText().toString()+"'", null);
//                cursor = db.rawQuery("SELECT count(id) FROM memo WHERE date=?", new String[]{tv1_st.getText().toString()});
                if (cursor.moveToNext()) {
//                    result += cursor.getString(0);
                    result0 += cursor.getString(0);
                }

                new AlertDialog.Builder(Stats.this)
                        .setTitle("날짜 선택")
                        .setIcon(R.drawable.calendar_month)
                        .setView(datedialog)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                date = String.valueOf(yearPicker.getValue()) +". "
                                        + String.valueOf(monthPicker.getValue());
                                tv1_st.setText(date);
//                                tv4_st.setText(result);
                                tv4_st.setText(result0);
                                memoHelper.close();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

//        SQLiteDatabase db;
//        db = memoHelper.getReadableDatabase();
////        cursor = db.rawQuery("SELECT count(id) FROM memo WHERE category=?", new String[]{tv3_st.getText().toString()});
////        cursor = db.rawQuery("SELECT count(id) FROM memo WHERE date='" + tv1_st.getText().toString()+ "'and category='"+tv3_st.getText().toString()+"'", null);
//        cursor = db.rawQuery("SELECT count(id) FROM memo WHERE date=?", new String[]{tv1_st.getText().toString()});
//        if (cursor.moveToNext()) {
//            result += cursor.getString(0);
//        }
//        tv4_st.setText(result);
//        memoHelper.close();
    }
}