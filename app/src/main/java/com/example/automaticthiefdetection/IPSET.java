package com.example.automaticthiefdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IPSET extends AppCompatActivity {
    Button b1;
    EditText e1;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipset);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=findViewById(R.id.editTextTextPersonName12);
        b1=findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ip=e1.getText().toString();
                if (ip.equalsIgnoreCase(""))
                {
                    e1.setError("Enter your ip");
                }
                else {

                    SharedPreferences.Editor edp = sh.edit();
                    edp.putString("ip", ip);
                    edp.commit();


                    Intent i = new Intent(new Intent(getApplicationContext(), MainActivity.class));
                    startActivity(i);
                }

            }
        });
    }
}