package com.example.robin.sukarela.event;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.robin.sukarela.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.ivphone);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:8074 0434"));
                startActivity(intent);
            }
        });

        ImageView imageView2 = (ImageView) view.findViewById(R.id.ivws);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = "This is a test";// Replace with your message.

                String toNumber = "0193256424"; // Replace with mobile phone number without +Sign or leading zeros.


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
                startActivity(intent);

            }
        });

        ImageView imageView3 = (ImageView) view.findViewById(R.id.ivtele);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appName = "MuslimVolunteerMalaysia";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("www.telegram.me/" + appName));
                startActivity(intent);
            }
        });

        return view;
    }

}
