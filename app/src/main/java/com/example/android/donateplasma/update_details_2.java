package com.example.android.donateplasma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class update_details_2 extends AppCompatActivity {
    private static final String TAG = "updatedetails_2";
    private static final int RESULT_UPDATE_IMAGE = 1;
    EditText updatename;
    EditText updatenumber;
    EditText updateaddress;
    EditText updateage;
    ImageView updatecovid_report;
    ImageView oldreport;
    String pathtoimage_updated;
    String selectedimage_update = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    String oldimage;
    String oldbloodgroup;
    Spinner updatebloodgroup;
    String blood_group_selected = "";
    String oldname;
    String oldaddress;
    String oldnumber;
    String oldage;
    Button updatebuttonclick;
    String email_id;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details_2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        updatename = findViewById(R.id.updatename);
        updateaddress = findViewById(R.id.updateaddress);
        updatenumber = findViewById(R.id.updatenumber);
        updateage = findViewById(R.id.updateage);
        updatebloodgroup = findViewById(R.id.updatebloodgroupspinner);
        updatecovid_report = findViewById(R.id.newreport);
        oldreport = findViewById(R.id.oldreport);
        updatebuttonclick = findViewById(R.id.updatebuttonclick);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        oldname = (String) data.get("name");
        oldaddress = (String) data.get("address");
        oldnumber = (String) data.get("number");
        oldage = (String) data.get("age");
        oldbloodgroup = (String) data.get("bloodgroup");
        oldimage = (String) data.get("image");
        email_id=(String)data.get("email_id");
        Log.d(TAG, "old image uri: " + oldimage);
        //  Log.d(TAG, "bloodgroup: " + oldbloodgroup);
        Log.d(TAG, "Image URl receiving " + oldimage);
        updatename.setText(oldname);
        updateaddress.setText(oldnumber);
        updatenumber.setText(oldnumber);
        updateage.setText(oldage);
        Glide.with(update_details_2.this).load(oldimage).into(oldreport);

        ArrayList<String> bloodgroupselected = new ArrayList<String>();
        bloodgroupselected.addAll(Arrays.asList(getResources().getStringArray(R.array.bloodgroups)));


        ///Spinner

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bloodgroupsupdate, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updatebloodgroup.setAdapter(adapter);
        updatebloodgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                blood_group_selected = (String) parent.getItemAtPosition(parent.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int pos = position(bloodgroupselected, oldbloodgroup);
        updatebloodgroup.setSelection(pos);


        ///spinner ends


        updatebuttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatecoivdreport();
            }
        });

        updatecovid_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryintent, RESULT_UPDATE_IMAGE);
                Log.d(TAG, "on clicking the image above executes ");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_UPDATE_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedimage_update = String.valueOf(data.getData());
            updatecovid_report.setImageURI(Uri.parse(selectedimage_update));
        }
    }

    public int position(ArrayList<String> list, String blood_group) {
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).equals(oldbloodgroup)) {

                return i;
            }
        }
        return 0;
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


    public void updatecoivdreport(){
        dialog();
        //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        Log.d(TAG, "upload image: " + selectedimage_update);
        StorageReference riversRef = storage.getReference().child(UUID.randomUUID().toString());
        UploadTask uploadTask = riversRef.putFile(Uri.parse(selectedimage_update));

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
                        pathtoimage_updated = uri.toString();
                        upload(pathtoimage_updated);
                        Log.d(TAG, "onSuccess: " + uri);
                    }
                });


            }
        });
    }


    //to upload everything in cloudfirestore
    public void upload(String s) {
        //upload in cloud firestore
        //remember the names should be same in the List_of_Donors and
        Map<String, Object> updateddetails = new HashMap<>();
        updateddetails.put("Name", updatename.getText().toString());
        updateddetails.put("Number", updatenumber.getText().toString());
        updateddetails.put("Address", updateaddress.getText().toString());
        updateddetails.put("BloodGroup", blood_group_selected);
        updateddetails.put("Age", updateage.getText().toString());
        updateddetails.put("ImageAddress", s);
        // GoogleSignInAccount email_id= GoogleSignIn.getLastSignedInAccount(this);
        //updateddetails.put("email_id", email_id);
        db.collection("Donor_Name").document(/*id of the document to be put here*/ )
                .set("updateddetails")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Log.d(TAG, "onSuccess: Successfully updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);

                        Toast.makeText(update_details_2.this, "Some Error", Toast.LENGTH_SHORT).show();

                    }
                });

    }
    public void dialog() {
        pd = new ProgressDialog(update_details_2.this);
        pd.setMessage("updating data");
        pd.setCancelable(false);
        pd.show();

    }


}