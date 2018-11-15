package com.example.robin.sukarela.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.MainActivity;
import com.example.robin.sukarela.R;


public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;


    public HomeFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration decoration = new DividerItemDecoration(view.getContext(), mLayoutManager.getOrientation());
        mRecyclerView = view.findViewById(R.id.home_recyclerview);

        // setup recyler view
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(MainActivity.EVENT_ADAPTER);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        Intent intent = new Intent(getActivity(), EventActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
