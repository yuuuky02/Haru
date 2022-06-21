package com.example.termproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class JoinFrament extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_join_frament, container, false); // rootView는 프레그먼트의 레이아웃

        EditText etjoin1 = (EditText) rootView.findViewById(R.id.etjoin1);
        EditText etjoin2 = (EditText) rootView.findViewById(R.id.etjoin2);
        EditText etjoin3 = (EditText) rootView.findViewById(R.id.etjoin3);
        Button btnjoin1 = (Button) rootView.findViewById(R.id.btnjoin1);
        btnjoin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(0);
            }
        });

        return rootView;
    }
}