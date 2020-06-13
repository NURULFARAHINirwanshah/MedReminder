package com.psm.medreminder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.psm.medreminder.activity.IconTabsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MedicineAdd extends AppCompatActivity{

    public static  EditText etUserid, etName, etDosage, etFrequency, etIndication, etSdate, etEdate;
    Button btnAddMed, buttonScan;
    DatePickerDialog datePickerDialog;
    SharedPreferences shared;
    String url;

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_add);

        //intializing scan object
        qrScan = new IntentIntegrator(this);
        buttonScan = findViewById(R.id.buttonScan);

        etUserid = findViewById(R.id.etUserId);
        etName = findViewById(R.id.etName);
        etDosage = findViewById(R.id.etDosage);
        etFrequency = findViewById(R.id.etFrequency);
        etIndication = findViewById(R.id.etIndication);
        etSdate = findViewById(R.id.etSdate);
        etEdate = findViewById(R.id.etEdate);
        btnAddMed = findViewById(R.id.btnAddMed);

        shared = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        String userid = shared.getString("id","");
        etUserid.setText(userid);

        etSdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etSdate.setFocusableInTouchMode(true);
                etSdate.requestFocus();

                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(MedicineAdd.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etSdate.setText(dayOfMonth + "-" + (month+1) + "-" + year);
                       // etSdate.setText(year + "/" +(month+1) + "/" +dayOfMonth);
                    }

                }, year, month, day);
                datePickerDialog.show();
            }
        });

        etEdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etEdate.setFocusableInTouchMode(true);
                etEdate.requestFocus();

                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(MedicineAdd.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etEdate.setText(dayOfMonth + "-" + (month+1) + "-" + year);
                       // etEdate.setText(year + "/" +(month+1) + "/" +dayOfMonth);

                    }

                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnAddMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_id = etUserid.getText().toString();
                String m_name = etName.getText().toString();
                String m_dosage = etDosage.getText().toString();
                String m_frequency = etFrequency.getText().toString();
                String m_indication = etIndication.getText().toString();
                String m_sdate = etSdate.getText().toString();
                String m_edate = etEdate.getText().toString();

                if(user_id.isEmpty()||m_name.isEmpty()||m_dosage.isEmpty()||m_frequency.isEmpty()||
                        m_indication.isEmpty()||m_sdate.isEmpty()||m_edate.isEmpty()){

                    Toast.makeText(MedicineAdd.this, "Fill all details", Toast.LENGTH_SHORT).show();
                }else {

                    btnAddMed(user_id,m_name,m_dosage,m_frequency,m_indication,m_sdate,m_edate);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("id",user_id);
                    editor.putString("scientific_name",m_name);
                    editor.putString("dosage",m_dosage);
                    editor.putString("schedule",m_frequency);
                    editor.putString("indication",m_indication);
                    editor.putString("s_date",m_sdate);
                    editor.putString("e_date",m_edate);
                    editor.commit();
                    show();

                }
            }
        });

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();

            }
        });

    }

    private void btnAddMed(final String user_id, final String m_name, final String m_dosage, final String m_frequency, final String m_indication, final String m_sdate, final String m_edate) {

        url = "http://192.168.43.207/medreminder/medicine-add.php?id="+user_id+"";

        RequestQueue requestQueue = Volley.newRequestQueue(MedicineAdd.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Hitesh",""+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(MedicineAdd.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("id",user_id);
                stringMap.put("scientific_name",m_name);
                stringMap.put("dosage",m_dosage);
                stringMap.put("schedule",m_frequency);
                stringMap.put("indication",m_indication);
                stringMap.put("s_date",m_sdate);
                stringMap.put("e_date",m_edate);
                return stringMap;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void show() {

        Intent intent = new Intent(MedicineAdd.this, IconTabsActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    etName.setText(obj.getString("name"));
                    etDosage.setText(obj.getString("dose"));
                    etFrequency.setText(obj.getString("freq"));
                    etIndication.setText(obj.getString("indication"));
                    etSdate.setText(obj.getString("sdate"));
                    etEdate.setText(obj.getString("edate"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
