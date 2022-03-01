package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplication.home.HomeFragment;

public class MainActivity2 extends AppCompatActivity {

    LinearLayout home_layout, wenda_layout, my_layout;
    HomeFragment blankFragment = new HomeFragment();
    BlankFragment2 blankFragment2 = new BlankFragment2();
    BlankFragment3 blankFragment3 = new BlankFragment3();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        home_layout = findViewById(R.id.home_layout);
        wenda_layout = findViewById(R.id.wenda_layout);
        my_layout = findViewById(R.id.my_layout);

        home_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_layout.setSelected(true);
                wenda_layout.setSelected(false);
                my_layout.setSelected(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, blankFragment).commit();
            }
        });

        wenda_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_layout.setSelected(false);
                wenda_layout.setSelected(true);
                my_layout.setSelected(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, blankFragment2).commit();
            }
        });

        my_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_layout.setSelected(false);
                wenda_layout.setSelected(false);
                my_layout.setSelected(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, blankFragment3).commit();
            }
        });

        home_layout.setSelected(true);
        wenda_layout.setSelected(false);
        my_layout.setSelected(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, blankFragment).commit();
    }
}