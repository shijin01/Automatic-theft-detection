package com.example.automaticthiefdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class reg extends AppCompatActivity {
    EditText fname,lname,dob,place,post,pin,phone,email,usrnm,pswd;
    Button b1;
    RadioButton r1,r2;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        fname=findViewById(R.id.editTextTextPersonName3);
        lname=findViewById(R.id.editTextTextPersonName4);
        dob=findViewById(R.id.editTextTextPersonName5);
        place=findViewById(R.id.editTextTextPersonName6);
        post=findViewById(R.id.editTextTextPersonName7);
        pin=findViewById(R.id.editTextTextPersonName8);
        phone=findViewById(R.id.editTextTextPersonName9);
        email=findViewById(R.id.editTextTextPersonName10);
        usrnm=findViewById(R.id.editTextTextPersonName11);
        pswd=findViewById(R.id.editTextTextPersonName16);
        b1=findViewById(R.id.button3);
        r1=findViewById(R.id.radioButton3);
        r2=findViewById(R.id.radioButton4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fn=fname.getText().toString();
                final String ln=lname.getText().toString();
                final String db=dob.getText().toString();
                final String pl=place.getText().toString();
                final String pn=pin.getText().toString();
                final String pot=post.getText().toString();
                final String phn= phone.getText().toString();
                final String eml=email.getText().toString();
                final String usn=usrnm.getText().toString();
                final String psd=pswd.getText().toString();
                final String gender;
                if(r1.isChecked())
                {
                    gender=r1.getText().toString();
                }
                else {
                    gender=r2.getText().toString();

                }
                if (fn.equalsIgnoreCase(""))
                {
                    fname.setError("Enter first name");
                }
                else if(ln.equalsIgnoreCase(""))
                {
                    lname.setError("Enter last name");
                }
                else if(db.equalsIgnoreCase(""))
                {
                    dob.setError("Enter date of birth");
                }

                else if(pl.equalsIgnoreCase(""))
                {
                    place.setError("Enter Your Place");
                }
                else if(pot.equalsIgnoreCase(""))
                {
                    post.setError("Enter Your Post");
                }
                else if(pn.equalsIgnoreCase(""))
                {
                    pin.setError("Enter Your pin");
                }
                else if(phn.equalsIgnoreCase(""))
                {
                    phone.setError("Enter Your Phone no:");
                }
                else if(phn.length()<10)
                {
                    phone.setError("Minimum 10 nos required");
                    phone.requestFocus();
                }

                else if(eml.equalsIgnoreCase(""))
                {
                    email.setError("Enter Your Email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(eml).matches())
                {
                    email.setError("Enter Valid Email");
                    email.requestFocus();
                }
                else if(usn.equalsIgnoreCase(""))
                {
                    usrnm.setError("Enter Your username");
                }
                else if(psd.equalsIgnoreCase(""))
                {
                    pswd.setError("Enter Your password");
                }
                else{
                RequestQueue queue = Volley.newRequestQueue(reg.this);
                url = "http://" + sh.getString("ip","") + ":5000/userreg";

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

                                Intent ik = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(ik);

                            } else {

                                Toast.makeText(reg.this, "Failed ", Toast.LENGTH_SHORT).show();

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
                        params.put("firstname", fn);
                        params.put("lastname", ln);
                        params.put("gender",gender);
                        params.put("dob", db);
                        params.put("place", pl);
                        params.put("post", pn);
                        params.put("pin",pot );
                        params.put("phone", phn);
                        params.put("email", eml);
                        params.put("username", usn);
                        params.put("password", psd);
                        return params;
                    }
                };
                queue.add(stringRequest);



            }}
        });
    }
}