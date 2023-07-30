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

public class notify_police extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button b1;
    Spinner s1;
    ArrayList<String> p_id,p_name;
    String url,psid;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_police);
        b1=findViewById(R.id.button16);
        s1=findViewById(R.id.spinner);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url ="http://"+sh.getString("ip", "") + ":5000/viewpolice";
        RequestQueue queue = Volley.newRequestQueue(notify_police.this);

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

                     ArrayAdapter<String> ad=new ArrayAdapter<>(notify_police.this,android.R.layout.simple_list_item_1,p_name);
                    s1.setAdapter(ad);
                    s1.setOnItemSelectedListener(notify_police.this);

//                    l1.setAdapter(new custom_two(viewcameranotifiaction.this,photo,date));
/*
                    l1.setOnItemClickListener(viewcameranotifiaction.this);
*/

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                    Toast.makeText(notify_police.this, "err"+e, Toast.LENGTH_SHORT).show();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(notify_police.this, "err"+error, Toast.LENGTH_SHORT).show();
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
            public void onClick(View v) {



                RequestQueue queue = Volley.newRequestQueue(notify_police.this);
                url = "http://" + sh.getString("ip","") + ":5000/policenotification";

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
                                Toast.makeText(notify_police.this, "success", Toast.LENGTH_SHORT).show();

                                Intent ik = new Intent(getApplicationContext(), home.class);
                                startActivity(ik);

                            }
                            else if (res.equalsIgnoreCase("exist")) {
                                Toast.makeText(notify_police.this, "Already sent", Toast.LENGTH_SHORT).show();

                                Intent ik = new Intent(getApplicationContext(), home.class);
                                startActivity(ik);

                            }
                            else {

                                Toast.makeText(notify_police.this, "Failed", Toast.LENGTH_SHORT).show();


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
                        params.put("cn_id", sh.getString("nid",""));
                        params.put("p_id", psid);

                        return params;
                    }
                };
                queue.add(stringRequest);

















            }
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