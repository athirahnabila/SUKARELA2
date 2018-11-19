package com.example.robin.sukarela.loginfragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.robin.sukarela.R;
import com.example.robin.sukarela.utility.SmsCodeDialog;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;


public class SigninFragment extends Fragment implements View.OnClickListener {

    // declare view
    private MaterialEditText field_phone;
    private Button button_submit;


    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize view
        field_phone = view.findViewById(R.id.signin_field_phone);
        button_submit = view.findViewById(R.id.signin_button_submit);

        // setup button_submit
        button_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone = field_phone.getText().toString().trim();

        if (phone == null || phone.isEmpty()) {
            Toast.makeText(getContext(), "Please enter contact number", Toast.LENGTH_SHORT).show();
            return;
        }

        SmsCodeDialog dialog = new SmsCodeDialog(getActivity(), phone){
            @Override
            public void success(FirebaseUser user) {
                super.success(user);
            }

            @Override
            public void denied() {
                super.denied();
                Toast.makeText(getContext(), "Verification is fail!", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
