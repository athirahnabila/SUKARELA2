package com.example.robin.sukarela;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.robin.sukarela.main.HomeFragment;
import com.example.robin.sukarela.main.JoinFragment;
import com.example.robin.sukarela.main.ProfileFragment;
import com.example.robin.sukarela.model.ItemEvent;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private BottomNavigationView mBottomBar;
    private FragmentManager mFragmentManager;

    // activity fragments
    HomeFragment homeFragment = new HomeFragment();
    JoinFragment joinFragment = new JoinFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    String description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.include_toolbar);
        mBottomBar = findViewById(R.id.main_bottombar);
        mFragmentManager = getSupportFragmentManager();

        String image_1 = "https://firebasestorage.googleapis.com/v0/b/sukarelaapp.appspot.com/o/test%2Falberto-santiago-1102994-unsplash.jpg?alt=media&token=8434c45d-b024-4b83-9308-e57207d16be5";
        String image_2 = "https://firebasestorage.googleapis.com/v0/b/sukarelaapp.appspot.com/o/test%2Fgades-photography-540975-unsplash.jpg?alt=media&token=aeb3cb35-b288-4a2f-a080-2dbd29c539b0";
        String image_3 = "https://firebasestorage.googleapis.com/v0/b/sukarelaapp.appspot.com/o/test%2Fhudson-hintze-183959-unsplash.jpg?alt=media&token=18c32396-535f-46bc-92df-e5906e4e6bec";
        String image_4 = "https://firebasestorage.googleapis.com/v0/b/sukarelaapp.appspot.com/o/test%2Fprasad-sn-767846-unsplash.jpg?alt=media&token=b9ee157c-5845-4af2-a2d4-6bece2fd44d1";
        String image_5 = "https://firebasestorage.googleapis.com/v0/b/sukarelaapp.appspot.com/o/test%2Falberto-santiago-1102994-unsplash.jpg?alt=media&token=8434c45d-b024-4b83-9308-e57207d16be5";

        ItemEvent.EVENTS.add(new ItemEvent(image_1, "Ekplorasi Deria", description, "10 Mac 2016"));
        ItemEvent.EVENTS.add(new ItemEvent(image_2, "Selamatkan Encik Belang", description, "29 Feb 2018"));
        ItemEvent.EVENTS.add(new ItemEvent(image_3, "Agihan Banjir", description, "10 Apr 2018"));
        ItemEvent.EVENTS.add(new ItemEvent(image_4, "Bantuan hari Raya", description, "10 Nov 2017"));
        ItemEvent.EVENTS.add(new ItemEvent(image_5, "Apa apa je", description, "10 Jan 2019"));
    }

    @Override
    protected void onStart() {
        super.onStart();

        initUI();
    }

    private void initUI() {
        setSupportActionBar(mToolbar);
        updateFragment(homeFragment);

        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        updateFragment(homeFragment);
                        break;

                    case R.id.action_bookmark:
                        updateFragment(joinFragment);
                        break;

                    case R.id.action_profile:
                        updateFragment(profileFragment);
                        break;
                }

                // always consume
                return true;
            }
        });
    }

    private void updateFragment(Fragment fragment) {
        if (fragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
        }
    }
}
