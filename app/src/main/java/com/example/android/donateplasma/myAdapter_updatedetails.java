package com.example.android.donateplasma;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class myAdapter_updatedetails extends RecyclerView.Adapter<myAdapter_updatedetails.holder1> {

    ArrayList<Model> aarrlist_update_details;
    Context context1;
   //update_details context1; can do like this too but
   // then it will be specific class only in the statement below we pass update_details context1.
   //this means that this is the context of update_detais and not general context.
    public myAdapter_updatedetails(ArrayList<Model> arrlist_update_details,Context context1) {
        this.aarrlist_update_details = arrlist_update_details;
        this.context1=context1;
    }

    @NonNull
    @Override
    public holder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow_updatedetails, parent, false);
        return new holder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder1 holder, int position) {
        holder.enrtyname.setText(aarrlist_update_details.get(position).getName());


        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //perform action to delte data from cloud firestore
                Toast.makeText(context1, "deletebutton clicked", Toast.LENGTH_SHORT).show();
            }
        });
        holder.updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open the activity where you will update details.
                Intent goforupdate= new Intent(context1,update_details_2.class);
                goforupdate.putExtra("name",aarrlist_update_details.get(position).getName());
                goforupdate.putExtra("address",aarrlist_update_details.get(position).getAddress());
                goforupdate.putExtra("number",aarrlist_update_details.get(position).getNumber());
                goforupdate.putExtra("age",aarrlist_update_details.get(position).getAge());
                goforupdate.putExtra("bloodgroup",aarrlist_update_details.get(position).getBlood_group());
                goforupdate.putExtra("image",aarrlist_update_details.get(position).getImageurl());
                Log.d(TAG, "Image URl sending   "+ aarrlist_update_details.get(position).getImageurl());
                context1.startActivity(goforupdate);
     //           Toast.makeText(context1, "updatebutton clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return aarrlist_update_details.size();
    }


    class holder1 extends RecyclerView.ViewHolder {
        TextView enrtyname;
        TextView bloodgroup;
        TextView address;
        TextView number;
        Button updatebutton;
        Button deletebutton;

        public holder1(@NonNull View itemView) {
            super(itemView);
            enrtyname = (TextView) itemView.findViewById(R.id.name);
            bloodgroup = (TextView) itemView.findViewById(R.id.bloodgroup);
            address = (TextView) itemView.findViewById(R.id.address);
            number = (TextView) itemView.findViewById(R.id.number);
            deletebutton=(Button) itemView.findViewById(R.id.deletebutton);
            updatebutton=(Button) itemView.findViewById(R.id.updatebutton);

        }
    }
}
