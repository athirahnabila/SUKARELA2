package com.example.robin.sukarela;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.robin.sukarela.adapter.LoginTabAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    // declare component
    LoginTabAdapter mAdapter;

    // declare view
    CoordinatorLayout root;
    CircleImageView image_profile;
    ImageView image_background;
    TabLayout tab;
    ViewPager pager;

    // firebase
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        root = findViewById(R.id.login_root);

        image_profile = findViewById(R.id.login_image_profile);
        image_background = findViewById(R.id.login_image_background);

        tab = findViewById(R.id.login_tab);
        pager = findViewById(R.id.login_pager);
        mAdapter = new LoginTabAdapter(getSupportFragmentManager());

        // firebase
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initUI();
        updateUI(mAuth.getCurrentUser());
    }

    private void initUI() {
        // setup pager
        pager.setAdapter(mAdapter);

        // setup tab
        tab.setupWithViewPager(pager);

        // setup image_background
        Glide
                .with(this)
                .load("https://images.unsplash.com/photo-1469571486292-0ba58a3f068b?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=e6daa4b6ab26a89c93cd9b84fee487fe&auto=format&fit=crop&w=1350&q=80")
                .into(image_background);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
