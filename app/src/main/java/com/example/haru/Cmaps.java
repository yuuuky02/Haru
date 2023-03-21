package com.example.haru;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Cmaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmaps);

        back = findViewById(R.id.back);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final LatLng Espresso = new LatLng(37.351384, 126.741974);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Espresso);
        markerOptions.title("에스프레소플래닛");
        googleMap.addMarker(markerOptions);

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Espresso));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(40));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            }
        });

        final LatLng Thepeach = new LatLng(37.346794, 126.739406);

        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(Thepeach);
        markerOptions1.title("카페더피치");
        googleMap.addMarker(markerOptions1);

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Thepeach));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(40));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            }
        });

        final LatLng Cake = new LatLng(37.344937, 126.735674);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(Cake);
        markerOptions2.title("153케이크");
        googleMap.addMarker(markerOptions2);

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Cake));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(40));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        });

        final LatLng Bakery = new LatLng(37.348143, 126.733310);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(Bakery);
        markerOptions3.title("쁘숑 베이커리");
        googleMap.addMarker(markerOptions3);

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Bakery));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(40));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        });

        final LatLng Boqui = new LatLng(37.350605, 126.735803);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(Boqui);
        markerOptions4.title("boqui");
        googleMap.addMarker(markerOptions4);

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Boqui));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(40));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        });

        final LatLng Cafe_302 = new LatLng(37.344177, 126.737469);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(Cafe_302);
        markerOptions5.title("302 카페");
        googleMap.addMarker(markerOptions5);

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Cafe_302));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(40));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        });

        final LatLng Cafe_10 = new LatLng(37.337647, 126.747613);

        MarkerOptions markerOptions6 = new MarkerOptions();
        markerOptions6.position(Cafe_10);
        markerOptions6.title("10온스 커피");
        googleMap.addMarker(markerOptions6);

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Cafe_10));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(40));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        });

        final LatLng Cafestar = new LatLng(37.343688, 126.736941);

        MarkerOptions markerOptions7 = new MarkerOptions();
        markerOptions7.position(Cafestar);
        markerOptions7.title("카페별");
        googleMap.addMarker(markerOptions7);

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Cafestar));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(40));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTitle().equals("에스프레소플래닛")){
                    String url = "http://naver.me/5fnPgAPy";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                if (marker.getTitle().equals("카페더피치")){
                    String url = "http://naver.me/FIgYxGmg";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                if (marker.getTitle().equals("153케이크")){
                    String url = "http://naver.me/G3JXd7Ma";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                if (marker.getTitle().equals("쁘숑 베이커리")){
                    String url = "http://naver.me/GMRWAvbU";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                if (marker.getTitle().equals("boqui")){
                    String url = "http://naver.me/F9QpznjR";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                if (marker.getTitle().equals("302 카페")){
                    String url = "http://naver.me/GsTOk0JJ";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                if (marker.getTitle().equals("10온스 커피")){
                    String url = "http://naver.me/I5Fmpovw";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                if (marker.getTitle().equals("카페별")){
                    String url = "http://naver.me/5bXQNN5p";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return false;
            }
        });



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            googleMap.setMyLocationEnabled(true);
        }
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
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    }
