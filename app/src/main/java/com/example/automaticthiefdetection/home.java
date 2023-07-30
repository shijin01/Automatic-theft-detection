package com.example.automaticthiefdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {
    Button addcam,viewfam,addfam,viewcam,sendcom,viewreply,lout;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        addcam=findViewById(R.id.button8);
        viewfam=findViewById(R.id.button9);
        addfam=findViewById(R.id.button10);
        viewcam=findViewById(R.id.button11);
        sendcom=findViewById(R.id.button12);
        viewreply=findViewById(R.id.button14);
        lout=findViewById(R.id.button15);
        addcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(new Intent(getApplicationContext(),addcamera.class));
                startActivity(i);

            }
        });
        viewfam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(new Intent(getApplicationContext(),viewfamilymember.class));
                startActivity(i);

            }
        });
        addfam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(new Intent(getApplicationContext(),Addfamily.class));
                startActivity(i);

            }
        });
        viewcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(new Intent(getApplicationContext(),viewcameranotifiaction.class));
                startActivity(i);

            }
        });
        sendcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(new Intent(getApplicationContext(),COMPLAINT.class));
                startActivity(i);

            }
        });
        viewreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(new Intent(getApplicationContext(),viewrply.class));
                startActivity(i);

            }
        });
        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(new Intent(getApplicationContext(),MainActivity.class));
                startActivity(i);

            }
        });
    }
}