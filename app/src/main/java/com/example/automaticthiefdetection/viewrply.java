package com.example.automaticthiefdetection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewrply extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> complaint,date,reply,c_id,station;
    String url,cid,url2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrply);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=findViewById(R.id.list3);
        url ="http://"+sh.getString("ip", "") + ":5000/viewreply";
        RequestQueue queue = Volley.newRequestQueue(viewrply.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    complaint= new ArrayList<>();
                    date= new ArrayList<>();
                    reply= new ArrayList<>();
                    c_id=new ArrayList<>();
                    station=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        complaint.add(jo.getString("complaint"));
                        station.add(jo.getString("station"));
                        date.add(jo.getString("date"));
                        reply.add(jo.getString("reply"));
                        c_id.add(jo.getString("c_id"));




                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom_3(viewrply.this,complaint,station,date,reply));
                    l1.setOnItemClickListener(viewrply.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewrply.this, "err"+error, Toast.LENGTH_SHORT).show();
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
        cid=c_id.get(position);
        AlertDialog.Builder ald=new AlertDialog.Builder(viewrply.this);
        ald.setTitle("Select option")
                .setPositiveButton(" Delete ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        RequestQueue queue = Volley.newRequestQueue(viewrply.this);
                        url2 = "http://" + sh.getString("ip","") + ":5000/delcomplaint";

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.d("+++++++++++++++++", response);
                                try {
                                    JSONObject json = new JSONObject(response);
                                    String res = json.getString("task");

                                    if (res.equalsIgnoreCase("success")) {
                                        Toast.makeText(viewrply.this, "success ", Toast.LENGTH_SHORT).show();

                                        Intent ik = new Intent(getApplicationContext(), home.class);
                                        startActivity(ik);

                                    } else {

                                        Toast.makeText(viewrply.this, "Failed ", Toast.LENGTH_SHORT).show();

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
                                params.put("c_id", cid);

                                return params;
                            }
                        };
                        queue.add(stringRequest);



                    }
                })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent ik = new Intent(getApplicationContext(), home.class);
                        startActivity(ik);




                    }
                });

        AlertDialog al=ald.create();
        al.show();





    }
}