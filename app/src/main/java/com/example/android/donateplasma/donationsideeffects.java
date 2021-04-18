package com.example.android.donateplasma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class donationsideeffects extends AppCompatActivity {
    Button buttonminor;
    Button buttonmild;
    Button buttonsevere;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donationsideeffects);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buttonminor = findViewById(R.id.buttonminor);
        buttonmild = findViewById(R.id.buttonmild);
        buttonsevere = findViewById(R.id.buttonsevere);
        buttonminor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(donationsideeffects.this, minor_side_effects_details.class);
                startActivity(intent);
            }
        });
        buttonmild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(donationsideeffects.this, mild_side_effects_details.class);
                startActivity(intent);
            }
        });
        buttonsevere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(donationsideeffects.this, severe_side_effects_details.class);
                startActivity(intent);
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