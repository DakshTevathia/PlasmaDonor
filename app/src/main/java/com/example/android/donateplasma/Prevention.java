package com.example.android.donateplasma;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Prevention extends AppCompatActivity {
    RecyclerView autorecyclerview;
    autoslideadapter adapter2;
    Timer timer;
    private static final String TAG = "Prevention";
    int i = 0;
    ArrayList<prevention_images_text> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevention);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        autorecyclerview = findViewById(R.id.autorecyclerview);
        list = new ArrayList<prevention_images_text>();
        add_data();
        //to scroll horizontally
        autorecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        autorecyclerview.setAdapter(new autoslideadapter(list));
        //this is for the autoslide purpose of our horizontal recycler view

    }

    public void onStop() {

        super.onStop();
        cancel();
    }
    public  void cancel(){
        if(timer!=null){
            timer.cancel();
        }
    }

    public void callTimer(){
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                i = i + 1;
                autorecyclerview.smoothScrollToPosition(i);
                if (i == list.size()) {
                    // Log.d(TAG, "value of i: "+i);
                    i = -1;
                    i = i + 1;
                    // Log.d(TAG, "value of i1: "+i);
                    autorecyclerview.smoothScrollToPosition(i);
                }
            }
        }, 3500, 3500);
    }
    public void onStart() {
        super.onStart();
        callTimer();

    }
    private void add_data() {

        prevention_images_text pa1 = new prevention_images_text();
        pa1.setImage(R.drawable.washyourhands);
        pa1.setText(getResources().getString(R.string.stop1));
        list.add(pa1);


        prevention_images_text pa2 = new prevention_images_text();
        pa2.setImage(R.drawable.socialdistancing);
        pa2.setText(getResources().getString(R.string.stop2));
        list.add(pa2);

        prevention_images_text pa3 = new prevention_images_text();
        pa3.setImage(R.drawable.no_touch);
        pa3.setText(getResources().getString(R.string.stop3));
        list.add(pa3);


        prevention_images_text pa4 = new prevention_images_text();
        pa4.setImage(R.drawable.respiratoryhygine);
        pa4.setText(getResources().getString(R.string.stop4));
        list.add(pa4);


        prevention_images_text pa5 = new prevention_images_text();
        pa5.setImage(R.drawable.stayhome);
        pa5.setText(getResources().getString(R.string.stop5));
        list.add(pa5);

        prevention_images_text pa6 = new prevention_images_text();
        pa6.setImage(R.drawable.hotspots);
        pa6.setText(getResources().getString(R.string.stop6));
        list.add(pa6);
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

    //gets called automatically once the timer is executed
    @Override
    public void onDestroy() {
        super.onDestroy();
        cancel();
    }

}