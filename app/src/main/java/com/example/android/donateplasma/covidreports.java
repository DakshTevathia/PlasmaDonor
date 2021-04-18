package com.example.android.donateplasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class covidreports extends AppCompatActivity {
    RecyclerView recyclerview;
    ArrayList<Model> arrlist;
    myAdapter_report adapterreport;
    FirebaseFirestore db_report = FirebaseFirestore.getInstance();
    private static final String TAG = "covidreports";
    String email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covidreports);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent= getIntent();
        Bundle data= intent.getExtras();
        email_id= (String) data.get("email_id");
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        arrlist = new ArrayList<>();
        adapterreport = new myAdapter_report(arrlist, covidreports.this);
        recyclerview.setAdapter(adapterreport);
        getData(email_id);
    }

    private ArrayList<Model> dataqueue_report() {
        ArrayList<Model> holder = new ArrayList<>();
        return holder;
    }


    public void getData(String s) {

        //to fetch data from firestore
        db_report.collection("Donor_Name")
                .whereEqualTo("email_id", s)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //same names as in reg_form which uploads data on cloud firestore
                                String name = (String) document.getData().get("Name");
                                String imageurl = (String) document.getData().get("ImageAddress");
                                Model ob1 = new Model();
                                ob1.setImageurl(imageurl);
                                ob1.setName(name);
                                arrlist.add(ob1);
                            }
                            adapterreport.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }


}