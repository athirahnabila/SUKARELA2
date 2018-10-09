package com.example.robin.sukarela;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.robin.sukarela.adapter.DetailAdapter;
import com.example.robin.sukarela.event.ContactFragment;
import com.example.robin.sukarela.event.DetailsFragment;
import com.example.robin.sukarela.event.TaskFragment;
import com.example.robin.sukarela.main.HomeFragment;
import com.example.robin.sukarela.model.Event;

public class EventActivity extends AppCompatActivity implements DetailsFragment.OnFragmentInteractionListener, ContactFragment.OnFragmentInteractionListener, TaskFragment.OnFragmentInteractionListener {

    Event mEvent;
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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            mAdapter = new DetailAdapter(getSupportFragmentManager());
            mEvent = Event.EVENTS.get(bundle.getInt("position"));
        }

        if (mEvent != null) {
            setTitle(mEvent.getTitle());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        initUI();
    }

    private void initUI() {
        setSupportActionBar(mToolbar);

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
