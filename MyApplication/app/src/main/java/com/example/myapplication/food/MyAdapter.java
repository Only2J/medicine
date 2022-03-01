package com.example.myapplication.food;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.R;


import java.util.List;
import java.util.zip.Inflater;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.FoodViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<Foods.ResultDTO.ListDTO> foodList;

    public MyAdapter(Context context, List<Foods.ResultDTO.ListDTO> foodList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_food_card, parent, false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(view);
        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(foodList.get(position).name);
        String url =foodList.get(position).pic;
//        Log.e("---", url);
        Glide.with(context)
                .load(url)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,FoodMessageActivity.class);
                intent.putExtra("food", (Parcelable) foodList.get(position));
                context.startActivity(intent);
//                Log.e("---",""+ position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.food_card_image);
            textView = itemView.findViewById(R.id.food_card_text);
        }
    }
}
