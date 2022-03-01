package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<Video> videoList;
    private Context context;
    private LayoutInflater layoutInflater = null;

    // 初始化你的Context
    public VideoAdapter(Context context ,List<Video> videoList) {
        this.videoList =videoList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    //ViewHolder 内部类，继承RecyclerView.ViewHolder
    //在ViewHolder的构造函数中传一个View参数，这个参数通常是RecyclerView子项最外层布局
    public class ViewHolder extends RecyclerView.ViewHolder {
        View videoView;
        ImageView videoImage;
        TextView videoName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView;
            videoImage = itemView.findViewById(R.id.video_image);//获取布局中的videoImage
            videoName = itemView.findViewById(R.id.video_name);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //用于创建ViewHolder
        //在这里加载video_item
        //将加载出来的布局传入构造函数中
        View view = layoutInflater.inflate(R.layout.video_item, parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Video video = videoList.get(position);
//                Toast.makeText(view.getContext(),"你点击了" + video.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,PlaysvideoActivity.class);
                intent.putExtra("url",video.getUrl());
                intent.putExtra("name",video.getName());
                context.startActivity(intent);
            }
        });
        return holder;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //用于对RecyclerView子项的数据进行赋值
        Video video = videoList.get(position);
        holder.videoImage.setImageResource(video.getImageId());
        holder.videoName.setText(video.getName());

    }

    @Override
    public int getItemCount() {
        //告诉RecyclerView共有多少子项
        return videoList.size();
    }


}
