package com.example.robin.sukarela.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.robin.sukarela.eventfragment.ContactFragment;
import com.example.robin.sukarela.eventfragment.DetailFragment;
import com.example.robin.sukarela.eventfragment.TaskFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailTabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    private ContactFragment contactFragment;
    private DetailFragment detailsFragment;
    private TaskFragment taskFragment;

    public DetailTabAdapter(FragmentManager fm) {
        super(fm);

        initFragment();

        mFragments = new ArrayList<>();
        mFragments.add(detailsFragment);
        mFragments.add(taskFragment);
        mFragments.add(contactFragment);

        mTitles = new ArrayList<>();
        mTitles.add("Detail");
        mTitles.add("Task");
        mTitles.add("Contact");
    }

    private void initFragment(){
        contactFragment = new ContactFragment();
        detailsFragment = new DetailFragment();
        taskFragment = new TaskFragment();
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

    public TaskFragment getTaskFragment() {
        return taskFragment;
    }
}
