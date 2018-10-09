package com.example.robin.sukarela;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.robin.sukarela.event.ContactFragment;
import com.example.robin.sukarela.adapter.PagerAdapter;
import com.example.robin.sukarela.event.DetailsFragment;
import com.example.robin.sukarela.event.TaskFragment;

public class EventActivity extends AppCompatActivity implements DetailsFragment.OnFragmentInteractionListener,TaskFragment.OnFragmentInteractionListener,ContactFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            Toast.makeText(this, "position : " + bundle.getInt("position"), Toast.LENGTH_SHORT).show();
        }



        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("DETAIL"));
        tabLayout.addTab(tabLayout.newTab().setText("TASK"));
        tabLayout.addTab(tabLayout.newTab().setText("CONTACT"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
