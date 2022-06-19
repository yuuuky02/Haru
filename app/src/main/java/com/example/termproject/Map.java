package com.example.termproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 300;

    private GoogleMap googleMap;

    View dialogview;
    EditText edt_md;

    Button mapbtn1, mapbtn2;

    SQLiteDatabase sqlDB;
    MarkerDBHelper markerHelper;
    String d_name;
    int d_latitude;
    int d_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapbtn1 = findViewById(R.id.mapbtn1);
        mapbtn2 = findViewById(R.id.mapbtn2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 뒤로가기
        mapbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // 나의 위치
        mapbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsTracker = new GpsTracker(Map.this);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                String address = getCurrentAddress(latitude, longitude);

                Toast.makeText(Map.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.putExtra("address", address);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        dialogview=(View)View.inflate(Map.this, R.layout.map_dialog, null);
        edt_md=dialogview.findViewById(R.id.edt_md);

        LatLng latLng = new LatLng(37.351756, 126.742844);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("정왕역");
        googleMap.addMarker(markerOptions);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            googleMap.setMyLocationEnabled(true);
        }

        markerHelper= new MarkerDBHelper(this);

        //조회
        sqlDB = markerHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM marker;", null);

        if (((Cursor) cursor).moveToNext()) {
            d_name += cursor.getString(0);
            d_latitude += cursor.getInt(1);
            d_longitude += cursor.getInt(2);

            markerOptions.position(new LatLng(d_latitude, d_longitude));
            markerOptions.title(d_name);
            googleMap.addMarker(markerOptions);

        }
        markerHelper.close();


        //맵 터치 시 마커생성
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(@NonNull LatLng point) {
                Double latitude = point.latitude; //위도
                Double longitude = point.longitude;  //경도
                markerOptions.position(new LatLng(latitude, longitude));

                AlertDialog.Builder dlg2 = new AlertDialog.Builder(Map.this);
                dlg2.setTitle("마커 이름을 입력하세요.");
                dlg2.setView(dialogview);
                dlg2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContentValues row;
                        sqlDB = markerHelper.getWritableDatabase();
                        row = new ContentValues();
                        row.put("name", edt_md.getText().toString());
                        row.put("latitude", latitude);
                        row.put("longitude", longitude);
                        sqlDB.insert("marker", null, row);
                        markerHelper.close();
                        Toast.makeText(getApplicationContext(), "마커가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        googleMap.addMarker(markerOptions);
                    }
                });
                dlg2.setNegativeButton("취소", null);
                dlg2.show();

            }
        });

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        switch (permsRequestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public String getCurrentAddress( double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            return "사용불가";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GPS_ENABLE_REQUEST_CODE:
                    if (checkLocationServicesStatus()) {
                        if (checkLocationServicesStatus()) {
                            Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                            return;
                        }
                    }
                    break;
            }
        }else {
            return;
        }
    }
}