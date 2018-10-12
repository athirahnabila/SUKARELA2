package com.example.robin.sukarela;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.robin.sukarela.adapter.LoginAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    CoordinatorLayout mRoot;

    CircleImageView mImage_profile;

    TabLayout mTab;
    ViewPager mPager;
    LoginAdapter mAdapter;

    Button mButton_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRoot = findViewById(R.id.login_root);

        mImage_profile = findViewById(R.id.login_image_profile);

        mTab = findViewById(R.id.login_tab);
        mPager = findViewById(R.id.login_pager);
        mAdapter = new LoginAdapter(getSupportFragmentManager());

        mButton_submit = findViewById(R.id.login_button_submit);
        mButton_submit.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initUI();
    }

    @Override
    public void onClick(View v) {
        Snackbar snackbar = Snackbar.make(mRoot, "Wrong password", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void initUI(){
        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);
    }
}
