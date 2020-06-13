package com.psm.medreminder.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.psm.medreminder.MedicineAdapter2;
import com.psm.medreminder.MedicineAdd;
import com.psm.medreminder.MedicineData2;
import com.psm.medreminder.ProfileEdit;
import com.psm.medreminder.R;
import com.psm.medreminder.SigninActivity;
import com.psm.medreminder.activity.IconTabsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TwoFragment extends Fragment implements MedicineAdapter2.OnMedicineListener {

    EditText tvpickdate;
    private Calendar calendar;
    private int year, month, day;
    DatePickerDialog datePickerDialog;

    public static final String st_id = "id";
    public static final String st_name = "name";
    public static final String st_sname = "scientific_name";
    public static final String st_dosage = "dosage";
    public static final String st_time = "time";
    public static final String st_date = "date";
    public static final String st_status = "status";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<MedicineData2> medicineDataList;
    private RecyclerView.Adapter adapter;

    SharedPreferences shared;
    String uid;
    String pickdate;
    String url;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//
////                Intent intent = new Intent(getActivity(), MedicineAdd.class);
////                startActivity(intent);
//            }
//        });

        recyclerView = (RecyclerView) view.findViewById(R.id.rvMedication);
        medicineDataList = new ArrayList<>();
        adapter = new MedicineAdapter2(getContext().getApplicationContext(), medicineDataList, this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        shared = getContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        uid = shared.getString("id", "");

        tvpickdate = (EditText) view.findViewById(R.id.tvPickDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        tvpickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvpickdate.setFocusableInTouchMode(true);
                tvpickdate.requestFocus();

                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvpickdate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        // etEdate.setText(year + "/" +(month+1) + "/" +dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        tvpickdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String date = (String)s.toString();
                try{

                    medicineDataList.clear();
                    adapter.notifyDataSetChanged();

                    retrieveDataJSON(uid,date);

                }catch (Exception e)
                {

                }
            }
        });
        pickdate = tvpickdate.getText().toString();

        retrieveDataJSON(uid, pickdate);

        return view;
    }

    private void showDate(int year, int month, int day) {
        tvpickdate.setText(new StringBuilder().append(day).append("-").append(month).append("-").append(year));
    }


    private void retrieveDataJSON(String uid, String pickdate) {

        final String url = "http://192.168.43.207/medreminder/medicine-status-view.php?id=" + uid + "&date=" + pickdate + "";

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Retrieving Data, Please Wait");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
//                      JSONArray jsonArray = response.getJSONArray(i);
//                      for (int j = 0;j<jsonArray.length();j++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        MedicineData2 medicineData = new MedicineData2();
                        medicineData.setSt_id(jsonObject.getString("id"));
                        medicineData.setSt_name(jsonObject.getString("name"));
                        medicineData.setSt_sname(jsonObject.getString("scientific_name"));
                        medicineData.setSt_dosage(jsonObject.getString("dosage"));
                        medicineData.setSt_time(jsonObject.getString("time"));
                        medicineData.setSt_date(jsonObject.getString("date"));
                        medicineData.setSt_status(jsonObject.getString("status"));
                        medicineData.setSt_mid(jsonObject.getString("mid"));
                        medicineData.setSt_sid(jsonObject.getString("sid"));
                        medicineData.setSt_uid(jsonObject.getString("uid"));
                        medicineData.setYear(jsonObject.optInt("year"));
                        medicineData.setMonth(jsonObject.optInt("month"));
                        medicineData.setDay(jsonObject.optInt("day"));
                        medicineData.setHour(jsonObject.optInt("hour"));
                        medicineData.setMinute(jsonObject.optInt("minute"));
                        medicineDataList.add(medicineData);
//                         }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onMedicineListener(int position) {
        final MedicineData2 medicineData = medicineDataList.get(position);
//        Intent intent = new Intent(getActivity(), TwoFragment.class);
//        intent.putExtra(st_id, medicineData.getSt_id());
//        intent.putExtra(st_name, medicineData.getSt_name());
//        intent.putExtra(st_sname, medicineData.getSt_sname());
//        intent.putExtra(st_dosage, medicineData.getSt_dosage());
//        intent.putExtra(st_time, medicineData.getSt_time());
//        intent.putExtra(st_date, medicineData.getSt_date());
//        intent.putExtra(st_status, medicineData.getSt_status());
//        startActivity(intent);

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final EditText mid = alertLayout.findViewById(R.id.mid);
        final EditText sid = alertLayout.findViewById(R.id.sid);
        final EditText uid = alertLayout.findViewById(R.id.uid);
        final EditText date = alertLayout.findViewById(R.id.date);

//        final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);

        mid.setText(medicineData.getSt_mid());
        sid.setText(medicineData.getSt_sid());
        uid.setText(medicineData.getSt_uid());
        date.setText(medicineData.getSt_date());

//        cbToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    // to encode password in dots
//                    etEmail.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                } else {
//                    // to display the password in normal text
//                    etEmail.setTransformationMethod(null);
//                }
//            }
//        });

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Update Status");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String mid2 = mid.getText().toString();
                String sid2 = sid.getText().toString();
                String uid2 = uid.getText().toString();
                String date2 = date.getText().toString();

                updStatus(mid2,sid2,uid2,date2);

                medicineDataList.clear();
                adapter.notifyDataSetChanged();

                retrieveDataJSON(uid2,date2);

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    //                String user = etUsername.getText().toString();
//                String pass = etEmail.getText().toString();
//                Toast.makeText(getActivity(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();

    //                mid.setText(medicineData.getSt_mid());
//                sid.setText(medicineData.getSt_sid());
//                uid.setText(medicineData.getSt_uid());
//                date.setText(medicineData.getSt_date());
    //                Toast.makeText(getActivity(), ""+mid2+""+sid2+""+uid2+""+date2, Toast.LENGTH_SHORT).show();
    private void updStatus(final String mid2, final String sid2, final String uid2, final String date2) {

        url = "http://192.168.43.207/medreminder/medicine-status-update.php?m_id="+mid2+"&s_id="+sid2+"&u_id="+uid2+"&date="+date2+"";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Hitesh",""+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("m_id",mid2);
                stringMap.put("s_id",sid2);
                stringMap.put("u_id",uid2);
                stringMap.put("date",date2);
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }

}
