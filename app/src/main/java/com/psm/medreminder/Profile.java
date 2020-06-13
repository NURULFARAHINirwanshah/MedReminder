package com.psm.medreminder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by csa on 03-Apr-17.
 */

public class Profile extends AppCompatActivity {


    TextView username1,pass1,email1,logout;
    SharedPreferences shared;
    AlertDialog alertdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        email1= (TextView) findViewById(R.id.email1);
        username1= (TextView) findViewById(R.id.username1);
        pass1= (TextView) findViewById(R.id.pass1);
        logout = (TextView) findViewById(R.id.logout);
        shared= getSharedPreferences("Mypref", Context.MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertdialog = new AlertDialog.Builder(Profile.this).create();

                alertdialog.setTitle("Logout");
                alertdialog.setMessage("Are you sure ! logout ?");
                alertdialog.setCancelable(false);
                alertdialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertdialog.dismiss();

                    }
                });

                alertdialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = shared.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(Profile.this, SigninActivity.class);
                        startActivity(intent);
                    }
                });
                alertdialog.show();
            }
        });

        String username = shared.getString("name", "");
        String email = shared.getString("email", "");
        String password = shared.getString("password", "");
        email1.setText(email);
        username1.setText(username);
        pass1.setText(password);



    }
}