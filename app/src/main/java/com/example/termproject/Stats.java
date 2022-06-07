package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class Stats extends AppCompatActivity {

    NumberPicker yearPicker, monthPicker;
    private static final int MAX_YEAR = 2030;
    private static final int MIN_YEAR = 2010;

    Button btn1_s;
    TextView tv1_s;
    View datedialog;

    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        btn1_s = findViewById(R.id.btn1_s);
        tv1_s = findViewById(R.id.tv1_s);

        btn1_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datedialog = View.inflate(Stats.this, R.layout.datedialog, null);
                yearPicker = datedialog.findViewById(R.id.yearPicker_date);
                monthPicker = datedialog.findViewById(R.id.monthPicker_date);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                yearPicker.setMinValue(MIN_YEAR);
                yearPicker.setMaxValue(MAX_YEAR);

                new AlertDialog.Builder(Stats.this)
                        .setTitle("날짜 선택")
                        .setIcon(R.drawable.calendar_month)
                        .setView(datedialog)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                date = String.valueOf(yearPicker.getValue()) +". "
                                        + String.valueOf(monthPicker.getValue());
                                tv1_s.setText(date);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });
    }
}