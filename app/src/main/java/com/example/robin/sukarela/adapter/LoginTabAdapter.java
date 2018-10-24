package com.example.robin.sukarela.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.robin.sukarela.login.SignupFragment;
import com.example.robin.sukarela.login.SigninFragment;

import java.util.ArrayList;
import java.util.List;

public class LoginTabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public LoginTabAdapter(FragmentManager fm) {
        super(fm);

        SigninFragment signinFragment = new SigninFragment();
        SignupFragment signupFragment = new SignupFragment();

        mFragments.add(signinFragment);
        mFragments.add(signupFragment);

        mTitles.add("sign in");
        mTitles.add("sign up");
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
