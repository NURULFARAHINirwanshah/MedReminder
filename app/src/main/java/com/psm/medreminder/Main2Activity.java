package com.psm.medreminder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

//    public void buttonClicked(View view) {
//        LayoutInflater inflater = getLayoutInflater();
//        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
//        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
//        final EditText etEmail = alertLayout.findViewById(R.id.et_email);
//        final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);
//
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
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Update Status");
//        // this is set the view from XML inside AlertDialog
//        alert.setView(alertLayout);
//        // disallow cancel of AlertDialog on click of back button and outside touch
//        alert.setCancelable(false);
//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String user = etUsername.getText().toString();
//                String pass = etEmail.getText().toString();
//                Toast.makeText(getBaseContext(), "Name: " + user + " Status: " + pass, Toast.LENGTH_SHORT).show();
//            }
//        });
//        AlertDialog dialog = alert.create();
//        dialog.show();
//    }
}
