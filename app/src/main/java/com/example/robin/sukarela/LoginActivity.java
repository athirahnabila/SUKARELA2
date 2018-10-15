package com.example.robin.sukarela;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.robin.sukarela.adapter.LoginAdapter;
import com.rengwuxian.materialedittext.MaterialEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    CoordinatorLayout mRoot;

    CircleImageView mImage_profile;

    TabLayout mTab;
    ViewPager mPager;
    LoginAdapter mAdapter;

    Button mButton_submit;

    int page = 0;

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
    }

    @Override
    protected void onStart() {
        super.onStart();

        initUI();
    }

    @Override
    public void onClick(View v) {
        View view = getLayoutInflater().inflate(R.layout.dialog_login, null);
        Button button_verify = view.findViewById(R.id.dialog_login_button);
        button_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();

//        BottomSheetBehavior behavior = BottomSheetBehavior.from((View) view.getParent());
//        behavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
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
}
