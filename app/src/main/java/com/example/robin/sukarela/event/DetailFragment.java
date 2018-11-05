package com.example.robin.sukarela.event;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.MainActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.EventModel;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class DetailFragment extends Fragment {

    private ImageView image_event;

    private TextView text_description;
    private TextView text_date;
    private TextView text_time;
    private TextView text_location;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        updateUI(MainActivity.EVENT_MAP.get(EventActivity.event_uid));
    }

    private void initUI(View view) {
        image_event = view.findViewById(R.id.image_event);
        text_description = view.findViewById(R.id.text_description);
        text_date = view.findViewById(R.id.text_date_event);
        text_time = view.findViewById(R.id.text_time_event);
        text_location = view.findViewById(R.id.text_location);
    }

    private void updateUI(EventModel event) {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH);

        Glide
                .with(image_event.getContext())
                .load(event.getImage())
                .into(image_event);

        text_description.setText(event.getDescription());
        text_date.setText(date.format(event.getEnd()));
        text_time.setText(time.format(event.getEnd()));
        text_location.setText(event.getLocation());
    }
}
