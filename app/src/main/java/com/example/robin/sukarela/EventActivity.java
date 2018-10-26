package com.example.robin.sukarela;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.robin.sukarela.adapter.DetailTabAdapter;
import com.example.robin.sukarela.model.ItemEvent;
import com.example.robin.sukarela.utility.EventHelper;

public class EventActivity extends AppCompatActivity {

    // data
    public static ItemEvent event;

    // adapters
    DetailTabAdapter mAdapter;

    // views
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mAdapter = new DetailTabAdapter(getSupportFragmentManager());

        mTabLayout = findViewById(R.id.event_tab);
        mViewPager = findViewById(R.id.event_pager);
        mToolbar = findViewById(R.id.include_toolbar);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            event = EventHelper.get(bundle.getString("uid"));
            setTitle(event.getTitle());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        setSupportActionBar(mToolbar);
        mViewPager.setAdapter(mAdapter);
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

                break;
        }

        return true;
    }
}
