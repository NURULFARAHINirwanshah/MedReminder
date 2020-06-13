package com.psm.medreminder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.psm.medreminder.fragments.ThreeFragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileEdit extends AppCompatActivity {

   public static EditText etId, etName, etGender, etBdate, etEmail, etWeight, etHeight, etBP, etDisease;
    Button btnSaveInfo;
    DatePickerDialog datePickerDialog;
    SharedPreferences shared;
    String url;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        etId = (EditText)findViewById(R.id.etId);
        etName = (EditText)findViewById(R.id.etName);
        etGender = (EditText)findViewById(R.id.etGender);
        etBdate = (EditText)findViewById(R.id.etBdate);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etWeight = (EditText)findViewById(R.id.etWeight);
        etHeight = (EditText)findViewById(R.id.etHeight);
        etBP = (EditText)findViewById(R.id.etBP);
        etDisease = (EditText)findViewById(R.id.etDisease);

        shared = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

        String id = shared.getString("id","");
        String name = shared.getString("name","");
        String gender = shared.getString("gender","");
        String bdate = shared.getString("birthdate", "");
        String email = shared.getString("email", "");
        String weight = shared.getString("weight", "");
        String height = shared.getString("height", "");
        String bp = shared.getString("bloodpressure", "");
        String disease = shared.getString("disease", "");
        etId.setText(id);
        etName.setText(name);
        etGender.setText(gender);
        etBdate.setText(bdate);
        etEmail.setText(email);
        etWeight.setText(weight);
        etHeight.setText(height);
        etBP.setText(bp);
        etDisease.setText(disease);


//        etBdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                etBdate.setFocusableInTouchMode(true);
//                etBdate.requestFocus();
//
//                final Calendar calendar = Calendar.getInstance();
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                int month = calendar.get(Calendar.MONTH);
//                int year = calendar.get(Calendar.YEAR);
//
//                datePickerDialog = new DatePickerDialog(ProfileEdit.this, new DatePickerDialog.OnDateSetListener(){
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        etBdate.setText(dayOfMonth + "/" + (month+1) + "/" + year);
//                    }
//
//                }, year, month, day);
//                datePickerDialog.show();
//            }
//        });

        btnSaveInfo = (Button) findViewById(R.id.btnSaveInfo);
        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*    Intent intent = new Intent(ProfileEdit.this, MainActivity.class);
                startActivity(intent); */

                String id = etId.getText().toString();
                String name = etName.getText().toString();
                String gender = etGender.getText().toString();
                String bdate = etBdate.getText().toString();
                String email = etEmail.getText().toString();
                String weight = etWeight.getText().toString();
                String height = etHeight.getText().toString();
                String bp = etBP.getText().toString();
                String disease = etDisease.getText().toString();

                if(name.isEmpty() ||gender.isEmpty()||bdate.isEmpty()||email.isEmpty()||weight.isEmpty()||height.isEmpty()||bp.isEmpty()||disease.isEmpty()){

                    Toast.makeText(ProfileEdit.this, "Fill all details", Toast.LENGTH_SHORT).show();
                }else {

                    btnSaveInfo(id,name,gender,bdate,email,weight,height,bp,disease);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("id",id);
                    editor.putString("name",name);
                    editor.putString("gender",gender);
                    editor.putString("birthdate",bdate);
                    editor.putString("email",email);
                    editor.putString("weight",weight);
                    editor.putString("height",height);
                    editor.putString("bloodpressure",bp);
                    editor.putString("disease",disease);
                    editor.commit();
                    show();

                }
            }
        });

    }

    private void show() {
        Intent intent = new Intent(ProfileEdit.this, ThreeFragment.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void btnSaveInfo(final String id, final String name, final String gender, final String bdate, final String email, final String weight, final String height, final String bp, final String disease) {

        url = "http://192.168.43.207/medreminder/profile-edit.php?id="+id+"";
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileEdit.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Hitesh",""+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(ProfileEdit.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("id",id);
                stringMap.put("name",name);
                stringMap.put("gender",gender);
                stringMap.put("birthdate",bdate);
                stringMap.put("email",email);
                stringMap.put("weight",weight);
                stringMap.put("height",height);
                stringMap.put("bloodpressure",bp);
                stringMap.put("disease",disease);
                return stringMap;
            }
        };

        requestQueue.add(stringRequest);

    }
}
