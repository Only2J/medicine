package com.example.myapplication.home;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.myapplication.BaseFragment;
import com.example.myapplication.R;
import com.example.myapplication.VideoActivity;
import com.example.myapplication.food.FoodActivity;
import com.example.myapplication.knowledge.KnowledgeActivity;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class HomeFragment extends BaseFragment {
    Banner banner;
    TextView home_ts_content,shiLing_title,jieQi_type,jieQi_title,jieQi_content1,jieQi_content2,jieQi_content3;
    ConstraintLayout home_cons1;
    ConstraintLayout home_cons2;
    ConstraintLayout home_cons3;
    ConstraintLayout home_cons4;


    @Override
    protected void initViews() {

        banner = find(R.id.home_banner10);
        home_ts_content= find(R.id.home_ts_content);
        shiLing_title =find(R.id.shiLing_title);
        jieQi_type = find(R.id.jieQi_type);
        jieQi_title = find(R.id.jieQi_title);
        jieQi_content1 = find(R.id.jieQi_content1);
        jieQi_content2 = find(R.id.jieQi_content2);
        jieQi_content3 = find(R.id.jieQi_content3);
        home_cons1 = find(R.id.home_cons1);
        home_cons1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转
                Intent intent = new Intent(getActivity(), FoodActivity.class);
                startActivity(intent);
            }
        });
        home_cons2 = find(R.id.home_cons2);
        home_cons2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转
                Intent intent = new Intent(getActivity(), KnowledgeActivity.class);
                startActivity(intent);
            }
        });
        home_cons3 = find(R.id.home_cons3);
        home_cons3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转

            }
        });
        home_cons4 = find(R.id.home_cons4);
        home_cons4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                startActivity(intent);
            }
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();//1.定义一个client
                Request request = new Request.Builder().url("https://api.tianapi.com/txapi/healthtip/index?key=4d909da84f8ed2341ff82dea59337998").build();//2.定义一个request
                Call call = okHttpClient.newCall(request);
                try{
                    String result = call.execute().body().string();
                    Gson gson = new Gson();
                    Ts ts = gson.fromJson(result,Ts.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            home_ts_content.setText(ts.newslist.get(0).content);
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();//1.定义一个client
                Request request = new Request.Builder().url("https://www.fastmock.site/mock/789a822cfe63ceedc490e17eb70b9f67/app/banner").build();//2.定义一个request
                Call call = okHttpClient.newCall(request);
                try {
                    String result = call.execute().body().string();
                    Gson gson = new Gson();
                    HomeBanner homeBanner = gson.fromJson(result, HomeBanner.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            banner.setAdapter(new BannerImageAdapter<String>(homeBanner.banners) {
                                @Override
                                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                                    //图片加载自己实现
                                    Glide.with(holder.itemView)
                                            .load(data)
                                            .apply(RequestOptions.centerCropTransform())
                                            .into(holder.imageView);
                                }
                            }).addBannerLifecycleObserver((LifecycleOwner) getActivity())//添加生命周期观察者
                                    .setIndicator(new CircleIndicator(getActivity()));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

}

