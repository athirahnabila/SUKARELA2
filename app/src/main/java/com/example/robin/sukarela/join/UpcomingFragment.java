package com.example.robin.sukarela.join;

import android.content.Context;
import android.content.Intent;
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

import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.adapter.EventAdapter;
import com.example.robin.sukarela.model.Event;

public class UpcomingFragment implements AdapterView.OnItemClickListener {

    private UpcomingFragment.OnFragmentInteractionListener mListener;

    String description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join, container, false);

        Event.EVENTS.add(new Event(R.drawable.pic1, "Ekplorasi Deria", description, "10 Mac 2018"));
        Event.EVENTS.add(new Event(R.drawable.pic2, "Ekplorasi Deria", description, "10 Mac 2018"));
        Event.EVENTS.add(new Event(R.drawable.pic3, "Ekplorasi Deria", description, "10 Mac 2018"));
        Event.EVENTS.add(new Event(R.drawable.pic4, "Ekplorasi Deria", description, "10 Mac 2018"));
        Event.EVENTS.add(new Event(R.drawable.pic5, "Ekplorasi Deria", description, "10 Mac 2018"));

        ListView listView = view.findViewById(R.id.listv);
        EventAdapter adapter = new EventAdapter(getActivity());

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
