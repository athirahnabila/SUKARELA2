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

        ItemEvent.EVENTS.add(new ItemEvent(R.drawable.pic1, "Ekplorasi Deria", description, "10 Mac 2018"));
        ItemEvent.EVENTS.add(new ItemEvent(R.drawable.pic2, "Ekplorasi Deria", description, "10 Mac 2018"));
        ItemEvent.EVENTS.add(new ItemEvent(R.drawable.pic3, "Ekplorasi Deria", description, "10 Mac 2018"));
        ItemEvent.EVENTS.add(new ItemEvent(R.drawable.pic4, "Ekplorasi Deria", description, "10 Mac 2018"));
        ItemEvent.EVENTS.add(new ItemEvent(R.drawable.pic5, "Ekplorasi Deria", description, "10 Mac 2018"));
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
