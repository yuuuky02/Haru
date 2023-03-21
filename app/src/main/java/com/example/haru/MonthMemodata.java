package com.example.haru;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class MonthMemodata extends AppCompatActivity {

    MemoDBHelper memoHelper;
    Cursor cursor;

    Button btn1_md, btn2_md;
    TextView tv1_md;
    ListView listView_md;

    NumberPicker yearPicker, monthPicker;
    private static final int MAX_YEAR = 2024;
    private static final int MIN_YEAR = 2020;
    View datedialog;
    String Mmonth;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_memodata);
        btn1_md = findViewById(R.id.btn1_md);
        btn2_md = findViewById(R.id.btn2_md);
        tv1_md = findViewById(R.id.tv1_md);
        listView_md = (ListView) findViewById(R.id.listView1_md);
        memoHelper = new MemoDBHelper(this);

        // 닫기
        btn1_md.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_home.class);
                startActivity(intent);
            }
        });

        // 날짜 선택
        btn2_md.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datedialog = View.inflate(MonthMemodata.this, R.layout.datedialog, null);
                yearPicker = datedialog.findViewById(R.id.yearPicker_date);
                monthPicker = datedialog.findViewById(R.id.monthPicker_date);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                yearPicker.setMinValue(MIN_YEAR);
                yearPicker.setMaxValue(MAX_YEAR);

                SQLiteDatabase db = memoHelper.getReadableDatabase();

                new AlertDialog.Builder(MonthMemodata.this)
                        .setTitle("날짜 선택")
                        .setIcon(R.drawable.calendar_month)
                        .setView(datedialog)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (monthPicker.getValue() > 0 && monthPicker.getValue() < 10){
                                    Mmonth = "0" + monthPicker.getValue();
                                }else{
                                    Mmonth = String.valueOf(monthPicker.getValue());
                                }
                                date = String.valueOf(yearPicker.getValue())+"년"+Mmonth+"월";
                                tv1_md.setText(date);
                                displayList();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });
    }

    // '리스트뷰'
    @RequiresApi(api = Build.VERSION_CODES.O)
    void displayList() {
        MemoDBHelper memoHelper = new MemoDBHelper(this);
        SQLiteDatabase db = memoHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM memo WHERE substr(date,1,8)=?", new String[]{tv1_md.getText().toString()});
        MemoListAdapter adapter = new MemoListAdapter();
        while(cursor.moveToNext()) {
            adapter.addItemToList(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getBlob(5),
                    cursor.getBlob(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9));
        }
        listView_md.setAdapter(adapter);
    }
}