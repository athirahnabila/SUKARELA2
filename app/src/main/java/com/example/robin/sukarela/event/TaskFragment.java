package com.example.robin.sukarela.event;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.robin.sukarela.R;
import com.example.robin.sukarela.adapter.TaskItemAdapter;
import com.example.robin.sukarela.model.ItemTask;

import java.util.ArrayList;
import java.util.List;


public class TaskFragment extends Fragment {


    TaskItemAdapter adapter;

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
        adapter = new TaskItemAdapter(getActivity(), list);

        listView.setAdapter(adapter);

        return view;
    }

    public void update() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}

