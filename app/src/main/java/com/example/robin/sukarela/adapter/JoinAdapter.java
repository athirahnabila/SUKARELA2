package com.example.robin.sukarela.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.robin.sukarela.join.PreviousFragment;
import com.example.robin.sukarela.join.UpcomingFragment;

import java.util.ArrayList;
import java.util.List;

public class JoinAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    private UpcomingFragment upcomingFragment;
    private PreviousFragment previousFragment;

    public JoinAdapter(FragmentManager fm) {
        super(fm);

        initFragment();

        mFragments = new ArrayList<>();
        mFragments.add(upcomingFragment);
        mFragments.add(previousFragment);

        mTitles = new ArrayList<>();
        mTitles.add("Upcoming Event");
        mTitles.add("Previous Event");
    }

    private void initFragment(){
        upcomingFragment = new UpcomingFragment();
        previousFragment = new PreviousFragment();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
