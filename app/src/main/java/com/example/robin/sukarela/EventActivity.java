package com.example.robin.sukarela;

import android.content.Intent;
import android.os.Bundle;
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

public class EventActivity extends AppCompatActivity {

    // static datas
    public static ItemEvent event;
    public static String event_uid;


    // adapters
    DetailTabAdapter mDetailTabAdapter;

    // views
    Toolbar mToolbar;
    ViewPager mViewPager;
    TabLayout mTabLayout;

    // firebases
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // initialize adapters
        mDetailTabAdapter = new DetailTabAdapter(getSupportFragmentManager());

        // initialize child views
        mTabLayout = findViewById(R.id.event_tab);
        mViewPager = findViewById(R.id.event_pager);
        mToolbar = findViewById(R.id.include_toolbar);

        // setup activity
        setSupportActionBar(mToolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            event_uid = bundle.getString("event_uid");
            event = EventHelper.get(event_uid);

            setTitle(event.getTitle());

            mViewPager.setAdapter(mDetailTabAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }
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
}
