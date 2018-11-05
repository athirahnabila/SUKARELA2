package com.example.robin.sukarela.event;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.robin.sukarela.R;


public class ContactFragment extends Fragment {

    // declare child view
    ImageView imageView;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        imageView = view.findViewById(R.id.contact_image);
        Glide
                .with(this)
                .load("https://mvm.org.my/wp-content/uploads/2016/01/logo-mvm.png")
                .into(imageView);

        return view;
    }
}
