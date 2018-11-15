package com.example.robin.sukarela.eventfragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.R;


public class TaskFragment extends Fragment {

    // views
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayout;
    DividerItemDecoration mDecoration;


    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove annoying message
        assert getContext() != null;

        mLinearLayout = new LinearLayoutManager(getContext());
        mDecoration = new DividerItemDecoration(getContext(), mLinearLayout.getOrientation());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        // initialize child views
        mRecyclerView = view.findViewById(R.id.task_recyclerview);

        // setup recycler view
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.addItemDecoration(mDecoration);
        mRecyclerView.setAdapter(EventActivity.TASK_ADAPTER);

        return view;
    }
}

