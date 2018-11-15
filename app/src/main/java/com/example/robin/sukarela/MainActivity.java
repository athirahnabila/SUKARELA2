package com.example.robin.sukarela;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.robin.sukarela.adapter.EventAdapter;
import com.example.robin.sukarela.mainfragment.HomeFragment;
import com.example.robin.sukarela.mainfragment.JoinFragment;
import com.example.robin.sukarela.mainfragment.ProfileFragment;
import com.example.robin.sukarela.model.EventModel;
import com.example.robin.sukarela.model.ProfileModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements EventAdapter.OnItemClick {

    private static final String TAG = "MainActivity";

    // declare static component
    public static final Map<String, EventModel> EVENT_MAP = new HashMap<>();
    public static final EventAdapter EVENT_ADAPTER = new EventAdapter();

    // activity fragment
    HomeFragment homeFragment = new HomeFragment();
    JoinFragment joinFragment = new JoinFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    // declare child view
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    // declare firebase
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    ListenerRegistration listenerRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize view
        toolbar = findViewById(R.id.include_toolbar);
        bottomNavigationView = findViewById(R.id.main_bottombar);
        fragmentManager = getSupportFragmentManager();

        // setup ui
        initUI();

        // start listen to available event
        listenerRegistration = firestore
                .collection("/events")
                .orderBy("start") // from latest to old event
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

                                            // cast data snapshot into data model
                                            EventModel eventModel = snapshot.toObject(EventModel.class);

                                            // add item
                                            EVENT_MAP.put(snapshot.getId(), eventModel);

                                            Log.i(TAG, "onEvent: " + eventModel);

                                            break;
                                        case REMOVED:
                                            // remove event using key id
                                            EVENT_MAP.remove(snapshot.getId());
                                            break;
                                    }
                                }

                                // update recycler view that use this adapter
                                EVENT_ADAPTER.notifyDataSetChanged();
                                EventActivity.TASK_ADAPTER.notifyDataSetChanged();

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

        updateProfile();
        updateBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (listenerRegistration != null) {
            listenerRegistration.remove();
            Log.i(TAG, "onDestroy: " + "remove events listener.");
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
                auth.signOut();

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

    @Override
    public void onItemClick(String uid) {
        Bundle bundle = new Bundle();
        bundle.putString("event_uid", uid);

        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void initUI() {
        // setup toolbar
        setSupportActionBar(toolbar);

        // setup default fragment
        updateFragment(homeFragment);

        // setup bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

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

        // setup EVENT_ADAPTER
        EVENT_ADAPTER.setOnItemClick(this);
    }

    private void updateFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
        }
    }

    private void updateProfile() {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            // get user profile data
            firestore
                    .collection("users")
                    .document(user.getUid())
                    .get()
                    .addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            DocumentSnapshot snapshot = task.getResult();

                            if (task.isSuccessful()) {

                                if (snapshot != null && snapshot.exists()) {
                                    ProfileModel profile = ProfileModel.USER_PROFILE;
                                    profile.setName(snapshot.getString("name"));
                                    profile.setContact(snapshot.getString("contact"));
                                    profile.setAge(10);

                                    updateBar();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Load user data fail!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void updateBar() {
        if (auth.getCurrentUser() == null) {
            bottomNavigationView.setVisibility(View.GONE);
            toolbar.setSubtitle("Sign in is needed");
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
            toolbar.setSubtitle("Welcome, " + ProfileModel.USER_PROFILE.getName() + "...");
        }
    }
}
