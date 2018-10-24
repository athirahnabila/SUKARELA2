package com.example.robin.sukarela.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.ItemProfile;


public class ProfileFragment extends Fragment {

    // views
    EditText mEditText_name;
    EditText mEditText_age;
    EditText mEditText_contact;

    // data
    ItemProfile profile = ItemProfile.USER_PROFILE;


    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mEditText_name = view.findViewById(R.id.profile_field_name);
        mEditText_age = view.findViewById(R.id.profile_field_age);
        mEditText_contact = view.findViewById(R.id.profile_field_contact);

        mEditText_name.setText(profile.getName());
        mEditText_age.setText(profile.getAge());
        mEditText_contact.setText(profile.getContact());

        return view;
    }
}
