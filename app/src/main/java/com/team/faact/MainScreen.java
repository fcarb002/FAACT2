package com.team.faact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.*;

public class MainScreen extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        final EditText loginUser = findViewById(R.id.loginUser);
        final EditText loginPassword = findViewById(R.id.loginPassword);
        final TextView invalidText = findViewById(R.id.invalidText);
        invalidText.setVisibility(View.INVISIBLE);
        Button logIN = findViewById(R.id.logIN);
        logIN.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent myIntent;
                if ((loginUser.getText().toString().equals("admin")) && (loginPassword.getText().toString().equals("password"))) {
                    myIntent = new Intent(MainScreen.this, MainScreen2.class);
                    MainScreen.this.startActivity(myIntent);
                    MainScreen.this.finish();
                }
                else
                    invalidText.setVisibility(View.VISIBLE);
            }
        });
    }
}
