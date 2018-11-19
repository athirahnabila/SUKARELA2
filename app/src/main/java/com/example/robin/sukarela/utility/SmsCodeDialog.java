package com.example.robin.sukarela.utility;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.robin.sukarela.R;
import com.github.glomadrian.codeinputlib.CodeInput;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SmsCodeDialog implements View.OnClickListener {

    private static final String TAG = "SmsCodeDialog";

    // declare component
    private Activity activity;
    private String phone;

    // declare view
    private View root;
    private CodeInput codeInput;
    private Button submit;

    // firebase
    private FirebaseAuth auth;
    private Task<AuthResult> task;

    protected SmsCodeDialog(Activity activity, String phone) {
        // initialize component
        this.activity = activity;
        this.phone = phone;

        // initialize view
        root = LayoutInflater.from(activity).inflate(R.layout.dialog_login, null);
        codeInput = root.findViewById(R.id.dialog_login_code);
        submit = root.findViewById(R.id.dialog_login_button);
        submit.setOnClickListener(this);

        // firebase
        auth = FirebaseAuth.getInstance();
    }

    public void show() {

        if (root != null) {

            if (task != null) {

                if (!task.isComplete()) {
                    Toast.makeText(activity, "Please wait...", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            BottomSheetDialog dialog = new BottomSheetDialog(activity);
            dialog.setContentView(root);
            dialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        submit.setEnabled(false);

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                // confirmation code
                String code = "";

                for (Character character : codeInput.getCode()){
                    code = code + character;
                }

                Log.i(TAG, "onCodeSent: " + code);

                if (code.isEmpty()) return;

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(s, code);

                // sign in
                task = auth.signInWithCredential(credential);
                task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            assert task.getResult() != null;
                            success(task.getResult().getUser());
                        } else {
                            denied();
                        }

                        submit.setEnabled(true);
                        task = null;
                    }
                });
            }
        };

        // "+60 18-968 4066"
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, activity, mCallbacks);
    }

    public void success(FirebaseUser user){

    }

    public void denied(){

    }
}
