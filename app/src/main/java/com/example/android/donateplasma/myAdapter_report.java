package com.example.android.donateplasma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class myAdapter_report extends RecyclerView.Adapter<myAdapter_report.holder>
{
    ArrayList<Model> data_report;

    Context context;

    public myAdapter_report(ArrayList<Model> data, Context context){
        this.data_report = data;
        this.context=context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.singlerow_report,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.txt.setText(data_report.get(position).getName());
        Glide.with(context).load(data_report.get(position).getImageurl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return data_report.size();
    }


    class holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;
        public holder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.report);
            txt=itemView.findViewById(R.id.txt);
        }
    }
}
