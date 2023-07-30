package com.example.automaticthiefdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class COMPLAINT extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button b1;
    EditText e1;
    SharedPreferences sh;
    String url,psid;
    Spinner s1;
    ArrayList<String> p_id,p_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=findViewById(R.id.editTextTextMultiLine);
        b1=findViewById(R.id.button5);
        s1=findViewById(R.id.spinner2);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url ="http://"+sh.getString("ip", "") + ":5000/viewpolice";
        RequestQueue queue = Volley.newRequestQueue(COMPLAINT.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    p_id=new ArrayList<>();
                    p_name=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        p_id.add(jo.getString("lid"));
                        p_name.add(jo.getString("station"));


                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<>(COMPLAINT.this,android.R.layout.simple_list_item_1,p_name);
                    s1.setAdapter(ad);
                    s1.setOnItemSelectedListener(COMPLAINT.this);

//                    l1.setAdapter(new custom_two(viewcameranotifiaction.this,photo,date));
/*
                    l1.setOnItemClickListener(viewcameranotifiaction.this);
*/

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                    Toast.makeText(COMPLAINT.this, "err"+e, Toast.LENGTH_SHORT).show();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(COMPLAINT.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid",""));
                return params;
            }
        };
        queue.add(stringRequest);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String compliant=e1.getText().toString();
                if (compliant.equalsIgnoreCase(""))
                {
                    e1.setError("Enter complaint");
                }
                else{
                RequestQueue queue = Volley.newRequestQueue(COMPLAINT.this);
                url = "http://" + sh.getString("ip","") + ":5000/addcomplaint";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");

                            if (res.equalsIgnoreCase("success")) {
                                Toast.makeText(COMPLAINT.this, "Success", Toast.LENGTH_SHORT).show();
                                Intent ik = new Intent(getApplicationContext(), home.class);
                                startActivity(ik);

                            } else {

                                Toast.makeText(COMPLAINT.this, "Sending failed!", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("complaint", compliant);
                        params.put("uid", sh.getString("lid",""));
                        params.put("p_id",psid);

                        return params;
                    }
                };
                queue.add(stringRequest);

            }}
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        psid=p_id.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}