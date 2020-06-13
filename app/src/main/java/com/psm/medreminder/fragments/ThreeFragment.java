package com.psm.medreminder.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.psm.medreminder.ProfileEdit;
import com.psm.medreminder.R;
import com.psm.medreminder.SigninActivity;
import com.psm.medreminder.SignupActivity;


public class ThreeFragment extends Fragment {

    TextView tvName, tvGender, tvBdate, tvEmail, tvWeight, tvHeight, tvBP, tvDisease, tvId;
    Button btnEditProfile;
    SharedPreferences shared;
    AlertDialog alertDialog;

    public ThreeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_three, container, false);


        tvId = (TextView)view.findViewById(R.id.tvid);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvGender = (TextView) view.findViewById(R.id.tvGender);
        tvBdate = (TextView) view.findViewById(R.id.tvBdate);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvWeight = (TextView) view.findViewById(R.id.tvWeight);
        tvHeight = (TextView) view.findViewById(R.id.tvHeight);
        tvBP = (TextView) view.findViewById(R.id.tvBP);
        tvDisease = (TextView) view.findViewById(R.id.tvDisease);

        shared = getContext().getSharedPreferences("Mypref",Context.MODE_PRIVATE);
        String id = shared.getString("id","");
        String name = shared.getString("name", "");
        String gender = shared.getString("gender", "");
        String bdate = shared.getString("birthdate", "");
        String email = shared.getString("email", "");
        String weight = shared.getString("weight", "");
        String height = shared.getString("height", "");
        String bp = shared.getString("bloodpressure", "");
        String disease = shared.getString("disease", "");
        tvId.setText(id);
        tvName.setText(name);
        tvGender.setText(gender);
        tvBdate.setText(bdate);
        tvEmail.setText(email);
        tvWeight.setText(weight);
        tvHeight.setText(height);
        tvBP.setText(bp);
        tvDisease.setText(disease);

        btnEditProfile = (Button)view.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProfileEdit.class);
                startActivity(intent);
            }
        });

        return view;

    }


}
