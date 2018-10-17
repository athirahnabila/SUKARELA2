package com.example.robin.sukarela.main;


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

import com.example.robin.sukarela.R;
import com.example.robin.sukarela.adapter.JoinAdapter;
import com.example.robin.sukarela.model.ItemEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinFragment extends Fragment {

    RecyclerView mRecyclerView;

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

        JoinAdapter adapter = new JoinAdapter(ItemEvent.EVENTS);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        DividerItemDecoration decoration = new DividerItemDecoration(view.getContext(), manager.getOrientation());

        mRecyclerView = view.findViewById(R.id.join_recyclerview);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(decoration);
    }
}
