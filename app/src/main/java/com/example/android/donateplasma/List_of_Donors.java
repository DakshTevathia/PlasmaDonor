package com.example.android.donateplasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class List_of_Donors extends AppCompatActivity {
    RecyclerView rcv;
    myAdapter adapter;
    ArrayList<Model> arrlist;
    private static final String TAG = "List_of_Donors";  // to remove Log.d error
    FirebaseFirestore db = FirebaseFirestore.getInstance();  //to remove db error this calls data from cloud firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of__donors);
        rcv = findViewById(R.id.recview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setItemAnimator(new DefaultItemAnimator());
        arrlist = new ArrayList<>();
        adapter = new myAdapter(arrlist);
        rcv.setAdapter(adapter);
        getData();
    }

    private ArrayList<Model> dataqueue() {
        ArrayList<Model> holder = new ArrayList<>();
        return holder;
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

    public void getData() {
        //to fetch data from firestore
        db.collection("Donor_Name")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //the above statement is too see results in logcat
                                //same names as in reg_form which uploads data on cloud firestore
                                String address = (String) document.getData().get("Address");
                                String bloodgroup = (String) document.getData().get("BloodGroup");
                                String name = (String) document.getData().get("Name");
                                String number = (String) document.getData().get("Number");
                                Model ob1 = new Model();
                                ob1.setName(name);
                                ob1.setBlood_group(bloodgroup);
                                ob1.setAddress(address);
                                ob1.setNumber(number);
                                arrlist.add(ob1);
                            }

                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}