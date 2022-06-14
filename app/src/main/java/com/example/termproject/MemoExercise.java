package com.example.termproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

public class MemoExercise extends AppCompatActivity {
    private static final int REQ_CODE_SELECT_CAMERA = 100;
    private static final int REQ_CODE_SELECT_IMAGE = 200;
    private static final int GPS_ENABLE_REQUEST_CODE = 300;

    Bitmap bitmap, imageBitmap;
    Button btn1_e, btn2_e, btn3_e, btn4_e, btn5_e, album_btn1, album_btn2, album_btn3, album_btn4;
    RadioGroup rg_e;
    RadioButton rb1_e, rb2_e, rb3_e;
    EditText et1_e;
    TextView tv2_e, tv3_e;
    ImageView iv1_e, iv2_e, album_iv1;

    View albumdialog;
    Canvas canvas;
    Paint paint;
    Path path;

    String mode = "none";
    float upx = 0, upy = 0;
    static float width = 15F;
    static int color = Color.RED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_exercise);
        ActivityCompat.requestPermissions(this, new String[]
                {android.Manifest.permission.CAMERA,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION}, MODE_PRIVATE);

        btn1_e = findViewById(R.id.btn1_e);
        btn2_e = findViewById(R.id.btn2_e);
        btn3_e = findViewById(R.id.btn3_e);
        btn4_e = findViewById(R.id.btn4_e);
        btn5_e = findViewById(R.id.btn5_e);
        album_btn1 = findViewById(R.id.album_btn1);
        album_btn2 = findViewById(R.id.album_btn2);
        album_btn3 = findViewById(R.id.album_btn3);
        album_btn4 = findViewById(R.id.album_btn4);
        rg_e = findViewById(R.id.rg_e);
        rb1_e = findViewById(R.id.rb1_e);
        rb2_e = findViewById(R.id.rb2_e);
        rb3_e = findViewById(R.id.rb3_e);
        et1_e = findViewById(R.id.et1_e);
        tv2_e = findViewById(R.id.tv2_e);
        tv3_e = findViewById(R.id.tv3_e);
        iv1_e = findViewById(R.id.iv1_e);
        iv2_e = findViewById(R.id.iv2_e);
        album_iv1 = findViewById(R.id.album_iv1);

        // close
        btn1_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_home.class);
                startActivity(intent);
            }
        });

        // 저장하기
        btn2_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // 앨범 아이콘 선택 시 대화창
        btn4_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                albumdialog = View.inflate(MemoExercise.this, R.layout.albumdialog, null);
                album_iv1 = albumdialog.findViewById(R.id.album_iv1);
                album_btn1 = albumdialog.findViewById(R.id.album_btn1);
                new AlertDialog.Builder(MemoExercise.this)
                        .setTitle("사진 선택")
                        .setIcon(R.drawable.photo)
                        .setView(albumdialog)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                iv1_e.setImageBitmap(imageBitmap); // 카메라 사진
                                iv2_e.setImageBitmap(bitmap); // 앨범 사진
                                Toast.makeText(getApplicationContext(), "사진을 선택했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "사진 선택을 취소했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        // 지도 보기
        btn5_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.termproject.Map.class);
                startActivityForResult(intent, GPS_ENABLE_REQUEST_CODE);
            }
        });

        // "감정" 버튼 클릭 시 색상 변경
        rg_e.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1_e: // 좋음
                        rb1_e.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                        rb2_e.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        rb3_e.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        break;
                    case R.id.rb2_e: // 중간
                        rb1_e.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        rb2_e.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                        rb3_e.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        break;
                    case R.id.rb3_e: // 나쁨
                        rb1_e.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        rb2_e.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        rb3_e.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                        break;
                }
                return;
            }
        });
    }

    // 카메라 열기
    public void onCamera(View v){
        switch (v.getId()){
            case R.id.btn3_e:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQ_CODE_SELECT_CAMERA);
                break;
        }
        return;
    }

    // 앨범 열기
    public void onOpenAlbum(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    // 앨범에서 선택 후 호출, 그림
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode) {
                case REQ_CODE_SELECT_CAMERA: // 카메라 선택
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    iv1_e.setImageBitmap(imageBitmap);
                    break;
                case REQ_CODE_SELECT_IMAGE:  // 앨범 선택
                    try{
                        Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap1, album_iv1.getWidth(), album_iv1.getHeight(), false);

                        bitmap = bitmap2.copy(Bitmap.Config.ARGB_8888,true);

                        canvas = new Canvas(bitmap);
                        album_iv1.setImageBitmap(bitmap);

                        album_iv1.setOnTouchListener(new View.OnTouchListener() { // albumdialog의 iv4 에 그림그리기
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                float x = (float) motionEvent.getX();
                                float y = (float) motionEvent.getY();
                                int action = motionEvent.getAction();
                                switch (action){
                                    case MotionEvent.ACTION_DOWN:
                                        path.reset();
                                        path.moveTo(x, y);
                                        break;
                                    case MotionEvent.ACTION_MOVE:
                                        upx = motionEvent.getX();
                                        upy = motionEvent.getY();

                                        path.lineTo(x, y);
                                        canvas.drawPath(path, paint);
                                        album_iv1.invalidate();
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        break;
                                    default:
                                        return false;
                                }
                                album_iv1.invalidate();
                                return true;
                            }
                        });
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    break;
                case GPS_ENABLE_REQUEST_CODE:
                    if (resultCode == RESULT_OK) {
                        tv3_e.setText(data.getStringExtra("address"));
                    }
                    break;
            }
        }else {
            return;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        initJob();
    }

    // 그림 그리기 기본 상태
    public void initJob(){
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15F);
        paint.setAntiAlias(true);
        path = new Path();
    }

    // Paint 변경
    public void onChangePaint(View v) {
        switch (v.getId()) {
            case R.id.album_btn2: // 파랑
                color = Color.BLUE;
                break;
            case R.id.album_btn3: // 가늘게
                width -= 5F;
                break;
            case R.id.album_btn4: // 원래대로
                color = Color.RED;
                width = 15F;
                break;
        }
        album_iv1.invalidate();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);
        return;
    }
}