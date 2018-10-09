package com.example.robin.sukarela.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.robin.sukarela.event.ContactFragment;
import com.example.robin.sukarela.event.DetailsFragment;
import com.example.robin.sukarela.event.TaskFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    private ContactFragment contactFragment;
    private DetailsFragment detailsFragment;
    private TaskFragment taskFragment;

    public DetailAdapter(FragmentManager fm) {
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
        detailsFragment = new DetailsFragment();
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
}
