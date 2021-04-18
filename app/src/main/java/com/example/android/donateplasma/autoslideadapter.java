package com.example.android.donateplasma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class autoslideadapter extends RecyclerView.Adapter<autoslideadapter.myviewholder2> {
    ArrayList<prevention_images_text> list;

    public autoslideadapter(ArrayList<prevention_images_text> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public myviewholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singleautoslidepage, parent, false);
        return new myviewholder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder2 holder, int position) {
        prevention_images_text pa = list.get(position);
        holder.slidingtext.setText(pa.getText());
        holder.slidingimage.setImageResource(pa.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class myviewholder2 extends RecyclerView.ViewHolder {
        TextView slidingtext;
        ImageView slidingimage;

        public myviewholder2(@NonNull View itemView) {
            super(itemView);
            slidingimage = itemView.findViewById(R.id.slidingimage);
            slidingtext = itemView.findViewById(R.id.slidingtext);
        }
    }

}
