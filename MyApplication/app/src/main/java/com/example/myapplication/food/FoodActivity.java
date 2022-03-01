package com.example.myapplication.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.myapplication.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class FoodActivity extends AppCompatActivity {
    EditText food_edit;
    RecyclerView recyclerView;
    List<Foods.ResultDTO.ListDTO> foodsList;
    ImageView food_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        food_search = findViewById(R.id.knowledge_search);
        food_edit = findViewById(R.id.knowledge_edit);
        food_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = food_edit.getText().toString();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient = new OkHttpClient();//1.定义一个client
                        Request request = new Request.Builder().url("https://api.binstd.com/recipe/search?appkey=ecde22f9cfd7aeed&num=20&keyword="+text).build();//2.定义一个request
                        Call call = okHttpClient.newCall(request);
                        try {
                            String result = call.execute().body().string();
                            Gson gson = new Gson();
                            Foods foods = gson.fromJson(result,Foods.class);
                            foodsList = foods.result.list;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                recyclerView = findViewById(R.id.food_recycler);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(FoodActivity.this, 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(new MyAdapter(FoodActivity.this, foodsList));
            }
        });

    }
}
