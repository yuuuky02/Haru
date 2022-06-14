package com.example.termproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main_home extends AppCompatActivity {

    TextView tv1;
    CalendarView cv;
    ListView listView;
    RadioGroup rdg;
    RadioButton rbtn1, rbtn2, rbtn3, rbtn4;
    View dialogview;
    String fileName;
    int selectYear, selectMonth, selectDay;

    Intent intent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.stats) {
            Intent homeIntent = new Intent(this, Stats.class);
            startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);
        final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        final File dir = new File(sdPath + "Pictures");
        dir.mkdir();

        tv1=findViewById(R.id.tv_calendar);
        cv=findViewById(R.id.calendarView);
        //listView=(ListView)findViewById(R.id.listview);
        rdg=findViewById(R.id.radiogroup);
        rbtn1=findViewById(R.id.rbtn_daily);
        rbtn2=findViewById(R.id.rbtn_exercise);
        rbtn3=findViewById(R.id.rbtn_goal);
        rbtn4=findViewById(R.id.rbtn_travel);


        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                selectYear =  year;
                selectMonth = month + 1;
                selectDay = dayOfMonth;

                fileName = Integer.toString(selectYear) + "년"
                        + Integer.toString(selectMonth + 1) + "월"
                        + Integer.toString(selectDay) + "일";
                String Path =sdPath+"/Pictures/"+fileName;
                File files = new File(Path);

                //파일 있으면 리스트뷰로 띄우기 각각 다른 경로에서 가져와야함 픽쳐스 폴더에 아예 다 저장해버리는 것도 되긴함 파일 내용은 달라야함
                if (files.exists()) {
                    try {
                        FileInputStream infile =new FileInputStream(Path);
                        byte[] txt = new byte[infile.available()];
                        infile.read(txt);
                        String str = new String(txt);

                        //리스트뷰


                    } catch (IOException e){
                        e.printStackTrace();;
                    }

                    //파일 없는 경우 라디오버튼으로 카테고리 선택 후 해당 카테고리로 화면이동
                }else {
                    dialogview=(View)View.inflate(Main_home.this, R.layout.dialog, null);
                    rdg = dialogview.findViewById(R.id.radiogroup);
                    rbtn1 = dialogview.findViewById(R.id.rbtn_daily);
                    rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                            switch (checkedId) {
                                case R.id.rbtn_daily:
                                    intent = new Intent(getApplicationContext(), MemoDaily.class);
                                    break;
                                case R.id.rbtn_exercise:
                                    intent = new Intent(getApplicationContext(), MemoExercise.class);
                                    break;
                                case R.id.rbtn_goal:
                                    intent = new Intent(getApplicationContext(), MemoStudy.class);
                                    break;
                                case R.id.rbtn_travel:
                                    intent = new Intent(getApplicationContext(), MemoTrip.class);
                                    break;
                            }
                            return;
                        }
                    });
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Main_home.this);
                    dlg.setTitle("Select Categoty");
                    dlg.setView(dialogview);

                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(intent);
                        }
                    });

                    dlg.setNegativeButton("취소",null);
                    dlg.show();
                }
            }
        });
    }
}