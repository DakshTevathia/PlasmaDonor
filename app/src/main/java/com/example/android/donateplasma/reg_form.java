package com.example.android.donateplasma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.DefaultTaskExecutor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class reg_form extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;
    EditText name;
    EditText address;
    EditText age;
    EditText number;
    Spinner bloodgroupspinner;
    Button button;
    ProgressDialog pd;
    ImageView covidreportimage;
    String pathtoimage;
    private static final String TAG = "reg_form";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private MenuItem item;
    String blood_group_selected = "";
    FirebaseStorage storage = FirebaseStorage.getInstance();
    String selectedimage = "";
    String email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //// this is how we receiver that extra intent passed int he after_donor_login
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        email_id = (String) data.get("email_id");
        ////
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        age = findViewById(R.id.age);
        number = findViewById(R.id.number);
        bloodgroupspinner = findViewById(R.id.bloodgroupspinner);
        button = findViewById(R.id.button);
        covidreportimage = findViewById(R.id.reportimage);
        //this is for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bloodgroups, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroupspinner.setAdapter(adapter);
        bloodgroupspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //postion_selected_spinner= parent.getSelectedItemPosition();
                blood_group_selected = (String) parent.getItemAtPosition(parent.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkallfields()) {

                    //if(checkage(Integer.parseInt(age.getText().toString()))){

                    //  Log.d(TAG, "Uploading Details");
                    upload1();
                    //}
                    //else {
                    //  Log.d(TAG, "check age returned false entering in else tag");
                    //  Toast.makeText(new reg_form(),"Sorry You Cannot Register",Toast.LENGTH_LONG).show();
                    //Intent i=new Intent(getApplicationContext(),after_donor_login.class);
                    //startActivity(i);
                    //}
                }


            }
        });


        covidreportimage.setOnClickListener(this::onClick);
       // Log.d(TAG, "on clicking the image above executes ");
    }

    public void upload1() {
        dialog();
        //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        Log.d(TAG, "upload1: " + selectedimage);
        StorageReference riversRef = storage.getReference().child(UUID.randomUUID().toString());
        UploadTask uploadTask = riversRef.putFile(Uri.parse(selectedimage));

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d(TAG, "onFailure: " + exception.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.d(TAG, "onSuccess: ");
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        pathtoimage = uri.toString();
                        upload(pathtoimage);
                        Log.d(TAG, "onSuccess: " + uri);
                    }
                });


            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryintent, RESULT_LOAD_IMAGE);
        Log.d(TAG, "on clicking the image above executes ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedimage = String.valueOf(data.getData());
            covidreportimage.setImageURI(Uri.parse(selectedimage));
        }
    }

    public void upload(String s) {
        //upload in cloud firestore
        //remember the names should be same in the List_of_Donors and
        Map<String, Object> details = new HashMap<>();
        details.put("Name", name.getText().toString());
        details.put("Number", number.getText().toString());
        details.put("Address", address.getText().toString());
        details.put("BloodGroup", blood_group_selected);
        details.put("Age", age.getText().toString());
        details.put("ImageAddress", s);
        // GoogleSignInAccount email_id= GoogleSignIn.getLastSignedInAccount(this);
        details.put("email_id", email_id);
        db.collection("Donor_Name")
                .add(details)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        pd.dismiss();
                        resetdetails();
                        Log.d(TAG, "Uploaded");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);

                        Toast.makeText(reg_form.this, "Some Error", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void resetdetails() {
        name.getText().clear();
        address.getText().clear();
        number.setText("");
        age.setText("");
        bloodgroupspinner.setSelection(0);
        blood_group_selected = "";
        selectedimage = "";
        covidreportimage.setImageResource(R.drawable.uploadimage);
    }

    public void dialog() {
        pd = new ProgressDialog(reg_form.this);
        pd.setMessage("uploading data");
        pd.setCancelable(false);
        pd.show();

    }

    public boolean checkallfields() {
        if (name.getText().toString().isEmpty()) {
            Toast.makeText(reg_form.this, "Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (number.getText().toString().isEmpty()) {
            Toast.makeText(reg_form.this, "Enter Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address.getText().toString().isEmpty()) {
            Toast.makeText(reg_form.this, "Enter Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bloodgroupspinner.getSelectedItem().toString().equals("Select Blood Group")) {
            Toast.makeText(reg_form.this, "Select Your Blood Group", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (age.getText().toString().isEmpty()) {
            Toast.makeText(reg_form.this, "Enter Age", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedimage.isEmpty()) {
            Toast.makeText(reg_form.this, "Upload Your Covid +ve Report", Toast.LENGTH_SHORT).show();
            return false;
        }
        Integer x = Integer.parseInt(age.getText().toString());
        if (x < 18) {
            Toast.makeText(reg_form.this, "Your age is less than 18", Toast.LENGTH_LONG).show();
            return false;
        }
        if (x > 60) {
            Toast.makeText(reg_form.this, "Your age is more than 60", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


}