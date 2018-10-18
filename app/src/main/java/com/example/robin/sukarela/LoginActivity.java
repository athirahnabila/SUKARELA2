package com.example.robin.sukarela;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.robin.sukarela.adapter.LoginAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    CoordinatorLayout mRoot;

    CircleImageView mImage_profile;

    TabLayout mTab;
    ViewPager mPager;
    LoginAdapter mAdapter;

    Button mButton_submit;


    private static final String TAG = "LoginActivity";
    int page = 0;

    //firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRoot = findViewById(R.id.login_root);

        mImage_profile = findViewById(R.id.login_image_profile);

        mTab = findViewById(R.id.login_tab);
        mPager = findViewById(R.id.login_pager);
        mAdapter = new LoginAdapter(getSupportFragmentManager());

        mButton_submit = findViewById(R.id.login_button_submit);
        mButton_submit.setOnClickListener(this);

        // firebase
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initUI();
        updateUI(mAuth.getCurrentUser());
    }

    @Override
    public void onClick(View v) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_login, null);
        Button button_verify = view.findViewById(R.id.dialog_login_button);
        button_verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendCode();
            }
        });

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        // update page number
        page = i;

        switch (page) {
            case 0:
                mButton_submit.setText(R.string.text_signin);
                break;
            case 1:
                mButton_submit.setText(R.string.text_signup);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void initUI() {
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(this);
        mTab.setupWithViewPager(mPager);
    }

    private void sendCode() {
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Toast.makeText(LoginActivity.this, "Code receive : " + credential.getSmsCode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, "123456");
                mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            updateUI(mAuth.getCurrentUser());
                        }
                    }
                });
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+60 18-968 4066", 60, TimeUnit.SECONDS, LoginActivity.this, mCallbacks);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
