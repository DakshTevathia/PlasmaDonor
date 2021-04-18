package com.example.android.donateplasma;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myviewholder> {
    ArrayList<Model> data;

    public myAdapter(ArrayList<Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(data.get(position).getName());
        holder.t2.setText(data.get(position).getBlood_group());
        holder.t3.setText(data.get(position).getAddress());
        holder.t5.setText(data.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class myviewholder extends RecyclerView.ViewHolder {
        TextView t5;
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.t1);
            t2 = (TextView) itemView.findViewById(R.id.t2);
            t3 = (TextView) itemView.findViewById(R.id.t3);
            t5 = (TextView) itemView.findViewById(R.id.t5);
        }
    }
}

///////////////////////////////////////////////

/*
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.holder> {
    ArrayList<String> list;

    public myAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.tv1.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class holder extends RecyclerView.ViewHolder {
        TextView tv1;

        public holder(@NonNull View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.t1);
        }
    }
}
*/