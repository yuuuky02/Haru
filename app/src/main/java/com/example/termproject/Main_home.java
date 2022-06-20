package com.example.termproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.AsyncTaskLoader;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VerifiedMotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Main_home extends AppCompatActivity {

    MemoDBHelper memoHelper;
    String selectDate;
    ListView listView;
    Button btn1_list, btn2_list;

    TextView tv1;
    CalendarView cv;
    RadioGroup rdg;
    RadioButton rbtn1, rbtn2, rbtn3, rbtn4;
    View dialogview;
    String fileName;
    int selectYear, selectMonth, selectDay;
    String Mmonth;
    String category = null;

    Menu menu;
    MenuItem stats, cafedata, mapdata;

    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        ActivityCompat.requestPermissions(this, new String[]
                {android.Manifest.permission.CAMERA,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION}, MODE_PRIVATE);
        memoHelper = new MemoDBHelper(this);

        tv1 = findViewById(R.id.tv_calendar);
        cv = findViewById(R.id.calendarView);
        rdg = findViewById(R.id.radiogroup);
        stats = findViewById(R.id.stats);
        cafedata = findViewById(R.id.cafedata);
        mapdata = findViewById(R.id.mapdata);
        listView = (ListView) findViewById(R.id.listView1_main);
        btn1_list = findViewById(R.id.btn1_list);
        btn2_list = findViewById(R.id.btn2_list);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                selectYear = year;
                selectMonth = month + 1;
                selectDay = dayOfMonth;
                if (selectMonth > 0 && selectMonth < 10){
                    Mmonth = "0" + selectMonth;
                }else{
                    Mmonth = String.valueOf(selectMonth);
                }
                selectDate = selectYear+"년"+Mmonth+"월"+selectDay+"일";

                // 해당 날짜에 기록된게 없으면 '카테고리' 뜨기,
                SQLiteDatabase db = memoHelper.getReadableDatabase();
                Cursor cursor1 = db.rawQuery("SELECT count(id) FROM memo WHERE date=?", new String[]{selectDate});
                String result = null;
                while(cursor1.moveToNext()){
                    result = cursor1.getString(0);
                }
                if (Integer.valueOf(result) == 0) {
                    new android.app.AlertDialog.Builder(Main_home.this)
                            .setTitle("기록된 내용이 없습니다.")
                            .setPositiveButton("작성하기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    onCategory();
                                }
                            })
                            .setNeutralButton("닫기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    displayList();
                                }
                            })
                            .show();
                }else{ // 있으면 '리스트뷰' 뜨기
                    new android.app.AlertDialog.Builder(Main_home.this)
                            .setPositiveButton("작성하기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    onCategory();
                                }
                            })
                            .setNeutralButton("기록보기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    displayList();
                                }
                            })
                            .show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.stats:
                intent = new Intent(getApplicationContext(), Stats.class);
                startActivity(intent);
                return true;
            case R.id.cafedata:
                intent = new Intent(getApplicationContext(), Cmaps.class);
                startActivity(intent);
                return true;
            case R.id.mapdata:
                intent = new Intent(getApplicationContext(), Map.class);
                startActivity(intent);
                return true;

        }
        return false;
    }
    // '리스트뷰'
    @RequiresApi(api = Build.VERSION_CODES.O)
    void displayList() {
        MemoDBHelper memoHelper = new MemoDBHelper(this);
        SQLiteDatabase db = memoHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM memo WHERE date=?", new String[]{selectDate});
        ListViewAdapter adapter = new ListViewAdapter();
        while(cursor.moveToNext()) {
            adapter.addItemToList(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getBlob(5),
                    cursor.getBlob(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9));
        }
        listView.setAdapter(adapter);
    }

    // '카테고리' 대화창
    @RequiresApi(api = Build.VERSION_CODES.O)
    void onCategory() {
        dialogview=(View)View.inflate(Main_home.this, R.layout.dialog, null);
        rdg = dialogview.findViewById(R.id.radiogroup);
        rbtn1 = dialogview.findViewById(R.id.rbtn_daily);
        rbtn2 = dialogview.findViewById(R.id.rbtn_exercise);
        rbtn3 = dialogview.findViewById(R.id.rbtn_goal);
        rbtn4 = dialogview.findViewById(R.id.rbtn_travel);
        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_daily:
                        intent = new Intent(getApplicationContext(), MemoDaily.class);
                        intent.putExtra("select1", Integer.toString(selectYear));
                        intent.putExtra("select2", Mmonth);
                        intent.putExtra("select3", Integer.toString(selectDay));
                        category = rbtn1.getText().toString();
                        break;

                    case R.id.rbtn_exercise:
                        intent = new Intent(getApplicationContext(), MemoExercise.class);
                        intent.putExtra("select4", Integer.toString(selectYear));
                        intent.putExtra("select5", Mmonth);
                        intent.putExtra("select6", Integer.toString(selectDay));
                        category = rbtn2.getText().toString();
                        break;

                    case R.id.rbtn_goal:
                        intent = new Intent(getApplicationContext(), MemoStudy.class);
                        intent.putExtra("select7", Integer.toString(selectYear));
                        intent.putExtra("select8", Mmonth);
                        intent.putExtra("select9", Integer.toString(selectDay));
                        category = rbtn3.getText().toString();
                        break;

                    case R.id.rbtn_travel:
                        intent = new Intent(getApplicationContext(), MemoTrip.class);
                        intent.putExtra("select10", Integer.toString(selectYear));
                        intent.putExtra("select11", Mmonth);
                        intent.putExtra("select12", Integer.toString(selectDay));
                        category = rbtn4.getText().toString();
                        break;
                }
                return;
            }
        });
        new android.app.AlertDialog.Builder(Main_home.this)
                .setTitle("Select Category")
                .setView(dialogview)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = memoHelper.getReadableDatabase();
                        String result2 = null;
                        Cursor cursor2;
                        cursor2 = db.rawQuery("SELECT count(id) FROM memo WHERE date=? AND category=?", new String[]{selectDate,category});
                        while(cursor2.moveToNext()){
                            result2 = cursor2.getString(0);
                        }
                        if (Integer.valueOf(result2) > 0) {
                            Toast.makeText(getApplicationContext(), "기록한 내용이 있습니다.", Toast.LENGTH_SHORT).show();
                            displayList();
                        }else {
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }
}