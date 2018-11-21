package com.example.robin.sukarela.eventfragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.robin.sukarela.R;


public class ContactFragment extends Fragment implements View.OnClickListener {

    // declare view
    ImageView imageView;
    Button button_call, button_whatsapp;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize view
        imageView = view.findViewById(R.id.contact_image);
        button_call = view.findViewById(R.id.contact_button_call);
        button_whatsapp = view.findViewById(R.id.contact_button_whatsapp);

        // setup imageView
        Glide
                .with(this)
                .load("https://mvm.org.my/wp-content/uploads/2016/01/logo-mvm.png")
                .into(imageView);

        // setup button contact
        button_call.setOnClickListener(this);
        button_whatsapp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(button_call)) {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+60123456789"));
            startActivity(intent);

        } else if (v.equals(button_whatsapp)) {

            String url = "https://api.whatsapp.com/send?phone=+60123456789";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
    }
}
