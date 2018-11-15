package com.example.robin.sukarela.mainfragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.robin.sukarela.R;
import com.example.robin.sukarela.adapter.JoinAdapter;


public class JoinFragment extends Fragment {

    // declare static component
    public static final JoinAdapter joinAdapter = new JoinAdapter();

    // declare child view
    private RecyclerView recyclerView;
    private Button button_1;
    private Button button_2;


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
        recyclerView = view.findViewById(R.id.join_recycler);
        button_1 = view.findViewById(R.id.join_button_1);
        button_2 = view.findViewById(R.id.join_button_2);

        initUI();
    }

    private void initUI(){
        joinAdapter.updateOngoing();

        // setup recyclerView
        assert getContext() != null;

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(joinAdapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), manager.getOrientation()));

        // setup buttons
        button_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                joinAdapter.updateFinish();
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                joinAdapter.updateOngoing();
            }
        });
    }
}
