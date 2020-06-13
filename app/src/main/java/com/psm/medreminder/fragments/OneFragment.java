package com.psm.medreminder.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.psm.medreminder.MainActivity;
import com.psm.medreminder.MedicineAdapter;
import com.psm.medreminder.MedicineAdd;
import com.psm.medreminder.MedicineData;
import com.psm.medreminder.MedicineInfo;
import com.psm.medreminder.QRscanner;
import com.psm.medreminder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OneFragment extends Fragment implements MedicineAdapter.OnMedicineListener{

    private IntentIntegrator qrScan;

    public static final String med_mid = "m_id";
    public static final String med_sname = "scientific_name";
    public static final String med_indication = "indication";
    public static final String med_dosage = "dosage";
    public static final String med_schedule = "schedule";
    public static final String med_date = "date";


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<MedicineData> medicineDataList;
    private RecyclerView.Adapter adapter;

   SharedPreferences shared;
   String uid;

    public OneFragment() {
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
        View view = inflater.inflate(R.layout.fragment_one, container, false);


        qrScan = new IntentIntegrator(getActivity());
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                qrScan.initiateScan();


          /*      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */

                Intent intent = new Intent(getActivity(), MedicineAdd.class);
                startActivity(intent);
            }
        });

   /*     Intent intent = new Intent(getActivity(), QRscanner.class);
        startActivity(intent); */

        recyclerView = (RecyclerView) view.findViewById(R.id.rvMedication);
        medicineDataList = new ArrayList<>();
        adapter = new MedicineAdapter(getContext().getApplicationContext(),medicineDataList, this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        shared = getContext().getSharedPreferences("Mypref",Context.MODE_PRIVATE);
        uid = shared.getString("id","");

        retrieveDataJSON(uid);

        return view;
    }

    private void retrieveDataJSON(String uid){

        final String url = "http://192.168.43.207/medreminder/medicine-view1.php?id="+uid;

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Retrieving Data, Please Wait");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                           // JSONArray jsonArray = response.getJSONArray(i);
                           // for (int j = 0;j<jsonArray.length();j++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                MedicineData medicineData = new MedicineData();
                                medicineData.setMed_mid(jsonObject.getString("m_id"));
                                medicineData.setMed_sname(jsonObject.getString("scientific_name"));
                                medicineData.setMed_indication(jsonObject.getString("indication"));
                                medicineData.setMed_dosage(jsonObject.getString("dosage"));
                                medicineData.setMed_schedule(jsonObject.getString("schedule"));
                                medicineData.setMed_date(jsonObject.getString("date"));
                                medicineDataList.add(medicineData);
                           // }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener(){
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
        MedicineData medicineData = medicineDataList.get(position);
//        Intent intent = new Intent(getActivity(), MedicineInfo.class);
//        intent.putExtra(med_mid, medicineData.getMed_mid());
//        intent.putExtra(med_sname, medicineData.getMed_sname());
//        intent.putExtra(med_indication, medicineData.getMed_indication());
//        intent.putExtra(med_dosage, medicineData.getMed_dosage());
//        intent.putExtra(med_schedule, medicineData.getMed_schedule());
//        intent.putExtra(med_date, medicineData.getMed_date());
//        startActivity(intent);

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog1, null);
        final TextView mid = alertLayout.findViewById(R.id.tvMid);
        final TextView sname = alertLayout.findViewById(R.id.tvSname);
        final TextView indication = alertLayout.findViewById(R.id.tvIndication);
        final TextView dosage = alertLayout.findViewById(R.id.tvDosage);
        final TextView frequency = alertLayout.findViewById(R.id.tvFrequency);
        final TextView date = alertLayout.findViewById(R.id.tvDate);

        mid.setText(medicineData.getMed_mid());
        sname.setText(medicineData.getMed_sname());
        indication.setText(medicineData.getMed_indication());
        dosage.setText(medicineData.getMed_dosage());
        frequency.setText(medicineData.getMed_schedule());
        date.setText(medicineData.getMed_date());

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Medicine Details");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
//        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String mid2 = mid.getText().toString();
                String sname2 = sname.getText().toString();
                String indication2 = indication.getText().toString();
                String dosage2 = dosage.getText().toString();
                String frequency2 = frequency.getText().toString();
                String date2 = date.getText().toString();


//                Toast.makeText(getActivity(), ""+mid2+""+sid2+""+uid2+""+date2, Toast.LENGTH_SHORT).show();

//                updStatus(mid2,sid2,uid2,date2);
//
//                medicineDataList.clear();
//                adapter.notifyDataSetChanged();

//                retrieveDataJSON(uid2,date2);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    QRscanner.textViewName.setText(obj.getString("name"));
                    QRscanner.textViewAddress.setText(obj.getString("address"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }

    }
}
