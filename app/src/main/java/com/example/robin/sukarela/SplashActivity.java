package com.example.robin.sukarela;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       // final EditText etUsername = (EditText) findViewById(R.id.etPhone);
        Button bRegister = (Button) findViewById(R.id.bRegister);
        Button bLogin = (Button) findViewById(R.id.bLogin);

        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent loginIntent=new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(loginIntent);
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent=new Intent(SplashActivity.this, RegisterActivity.class);
                SplashActivity.this.startActivity(registerIntent);
            }
        });
    }

}
