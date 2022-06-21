package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StartPage extends AppCompatActivity {

    JoinFrament joinFrament;
    LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        menuFragment = new MenuFragment();
    }
}