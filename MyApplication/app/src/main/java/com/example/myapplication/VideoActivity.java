package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
    private List<Video> videoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initVideo();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        VideoAdapter adapter = new VideoAdapter(this,videoList);
        recyclerView.setAdapter(adapter);

    }

    private void initVideo() {
        Video video1 = new Video("脾胃虚寒多吃姜", R.drawable.video,"https://vd3.bdstatic.com/mda-mc0mcs51bpcz71t0/v1-cae/hd/mda-mc0mcs51bpcz71t0.mp4");
        videoList.add(video1);
        Video video2 = new Video("睡眠的重要性", R.drawable.video,"https://vd3.bdstatic.com/mda-kmeg17disf643s7e/v1-hknm/1080p/mda-kmeg17disf643s7e.mp4");
        videoList.add(video2);
        Video video3 = new Video("核桃红枣，长生不老", R.drawable.video,"https://vd3.bdstatic.com/mda-mbnqyy6gzfnmk119/v1-cae/hd/mda-mbnqyy6gzfnmk119.mp4");
        videoList.add(video3);
        Video video4 = new Video("教你正确吃核桃", R.drawable.video, "https://vd3.bdstatic.com/mda-mbqmr42aw8ehgpiv/v1-cae/hd/mda-mbqmr42aw8ehgpiv.mp4");
        videoList.add(video4);
        Video video5 = new Video("长寿的5个关键要素", R.drawable.video, "https://vd4.bdstatic.com/mda-ifaf2t5ve76h2jdq/sc/mda-ifaf2t5ve76h2jdq.mp4");
        videoList.add(video5);
        Video video6 = new Video("八段锦", R.drawable.video,"https://vd3.bdstatic.com/mda-jigrjv31g495f1f9/sc/mda-jigrjv31g495f1f9.mp4");
        videoList.add(video6);
        Video video7 = new Video("颈椎操", R.drawable.video,"https://vd2.bdstatic.com/mda-jk6j8jtspdxd8fwu/sc/mda-jk6j8jtspdxd8fwu.mp4");
        videoList.add(video7);
        Video video8 = new Video("春季养生", R.drawable.video,"https://vd2.bdstatic.com/mda-md7kfmjabs1ydc2q/sc/cae_h264/1617866669/mda-md7kfmjabs1ydc2q.mp4");
        videoList.add(video8);
        Video video9 = new Video("运动养生",R.drawable.video,"https://vd4.bdstatic.com/mda-kdpfg1pen50qgx91/sc/mda-kdpfg1pen50qgx91.mp4");
        videoList.add(video9);
        Video video10 = new Video("秋季养生",R.drawable.video,"https://vd3.bdstatic.com/mda-mh8bie14td4m978e/1080p/cae_h264/1628496682345983430/mda-mh8bie14td4m978e.mp4");
        videoList.add(video10);
        Video video11 = new Video("白莲藕营养价值高",R.drawable.video,"https://vd3.bdstatic.com/mda-madq770atfusv0xv/v1-cae/sc/mda-madq770atfusv0xv.mp4");
        videoList.add(video11);
        Video video12 = new Video("经络养生操",R.drawable.video,"https://vd3.bdstatic.com/mda-kduxi2qg1t92jwie/sc/mda-kduxi2qg1t92jwie.mp4");
        videoList.add(video12);
        Video video13 = new Video("手部动作，提升记忆力",R.drawable.video,"https://vd4.bdstatic.com/mda-jk8qeqb0d2hneryg/sc/mda-jk8qeqb0d2hneryg.mp4");
        videoList.add(video13);
        Video video14 = new Video("好心情也能养生",R.drawable.video,"https://vd3.bdstatic.com/mda-jj5tu5t9wvf7hpqg/sc/mda-jj5tu5t9wvf7hpqg.mp4");
        videoList.add(video14);


    }
}