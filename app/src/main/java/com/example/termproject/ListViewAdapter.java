package com.example.termproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<ListViewData> list = new ArrayList<ListViewData>();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;
        final Context context = viewGroup.getContext();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list, viewGroup, false);
        }

        TextView tvlist1 = (TextView) view.findViewById(R.id.tvlist1);
        TextView tvlist2 = (TextView) view.findViewById(R.id.tvlist2);
        TextView tvlist3 = (TextView) view.findViewById(R.id.tvlist3);
        Button btn1_list = (Button) view.findViewById(R.id.btn1_list);
        Button btn2_list = (Button) view.findViewById(R.id.btn2_list);
        ListViewData listdata = list.get(pos);
        tvlist1.setText("카테고리 : " + listdata.getCategory());
        tvlist2.setText("내용 : " + listdata.getContent());
        tvlist3.setText("주소 : " + listdata.getAddress());
        btn1_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View memolistdialog = View.inflate(context, R.layout.memolistdialog, null);
                TextView tv1_ml = memolistdialog.findViewById(R.id.tv1_ml);
                TextView tv2_ml = memolistdialog.findViewById(R.id.tv2_ml);
                TextView tv3_ml = memolistdialog.findViewById(R.id.tv3_ml);
                TextView et1_ml = memolistdialog.findViewById(R.id.et1_ml);
                ImageView iv1_ml = memolistdialog.findViewById(R.id.iv1_ml);
                ImageView iv2_ml = memolistdialog.findViewById(R.id.iv2_ml);
                RadioButton rb1_ml = memolistdialog.findViewById(R.id.rb1_ml);
                RadioButton rb2_ml = memolistdialog.findViewById(R.id.rb2_ml);
                RadioButton rb3_ml = memolistdialog.findViewById(R.id.rb3_ml);
                tv1_ml.setText(listdata.getCategory());
                et1_ml.setText(listdata.getContent());
                tv2_ml.setText(listdata.getDate());
                tv3_ml.setText(listdata.getAddress());
                switch (listdata.getEmotion()){ // 감정 정보 받기
                    case "매우 좋음":
                        rb1_ml.setChecked(true); rb2_ml.setChecked(false); rb3_ml.setChecked(false);
                        rb1_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                        rb2_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        rb3_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        break;
                    case "중간":
                        rb1_ml.setChecked(false); rb2_ml.setChecked(true); rb3_ml.setChecked(false);
                        rb1_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        rb2_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                        rb3_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        break;
                    case "매우 나쁨":
                        rb1_ml.setChecked(false); rb2_ml.setChecked(false); rb3_ml.setChecked(true);
                        rb1_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        rb2_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        rb3_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                        break;
                }
                // 카메라 사진 받기
                Bitmap cameraBitmap = null;
                byte[] cameraByte = listdata.getCamera();
                if(cameraByte!=null) {
                    cameraBitmap = BitmapFactory.decodeByteArray(cameraByte, 0, cameraByte.length);
                    iv1_ml.setImageBitmap(cameraBitmap);
                }else{
                    iv1_ml.setImageBitmap(null);
                }
                // 앨범 사진 받기
                Bitmap albumBitmap = null;
                byte[] albumByte = listdata.getAlbum();
                if(albumByte!=null) {
                    albumBitmap = BitmapFactory.decodeByteArray(albumByte, 0, albumByte.length);
                    iv2_ml.setImageBitmap(albumBitmap);
                }else{
                    iv2_ml.setImageBitmap(null);
                }

                new AlertDialog.Builder(context)
                        .setView(memolistdialog)
                        .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addItemToList(int id, String date, String category, String content,
                              String address, byte[] camera, byte[] album, String emotion){
        ListViewData listdata = new ListViewData();
        listdata.setId(id);
        listdata.setDate(date);
        listdata.setCategory(category);
        listdata.setContent(content);
        listdata.setAddress(address);
        listdata.setCamera(camera);
        listdata.setAlbum(album);
        listdata.setEmotion(emotion);

        list.add(listdata);
    }
}
