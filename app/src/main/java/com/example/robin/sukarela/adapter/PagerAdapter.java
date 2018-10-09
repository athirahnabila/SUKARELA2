package com.example.robin.sukarela.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.robin.sukarela.event.ContactFragment;
import com.example.robin.sukarela.event.DetailsFragment;
import com.example.robin.sukarela.event.TaskFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {


    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm , int NumberOfTabs){

        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                DetailsFragment df = new DetailsFragment();
                return df;
            case 1:
                TaskFragment tf = new TaskFragment();
                return tf;
            case 2:
                ContactFragment cf = new ContactFragment();
                return cf;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
