package com.example.android.donateplasma;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Button loginbutton;
    ImageView image1;
    SignInButton googlelogin;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog signindialog;
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

   /* @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
       // FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
        //if there is already someone logged in then directly go to the after_donor_login activity.
       mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser2=mAuth.getCurrentUser();
       if(currentUser2!=null){
           //startActivity(new Intent(getApplicationContext(),after_donor_login.class));
           //Toast.makeText(getApplicationContext(),"user is already present",Toast.LENGTH_LONG).show();
       }
       else {
           //Toast.makeText(getApplicationContext(),"User is absent",Toast.LENGTH_LONG).show();

       }
    }

    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        googlelogin = findViewById(R.id.googlelogin);
        image1 = (ImageView) findViewById(R.id.image1);
        /*loginbutton = (Button) findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, loginpage.class);
                startActivity(intent);
            }
        });

         */

        signinwork();
        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login work
                processlogin();
                //Intent intent = new Intent(MainActivity.this, registration_donor.class);
                //startActivity(intent);
            }
        });


    }


    @Override
    public void onBackPressed() {
        exit();
    }


    //this is for the app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //this is for the app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //   int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void image1click(View v) {
        Intent intent1 = new Intent(MainActivity.this, Prevention.class);
        startActivity(intent1);
    }

    public void image2click(View v) {
        Intent intent3 = new Intent(MainActivity.this, eligibilty_form.class);
        startActivity(intent3);
    }

    public void image3click(View v) {
        //pop up a dialog saying these effects may not occur usually but are to be kept in mind while donating
        effectsdialog();

    }

    public void image4click(View v) {
        Intent intent4 = new Intent(MainActivity.this, List_of_Donors.class);
        startActivity(intent4);
    }

    public void exit() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Confirm Exit");
        alertdialog.setMessage("Are you sure, You want to exit");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //continue the app, do nothing
                // Toast.makeText(MainActivity.this,"App Resumed",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alertdialog.create();
        alertdialog.show();
    }

    public void effectsdialog() {
        AlertDialog.Builder effectsdialogbox = new AlertDialog.Builder(this);
        effectsdialogbox.setTitle("Alert");
        effectsdialogbox.setMessage("Plasma donation often has minor effects, very rarely there are any serious side effects after donation.");
        effectsdialogbox.setPositiveButton("Read More", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent3 = new Intent(MainActivity.this, donationsideeffects.class);
                startActivity(intent3);
            }
        });
        effectsdialogbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = effectsdialogbox.create();
        effectsdialogbox.show();
    }

    //to be called in oncreate method, this generates a process req. and passes it to google that we need to login using google sign in
    public void signinwork() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    private void processlogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    Log.d(TAG, "try block1");
                    GoogleSignInAccount account = (task).getResult(ApiException.class);
                    // Log.d(TAG, "try block2");
                    firebaseAuthWithGoogle(account.getIdToken());
                    // Log.d(TAG, "try block3");
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    // Log.d(TAG, "onActivityResult Exception is: "+e);
                    Toast.makeText(getApplicationContext(), "Error found in getting Google User Information", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Account not Selected", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //go to other activity if sucessfull
                            signingdialogbox();
                            Intent intent = new Intent(MainActivity.this, after_donor_login.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Login Sucessful", Toast.LENGTH_SHORT).show();
                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //   updateUI(null);
                            Toast.makeText(getApplicationContext(), "Problem found in Google Sign In", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signingdialogbox() {
        signindialog = new ProgressDialog(MainActivity.this);
        signindialog.setMessage("Logging In");
        signindialog.setCancelable(false);
        signindialog.show();
    }


}