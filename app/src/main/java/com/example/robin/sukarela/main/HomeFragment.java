package com.example.robin.sukarela.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.adapter.EventAdapter;
import com.example.robin.sukarela.adapter.EventAdapterV2;
import com.example.robin.sukarela.model.ItemEvent;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    EventAdapterV2 mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    private OnFragmentInteractionListener mListener;

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
        mAdapter = new EventAdapterV2(ItemEvent.EVENTS);
        mRecyclerView = view.findViewById(R.id.home_recyclerview);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context, "Kill me: ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        Intent intent = new Intent(getActivity(), EventActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
