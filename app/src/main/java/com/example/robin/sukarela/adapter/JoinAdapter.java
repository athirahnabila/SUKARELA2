package com.example.robin.sukarela.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.robin.sukarela.joinfragment.FinishFragment;
import com.example.robin.sukarela.joinfragment.OngoingFragment;

import java.util.ArrayList;
import java.util.List;

public class JoinAdapter extends FragmentStatePagerAdapter {

    // declare component
    private List<String> titles;
    private List<Fragment> fragments;


    public JoinAdapter(FragmentManager fm) {
        super(fm);

        // initialize component
        titles = new ArrayList<>();
        fragments = new ArrayList<>();

        // attach fragment onto fragments
        fragments.add(new OngoingFragment());
        fragments.add(new FinishFragment());

        // attach title onto titles
        titles.add("Ongoing");
        titles.add("Finish");
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
