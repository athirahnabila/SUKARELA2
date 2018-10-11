package com.example.robin.sukarela.event;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.robin.sukarela.R;
import com.example.robin.sukarela.adapter.TaskAdapter;
import com.example.robin.sukarela.model.ItemTask;

import java.util.ArrayList;
import java.util.List;


public class TaskFragment extends Fragment implements AdapterView.OnItemClickListener {

    private TaskFragment.OnFragmentInteractionListener mListener;
    TaskAdapter adapter;

    public TaskFragment() {
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
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        List<ItemTask> list = new ArrayList<>();
        list.add(new ItemTask("Packing", "Volunteer akan bantu packing barang-barang asas ini ke dalam kotak"));
        list.add(new ItemTask("Mengedarkan", "Volunteer akan bantu mengedarkan barang-barang keperluan asas kepada keluarga penerima bantuan."));

        ListView listView = view.findViewById(R.id.lvTask);
        adapter = new TaskAdapter(getActivity(), list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

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
        if (context instanceof TaskFragment.OnFragmentInteractionListener) {
            mListener = (TaskFragment.OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context, "choose ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void update() {
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }
}

