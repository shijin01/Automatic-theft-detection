package com.example.automaticthiefdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewcameranotifiaction extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> photo,date,nid;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcameranotifiaction);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=findViewById(R.id.list1);

        url ="http://"+sh.getString("ip", "") + ":5000/viewcameranotification";
        RequestQueue queue = Volley.newRequestQueue(viewcameranotifiaction.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    photo=new ArrayList<>();
                    date=new ArrayList<>();
                    nid=new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        photo.add(jo.getString("image"));
                        date.add(jo.getString("date"));
                        nid.add(jo.getString("notification_id"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom_two(viewcameranotifiaction.this,photo,date));
                  l1.setOnItemClickListener(viewcameranotifiaction.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                    Toast.makeText(viewcameranotifiaction.this, "err"+e, Toast.LENGTH_SHORT).show();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewcameranotifiaction.this, "err"+error, Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        SharedPreferences.Editor ed=sh.edit();
        ed.putString("nid",nid.get(position));
        ed.commit();
        startActivity(new Intent(getApplicationContext(),notify_police.class));


    }
}