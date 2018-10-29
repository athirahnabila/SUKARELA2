package com.example.robin.sukarela;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.robin.sukarela.adapter.DetailTabAdapter;
import com.example.robin.sukarela.model.ItemEvent;
import com.example.robin.sukarela.utility.EventHelper;
import com.google.firebase.auth.FirebaseAuth;

public class EventActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    // static datas
    public static ItemEvent event;

    // adapters
    DetailTabAdapter mDetailTabAdapter;

    // views
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    // firebases
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mDetailTabAdapter = new DetailTabAdapter(getSupportFragmentManager());

        mTabLayout = findViewById(R.id.event_tab);
        mViewPager = findViewById(R.id.event_pager);
        mToolbar = findViewById(R.id.include_toolbar);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            event = EventHelper.get(bundle.getString("uid"));
            setTitle(event.getTitle());
        }

        // listener to auth
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setSupportActionBar(mToolbar);
        mViewPager.setAdapter(mDetailTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_option_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_join:

                // check user is needed to login or not to continue join a event
                if (mAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Can join", Toast.LENGTH_SHORT).show();
                }

                break;
        }

        return true;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

    }
}
