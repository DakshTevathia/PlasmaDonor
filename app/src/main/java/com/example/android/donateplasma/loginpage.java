package com.example.android.donateplasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class loginpage extends AppCompatActivity {
    Button loginbutton2;
    EditText username;
    EditText password;
    ProgressDialog pd;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "loginpage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginbutton2 = (Button) findViewById(R.id.loginbutton2);
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);

        loginbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // readdata();

                String u = username.getText().toString();
                String p = password.getText().toString();
                if (!u.isEmpty() && !p.isEmpty()) {
                    // Intent intent = new Intent(loginpage.this, after_donor_login.class);
                    // startActivity(intent);
                    dialog();
                    readdata();
                } else if (p.isEmpty() && u.isEmpty()) {
                    Toast.makeText(loginpage.this, "Password and Username Empty", Toast.LENGTH_SHORT).show();
                } else if (p.isEmpty()) {
                    Toast.makeText(loginpage.this, "Password is empty ", Toast.LENGTH_SHORT).show();
                } else if (u.isEmpty()) {
                    Toast.makeText(loginpage.this, "Username is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public void readdata() {
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String u = username.getText().toString();
                                String p = password.getText().toString();
                                if (u.equals("abc") && p.equals("123")) {
                                    Intent intent = new Intent(loginpage.this, after_donor_login.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(loginpage.this,"Incorrect Username/Password",Toast.LENGTH_LONG).show();
                                }
                                pd.dismiss();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
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

    public void dialog() {
        pd = new ProgressDialog(loginpage.this);
        pd.setMessage("Logging In");
        pd.setCancelable(false);
        pd.show();

    }
}