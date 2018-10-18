package com.example.robin.sukarela;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.robin.sukarela.adapter.EventAdapter;
import com.example.robin.sukarela.main.HomeFragment;
import com.example.robin.sukarela.main.JoinFragment;
import com.example.robin.sukarela.main.ProfileFragment;
import com.example.robin.sukarela.model.ItemEvent;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    // adapters
    public static final EventAdapter EVENT_ADAPTER = new EventAdapter(ItemEvent.EVENTS);

    // activity fragments
    HomeFragment homeFragment = new HomeFragment();
    JoinFragment joinFragment = new JoinFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    // views
    private Toolbar mToolbar;
    private BottomNavigationView mBottomBar;
    private FragmentManager mFragmentManager;

    // firebase
    FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // views
        mToolbar = findViewById(R.id.include_toolbar);
        mBottomBar = findViewById(R.id.main_bottombar);
        mFragmentManager = getSupportFragmentManager();

        // firebase
        mFirestore = FirebaseFirestore.getInstance();

        // start listen to available event
        mFirestore.collection("/events").orderBy("date_posted").addSnapshotListener(this, new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (queryDocumentSnapshots != null) {

                    if (!queryDocumentSnapshots.isEmpty()) {

                        ItemEvent.EVENTS.clear();

                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {

                            // get event data
                            String title = snapshot.getString("title");
                            String description = snapshot.getString("description");
                            String image = snapshot.getString("image");
                            Timestamp date_posted = snapshot.getTimestamp("date_posted");
                            Timestamp date_event = snapshot.getTimestamp("date_event");

                            // remove annoying error message
                            assert date_posted != null;
                            assert date_event != null;

                            // create new event item
                            ItemEvent event = new ItemEvent();
                            event.setTitle(title);
                            event.setDescription(description);
                            event.setImage(image);
                            event.setDate_posted(date_posted.toDate());
                            event.setDate_event(date_event.toDate());

                            // add item
                            ItemEvent.EVENTS.add(event);
                        }

                        // update recycler view that use this adapter
                        EVENT_ADAPTER.notifyDataSetChanged();

                    }else{
                        Toast.makeText(MainActivity.this, "Nothing event to show.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
