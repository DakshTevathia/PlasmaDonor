package com.example.android.donateplasma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class eligibilty_form extends AppCompatActivity {
    Button checkbutton;
    Button resetbutton;
    RadioButton recoveredyes;
    RadioButton recoveredno;
    RadioButton insulinyes;
    RadioButton insulinno;
    RadioButton chronicyes;
    RadioButton chronicno;
    RadioButton canceryes;
    RadioButton cancerno;
    RadioButton genderM;
    RadioButton genderF;
    RadioButton genderN;
    RadioButton pregnentyes;
    RadioButton pregnentno;
    TextView pregnenttv;
    EditText age;
    EditText weight;
    LinearLayout invisiblelayout;
    RadioGroup rg1;
    RadioGroup rg2;
    RadioGroup rg3;
    RadioGroup rg4;
    RadioGroup rg5;
    RadioGroup rg6;
    private static final String TAG = "eligibilty_form";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligibilty_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        invisiblelayout = findViewById(R.id.invisiblelayout);
        age = findViewById(R.id.age);
        weight = findViewById(R.id.weight);
        recoveredyes = findViewById(R.id.rb1yes);
        recoveredno = findViewById(R.id.rb1no);
        insulinyes = findViewById(R.id.rb2yes);
        insulinno = findViewById(R.id.rb2no);
        canceryes = findViewById(R.id.rb3yes);
        cancerno = findViewById(R.id.rb3no);
        chronicyes = findViewById(R.id.rb4yes);
        chronicno = findViewById(R.id.rb4no);
        genderM = findViewById(R.id.male);
        genderF = findViewById(R.id.female);
        genderN = findViewById(R.id.PNTS);
        pregnentyes = findViewById((R.id.pregnentyes));
        pregnentno = findViewById((R.id.pregnentno));
        pregnenttv = findViewById(R.id.pregnenttv);
        checkbutton = findViewById(R.id.check);
        resetbutton = findViewById(R.id.reset);
        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);
        rg3 = findViewById(R.id.rg3);
        rg4 = findViewById(R.id.rg4);
        rg5 = findViewById(R.id.rg5);
        rg6 = findViewById(R.id.rg6);

        invisiblelayout.setVisibility(View.GONE);
        //to insure that pregnent radiobuttons are not visible till we check the female button in the gender radio group
        genderF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    invisiblelayout.setVisibility(View.VISIBLE);
                } else {
                    invisiblelayout.setVisibility(View.GONE);
                }
            }
        });

        //////////////////
        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do some action on button click
                // if(!allfilled()){Toast.makeText(eligibilty_form.this,"Filled All Fields",Toast.LENGTH_LONG).show();}
                if (allfilled()) {
                    if (true) {

                        boolean x = checkeligibility();
                        if (x) {
                           yeseligibiltydialog();
                        } else {
                            noeligibiltydialog();
                        }
                    }
                } else {
                    allfilled();
                }


            }
        });
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resets the whole form
                rg1.clearCheck();
                rg2.clearCheck();
                rg3.clearCheck();
                rg4.clearCheck();
                rg5.clearCheck();
                rg6.clearCheck();
                age.getText().clear();
                weight.getText().clear();


            }
        });

    }

    public boolean allfilled() {
        //to ensure that all fields are checked
        int selectedradio1 = rg1.getCheckedRadioButtonId();
        int selectedradio2 = rg2.getCheckedRadioButtonId();
        int selectedradio3 = rg3.getCheckedRadioButtonId();
        int selectedradio4 = rg4.getCheckedRadioButtonId();
        int selectedradio5 = rg5.getCheckedRadioButtonId();
        int selectedradio6 = rg6.getCheckedRadioButtonId();
        if (selectedradio1 == -1) {
            Toast.makeText(eligibilty_form.this, "Select Covid Recovery Field", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (age.getText().toString().isEmpty()) {
            Toast.makeText(eligibilty_form.this, "Enter Your Age", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (weight.getText().toString().isEmpty()) {
            Toast.makeText(eligibilty_form.this, "Enter Your Weight", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedradio2 == -1) {
            Toast.makeText(eligibilty_form.this, "Select Insulin Field", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedradio3 == -1) {
            Toast.makeText(eligibilty_form.this, "Select Cancer Survivor Field", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedradio4 == -1) {
            Toast.makeText(eligibilty_form.this, "Select Chronic Diseases Field", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedradio5 == -1) {
            Toast.makeText(eligibilty_form.this, "Select Your Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (genderF.isChecked()) {
            if (selectedradio6 == -1) {
                Toast.makeText(eligibilty_form.this, "Select Pregnency", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }


    public boolean checkeligibility() {
        //all conditions to be checked here
        boolean eligibilitycriteria = true;

        Log.d(TAG, "checkeligibility 1: " + eligibilitycriteria);
        if (recoveredno.isChecked()) {
            eligibilitycriteria = false;
            Log.d(TAG, "checkeligibility 2: " + eligibilitycriteria);
            return eligibilitycriteria;
        }
        if (checkage() == false) {
            eligibilitycriteria = false;
            Log.d(TAG, "checkeligibility 3: " + eligibilitycriteria);
            return eligibilitycriteria;
        }

        if (checkweight() == false) {
            eligibilitycriteria = false;
            Log.d(TAG, "checkeligibility 4: " + eligibilitycriteria);
            return eligibilitycriteria;
        }

        if (insulinyes.isChecked()) {
            eligibilitycriteria = false;
            Log.d(TAG, "checkeligibility 5: " + eligibilitycriteria);
            return eligibilitycriteria;
        }
        if (canceryes.isChecked()) {
            eligibilitycriteria = false;
            return eligibilitycriteria;
        }
        if (chronicyes.isChecked()) {
            eligibilitycriteria = false;
            return eligibilitycriteria;
        }
        if (genderM.isChecked()) {
            eligibilitycriteria = true;
        }
        if (genderN.isChecked()) {
            eligibilitycriteria = true;
        }
        if (genderF.isChecked()) {
            if (pregnentyes.isChecked()) {
                eligibilitycriteria = false;
                return eligibilitycriteria;
            }
        }
        return eligibilitycriteria;
    }

    public boolean checkage() {
        return (Integer.parseInt(age.getText().toString()) <= 60 && Integer.parseInt(age.getText().toString()) >= 18);
    }

    public boolean checkweight() {
        int x = Integer.parseInt(weight.getText().toString());
        if (x < 50) {
            return false;
        } else {
            return true;
        }
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
    public void yeseligibiltydialog(){
        AlertDialog.Builder eligibilitydialog=new AlertDialog.Builder(this);
        eligibilitydialog.setTitle("You Can Donate!");
        eligibilitydialog.setMessage("Want to donate plasma and help others in need. \n Click Yes to register as a Plasma Donor.");
        eligibilitydialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent3 = new Intent(eligibilty_form.this, MainActivity.class);
                startActivity(intent3);
            }
        });
        eligibilitydialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=eligibilitydialog.create();
        eligibilitydialog.show();
    }
    public void noeligibiltydialog(){
        AlertDialog.Builder eligibilitydialog=new AlertDialog.Builder(this);
        eligibilitydialog.setTitle("Oops!");
        eligibilitydialog.setMessage("You do not fulfill the necessary conditions required to be able to donate Convalescent Plasma.");
        eligibilitydialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { //do nothing just exit automatically
                 }
        });
        AlertDialog dialog=eligibilitydialog.create();
        eligibilitydialog.show();
    }
}