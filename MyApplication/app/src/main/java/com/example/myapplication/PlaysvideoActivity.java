package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class PlaysvideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playsvideo);

        videoView = findViewById(R.id.video_view);
        textView = findViewById(R.id.text_title);

        String uri = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("name");
        videoView.setVideoURI(Uri.parse(uri));
        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);

        textView.setText(title);
    }
}