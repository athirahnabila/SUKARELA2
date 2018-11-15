package com.example.robin.sukarela.mainfragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.robin.sukarela.R;
import com.example.robin.sukarela.adapter.JoinAdapter;


public class JoinFragment extends Fragment {

    // declare component
    JoinAdapter joinAdapter;

    // declare child view
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public JoinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize child view
        tabLayout = view.findViewById(R.id.join_tab);
        viewPager = view.findViewById(R.id.join_viewpager);

        // initialize component
        joinAdapter = new JoinAdapter(getFragmentManager());

        initUI();
    }

    private void initUI() {
        // setup viewPager
        viewPager.setAdapter(joinAdapter);

        // setup tabLayout
        tabLayout.setupWithViewPager(viewPager);
    }
}
