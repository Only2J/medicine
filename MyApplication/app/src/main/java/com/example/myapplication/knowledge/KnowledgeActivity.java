package com.example.myapplication.knowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.myapplication.R;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class KnowledgeActivity extends AppCompatActivity {
    EditText knowledge_edit;
    ImageView knowledge_search;
    TextView knowledge_content;
    TextView knowledge_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);
        knowledge_content = findViewById(R.id.knowledge_content);
        knowledge_edit = findViewById(R.id.knowledge_edit);
        knowledge_title = findViewById(R.id.knowledge_title);
        knowledge_search = findViewById(R.id.knowledge_search);
        knowledge_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = knowledge_edit.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient = new OkHttpClient();//1.定义一个client
                        Request request = new Request.Builder().url("http://api.tianapi.com/zhongyao/index?key=0c06ce64fc807b15e0005661e90932f6&word=" + text).build();//2.定义一个request
                        Call call = okHttpClient.newCall(request);
                        try {
                            String result = call.execute().body().string();
                            Gson gson = new Gson();
                            Knowledge knowledge = gson.fromJson(result, Knowledge.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    knowledge_title.setText(knowledge.newslist.get(0).title);
                                    knowledge_content.setText(knowledge.newslist.get(0).content);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}