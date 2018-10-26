package com.example.robin.sukarela.utility;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Date;

public class Helper {

    private static final String TAG = "Helper";
    private static int counter = 0;

    public static String readableDate(Date date) {
        String formated = "";

        return formated;
    }

    public static void joinEvent(String uid) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DocumentReference docEvent = FirebaseFirestore.getInstance().collection("events").document(uid);

        FirebaseFirestore.getInstance().runTransaction(new Transaction.Function<String>() {
            @Nullable
            @Override
            public String apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(docEvent);
                Object o = snapshot.get("join_list");

                if (o != null) {
                    ArrayList data = (ArrayList) o;
                    data.add("maria" + counter);
                    counter++;

                    transaction.update(docEvent, "join_list", data);
                }

                return null;
            }
        });

        // user must not null
        if (user != null) {
            // register user id into event document
        }
    }
}
