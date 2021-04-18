package com.example.android.donateplasma;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class update_details extends AppCompatActivity {
    String email_id;
    TextView emailidtv;
    FirebaseUser user;
    RecyclerView rcv_updatedetails;
    ArrayList<Model> arrlist_updatedetails;
    myAdapter_updatedetails adapter_update;
    FirebaseFirestore db_update = FirebaseFirestore.getInstance();
    private static final String TAG = "update_details";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
        emailidtv = findViewById(R.id.emailidtv);
        rcv_updatedetails = findViewById(R.id.recyclerview_updatedetails);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        email_id = (String) data.get("email_id");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = mAuth.getCurrentUser();
        emailidtv.setText("Logged in with: " + user.getEmail());
        arrlist_updatedetails = new ArrayList<>();
        rcv_updatedetails.setLayoutManager(new LinearLayoutManager(this));
        adapter_update = new myAdapter_updatedetails(arrlist_updatedetails,update_details.this);
        rcv_updatedetails.setAdapter(adapter_update);
        getData_update();


    }


    public void getData_update() {
        //to fetch data from firestore
        db_update.collection("Donor_Name")
                .whereEqualTo("email_id",email_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //the above statement is too see results in logcat
                                //same names as in reg_form which uploads data on cloud firestore
                                String address = (String) document.getData().get("Address");
                                String bloodgroup = (String) document.getData().get("BloodGroup");
                                String name = (String) document.getData().get("Name");
                                String number = (String) document.getData().get("Number");
                                String age= (String)document.getData().get("Age");
                                String oldimage= (String) document.getData().get("ImageAddress");
                                //String id=(String)document.getData().get("id");

                                Log.d(TAG, "old image " +oldimage);
                                Model ob1 = new Model();
                                ob1.setName(name);
                                ob1.setBlood_group(bloodgroup);
                                ob1.setAddress(address);
                                ob1.setNumber(number);
                                ob1.setAge(age);
                                ob1.setImageurl(oldimage);
                                arrlist_updatedetails.add(ob1);
                            }

                            adapter_update.notifyDataSetChanged();
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