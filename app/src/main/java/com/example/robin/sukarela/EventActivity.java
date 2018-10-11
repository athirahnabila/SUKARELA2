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

import com.example.robin.sukarela.adapter.DetailAdapter;
import com.example.robin.sukarela.model.ItemEvent;

public class EventActivity extends AppCompatActivity {

    public static ItemEvent event;
    public static boolean join;

    DetailAdapter mAdapter;

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mTabLayout = findViewById(R.id.event_tab);
        mViewPager = findViewById(R.id.event_pager);
        mToolbar = findViewById(R.id.include_toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_join:

                if (event != null) {
                    join = !join;

                    if (join) {
                        item.setTitle(R.string.text_cancel);
                    } else {
                        item.setTitle(R.string.text_join);
                    }

                    mAdapter.getTaskFragment().update();
                }
                break;
        }

        return true;
    }

    private void initUI() {
        setSupportActionBar(mToolbar);

        join = false;

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        if (bundle != null) {

            event = ItemEvent.EVENTS.get(bundle.getInt("position"));
            setTitle(event.getTitle());

            mAdapter = new DetailAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }
}
