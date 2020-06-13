package com.psm.medreminder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.psm.medreminder.activity.IconTabsActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends Activity {

    EditText birthdate1;
    TextInputLayout name, birthdate, email, pswd, cpswd, weight, height, bp, disease;
    private RadioGroup rgSex;
    private RadioButton rbSex;
    DatePickerDialog datePickerDialog;
    TextView signup, signin;
    String url = "http://192.168.43.207/medreminder/signup.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        name =  findViewById(R.id.tilName);
        birthdate = findViewById(R.id.tilBirthDate);
        email = findViewById(R.id.tilEmail);
        pswd = findViewById(R.id.tilPswd);
        cpswd = findViewById(R.id.tilCPswd);
        weight = findViewById(R.id.tilWeight);
        height = findViewById(R.id.tilHeight);
        bp = findViewById(R.id.tilBP);
        disease = findViewById(R.id.tilDisease);
        rgSex =(RadioGroup)findViewById(R.id.radioSex);


        birthdate1 = findViewById(R.id.birthdate1);
        birthdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                birthdate1.setFocusableInTouchMode(true);
                birthdate1.requestFocus();

            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            datePickerDialog = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener(){

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    birthdate1.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                }

            }, year, month, day);
             datePickerDialog.show();
            }
        });


        signin = (TextView) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = rgSex.getCheckedRadioButtonId();
                rbSex = (RadioButton)findViewById(selectedId);


                String p_name = name.getEditText().getText().toString();
                String p_gender = rbSex.getText().toString();
                String p_bdate = birthdate.getEditText().getText().toString();
                String p_email = email.getEditText().getText().toString();
                String p_pswd = pswd.getEditText().getText().toString();
                String p_cpswd = cpswd.getEditText().getText().toString();
                String p_weight = weight.getEditText().getText().toString();
                String p_height = height.getEditText().getText().toString();
                String p_bp = bp.getEditText().getText().toString();
                String p_disease = disease.getEditText().getText().toString();

                if(p_name.isEmpty()||p_gender.isEmpty()||p_bdate.isEmpty()||p_email.isEmpty()||p_pswd.isEmpty()||p_cpswd.isEmpty()||p_weight.isEmpty()||p_height.isEmpty()||p_bp.isEmpty()||p_disease.isEmpty()){

                    Toast.makeText(SignupActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                }else {

                    signup(p_name,p_gender,p_bdate,p_email,p_pswd,p_weight,p_height,p_bp,p_disease);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("name",p_name);
                    editor.putString("gender",p_gender);
                    editor.putString("birthdate",p_bdate);
                    editor.putString("email",p_email);
                    editor.putString("password",p_pswd);
                    editor.putString("weight",p_weight);
                    editor.putString("height",p_height);
                    editor.putString("bloodpressure",p_bp);
                    editor.putString("disease",p_disease);
                    editor.commit();
                    show();

                }
            }
        });

    }


    public void show(){

        Intent intent = new Intent(SignupActivity.this, IconTabsActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void signup(final String name, final String gender, final String birthdate,final String email, final String password,final String weight, final String height, final String bp, final String disease){

        RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Hitesh",""+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(SignupActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("name",name);
                stringMap.put("gender",gender);
                stringMap.put("birthdate",birthdate);
                stringMap.put("email",email);
                stringMap.put("password",password);
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
