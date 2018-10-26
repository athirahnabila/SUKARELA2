package com.example.robin.sukarela;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.robin.sukarela.adapter.EventItemAdapter;
import com.example.robin.sukarela.main.HomeFragment;
import com.example.robin.sukarela.main.JoinFragment;
import com.example.robin.sukarela.main.ProfileFragment;
import com.example.robin.sukarela.model.ItemEvent;
import com.example.robin.sukarela.utility.EventHelper;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    // adapters
    private static final String TAG = "MainActivity";
    public static final EventItemAdapter EVENT_ADAPTER = new EventItemAdapter();

    // activity fragments
    HomeFragment homeFragment = new HomeFragment();
    JoinFragment joinFragment = new JoinFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    // views
    private Toolbar mToolbar;
    private BottomNavigationView mBottomBar;
    private FragmentManager mFragmentManager;

    // firebases
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    ListenerRegistration mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // views
        mToolbar = findViewById(R.id.include_toolbar);
        mBottomBar = findViewById(R.id.main_bottombar);
        mFragmentManager = getSupportFragmentManager();

        // setup ui
        initUI();

        // start listen to available event
        mListener = mFirestore
                .collection("/events")
                .orderBy("date_posted") // from latest to old event
                .limit(10) // display only top 10 event
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        // document must not null
                        if (queryDocumentSnapshots != null) {

                            // checking numbers of event in events collection
                            if (!queryDocumentSnapshots.isEmpty()) {

                                for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
                                    // contain event data
                                    QueryDocumentSnapshot snapshot = change.getDocument();

                                    // checking which type of changes happen
                                    switch (change.getType()) {

                                        // add and modify is same operation
                                        case ADDED:
                                        case MODIFIED:

                                            // new event container
                                            ItemEvent event = new ItemEvent();

                                            // process data
                                            String title = snapshot.getString("title");
                                            String description = snapshot.getString("description");
                                            String image = snapshot.getString("image");
                                            Timestamp date_posted = snapshot.getTimestamp("date_posted");
                                            Timestamp date_event = snapshot.getTimestamp("date_event");
                                            Object joins = snapshot.get("join_list");

                                            // check joined status to current login user
                                            if (mAuth.getCurrentUser() != null) {

                                                // doing some datatype filtering
                                                if (joins instanceof ArrayList) {
                                                    ArrayList join_list = (ArrayList) joins;

                                                    for (Object o : join_list) {
                                                        // checking object is string, it should string
                                                        // cheking user is a volunteer for this event or not
                                                        if (o instanceof String) {
                                                            String uid = (String) o;

                                                            // if user join this event
                                                            if (uid.equals(mAuth.getCurrentUser().getUid())) {
                                                                event.setStatus_joins(true);

                                                                break;
                                                            } else {
                                                                event.setStatus_joins(false);
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            // remove annoying error message
                                            assert date_posted != null;
                                            assert date_event != null;

                                            // attach data
                                            event.setTitle(title);
                                            event.setDescription(description);
                                            event.setImage(image);
                                            event.setDate_posted(date_posted.toDate());
                                            event.setDate_event(date_event.toDate());

                                            // add item
                                            EventHelper.add(snapshot.getId(), event);

                                            break;
                                        case REMOVED:
                                            EventHelper.remove(snapshot.getId());
                                            break;
                                    }
                                }

                                // update recycler view that use this adapter
                                EVENT_ADAPTER.notifyDataSetChanged();

                            } else {
                                Log.i(TAG, "onEvent: " + "no event receive.");
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            mBottomBar.setVisibility(View.GONE);
        } else {
            mBottomBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mListener != null) {
            mListener.remove();
            Log.i(TAG, "onDestroy: " + "success remove events listener.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_option_signout:

                // firebase logout
                mAuth.signOut();

                // go to login activity
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();

                break;
            case R.id.main_option_about:
                break;
        }

        return true;
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
