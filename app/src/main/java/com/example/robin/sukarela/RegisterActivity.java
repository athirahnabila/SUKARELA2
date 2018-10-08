package com.example.robin.sukarela;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etPhone = (EditText) findViewById(R.id.etPhone);
        final EditText etUsername = (EditText) findViewById(R.id.etPhone);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
    }
}
