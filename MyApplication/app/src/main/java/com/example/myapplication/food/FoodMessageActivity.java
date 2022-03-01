package com.example.myapplication.food;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class FoodMessageActivity extends AppCompatActivity {

    TextView people_num,title,prepare_time,coooking_time,content;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_message);


        Foods.ResultDTO.ListDTO food = (Foods.ResultDTO.ListDTO) getIntent().getParcelableExtra("food");
//        Log.e("---",food.name);
        title = findViewById(R.id.food_message_title);
        title.setText(food.name);

        people_num =findViewById(R.id.food_meesage_people_num);
        people_num.setText(food.peoplenum);

        prepare_time = findViewById(R.id.food_message_prepare_time);
        prepare_time.setText(food.preparetime);

        coooking_time = findViewById(R.id.food_message_cooking_time);
        coooking_time.setText(food.cookingtime);

        content = findViewById(R.id.food_message_content);
        content.setText(food.content);

        image =findViewById(R.id.food_message_image);
        Glide.with(FoodMessageActivity.this).load(food.pic).into(image);


    }
}