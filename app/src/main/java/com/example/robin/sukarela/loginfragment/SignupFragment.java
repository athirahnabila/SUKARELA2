package com.example.robin.sukarela.loginfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.robin.sukarela.MainActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.ProfileModel;
import com.example.robin.sukarela.utility.SmsCodeDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;


public class SignupFragment extends Fragment implements View.OnClickListener {

    // declare child view
    private MaterialEditText field_name, field_phone;
    private Button button_submit;

    // firebase
    FirebaseFirestore firestore;


    public SignupFragment() {
        // Required empty public constructor

        // firebase
        firestore = FirebaseFirestore.getInstance();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize child view
        field_name = view.findViewById(R.id.signup_field_name);
        field_phone = view.findViewById(R.id.signup_field_phone);
        button_submit = view.findViewById(R.id.signup_button_submit);

        // setup button_submit
        button_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        button_submit.setEnabled(false);

        final String name = field_name.getText().toString().trim();
        final String phone = field_phone.getText().toString().trim();

        SmsCodeDialog dialog = new SmsCodeDialog(getActivity(), phone) {

            @Override
            public void success(FirebaseUser user) {
                super.success(user);

                ProfileModel model = new ProfileModel();
                model.setName(name);
                model.setContact(phone);
                model.setAge(45);

                firestore
                        .collection("users")
                        .document(user.getUid())
                        .set(model)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (getActivity() != null){
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            }
                        });
            }

            @Override
            public void denied() {
                super.denied();

                button_submit.setEnabled(true);
            }
        };

        dialog.show();
    }
}
