package com.example.bookify1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<BukuModel> dataList;


    public BukuAdapter(Context context, ArrayList<BukuModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buku, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BukuModel bukuModel = dataList.get(position);
        Glide.with(context).load(bukuModel.getDataImage()).into(holder.recImage);
        holder.recTitle.setText(bukuModel.getTitle());
        holder.recDesc.setText(bukuModel.getDesc());
        holder.recWriter.setText(bukuModel.getWriter());
        holder.recYear.setText(bukuModel.getYear());
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recImage;
        TextView recTitle, recDesc, recWriter, recYear;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recImage = itemView.findViewById(R.id.tv_image);
            recTitle = itemView.findViewById(R.id.tv_title);
            recWriter = itemView.findViewById(R.id.tv_writer);
            recDesc = itemView.findViewById(R.id.tv_desc);
            recYear = itemView.findViewById(R.id.tv_year);
        }

    }
}