package com.example.android.donateplasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class after_donor_login extends AppCompatActivity {
    ImageView img1;
    ImageView img2;
    TextView emailidtext;
    GoogleSignInClient mGoogleSignInClient;
    //GoogleApiClient googleapiclient;

    //GoogleSignInAccount emailaccount;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    /*
    @Override

     public void onStart() {
         super.onStart();
         OptionalPendingResult<GoogleSignInResult> opr=Auth.GoogleSignInApi.silentSignIn((googleapiclient));
     }

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_donor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //googlelogin();
        //this is for the back button in the action bar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img1 = (ImageView) findViewById(R.id.image1adl);
        img2 = (ImageView) findViewById(R.id.image2adl);
        emailidtext = findViewById(R.id.emailidtv);

        mAuth = FirebaseAuth.getInstance();
        //dont use the statement below it has problems
        // emailaccount= GoogleSignIn.getLastSignedInAccount(this);
        // GoogleSignInOptions.Builder gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN);
        // emailidtext.setText("Logged in with: "+ emailaccount.getEmail());
        user = mAuth.getCurrentUser();
        emailidtext.setText("Logged in with: " + user.getEmail());


    }

    public void image1clicked(View v) {
        Intent intent = new Intent(after_donor_login.this, reg_form.class);
        //this is to pass email with the intent itself. we can pass multiple using this
        intent.putExtra("email_id", user.getEmail());
        startActivity(intent);
    }

    public void image2clicked(View v) {
        Intent intent = new Intent(after_donor_login.this, List_of_Donors.class);
        startActivity(intent);
    }
    public void image3clicked(View v) {
        Intent intent = new Intent(after_donor_login.this, update_details.class);
        intent.putExtra("email_id", user.getEmail());
        startActivity(intent);
    }
    public void image4clicked(View v) {
        Intent intent = new Intent(after_donor_login.this, covidreports.class);
        intent.putExtra("email_id", user.getEmail());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.logoutbutton:
                signOut();
                break;
            /*
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                Intent intent = new Intent(after_donor_login.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;

             */
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    private void signOut() {
        signoutwork();
        mAuth.signOut();
        mGoogleSignInClient.revokeAccess();
        Intent intent = new Intent(after_donor_login.this, MainActivity.class);
        startActivity(intent);
        finish();
        /*
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });

         */
    }
    /*
   public void googlelogin(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
         googleapiclient=new GoogleApiClient.Builder(this)
                 .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                 .build();
    }

    */
    public void signoutwork(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

}