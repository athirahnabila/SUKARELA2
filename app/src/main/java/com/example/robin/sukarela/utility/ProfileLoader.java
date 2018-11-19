package com.example.robin.sukarela.utility;

import android.support.annotation.NonNull;

import com.example.robin.sukarela.model.ProfileModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileLoader {

    // declare component
    private String uid;

    // firebase
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    public ProfileLoader(String uid) {
        this.uid = uid;
    }

    public void get() {

        if (uid == null || uid.isEmpty()) {

            failed("Please put proper uid!");
            return;
        }

        DocumentReference reference = firestore.collection("users").document(uid);
        reference
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        DocumentSnapshot snapshot = task.getResult();

                        if (task.isSuccessful()){

                            if (snapshot != null){
                                success(snapshot.toObject(ProfileModel.class));
                            }
                        } else {
                            failed("Cannot find the user!");
                        }
                    }
                });
    }

    public void success(ProfileModel model) {

    }

    public void failed(String message) {

    }
}
