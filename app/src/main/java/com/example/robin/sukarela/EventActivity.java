package com.example.robin.sukarela;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.robin.sukarela.adapter.DetailTabAdapter;
import com.example.robin.sukarela.adapter.TaskItemAdapter;
import com.example.robin.sukarela.model.ItemEvent;
import com.example.robin.sukarela.model.ItemTask;
import com.example.robin.sukarela.utility.EventHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class EventActivity extends AppCompatActivity implements EventListener<QuerySnapshot> {

    // static datas
    public static ItemEvent event;
    public static String event_uid;
    public static MenuItem menuItem;
    public static final List<ItemTask> TASKS = new ArrayList<>();
    public static final TaskItemAdapter ADAPTER = new TaskItemAdapter(TASKS);
    private static final String TAG = "EventActivity";

    // adapters
    DetailTabAdapter mDetailTabAdapter;

    // views
    Toolbar mToolbar;
    ViewPager mViewPager;
    TabLayout mTabLayout;

    // firebases
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    ListenerRegistration mListenerRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // initialize adapters
        mDetailTabAdapter = new DetailTabAdapter(getSupportFragmentManager());

        // initialize child views
        mTabLayout = findViewById(R.id.event_tab);
        mViewPager = findViewById(R.id.event_pager);
        mToolbar = findViewById(R.id.include_toolbar);

        // initiliaze datas
        String description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";

        TASKS.clear();
        ItemTask task1 = new ItemTask("Memasak", description);
        ItemTask task2 = new ItemTask("Mengemas", description);
        ItemTask task3 = new ItemTask("Membina", description);
        ItemTask task4 = new ItemTask("Memberi", description);
        TASKS.add(task1);
        TASKS.add(task2);
        TASKS.add(task3);
        TASKS.add(task4);

        // setup activity
        setSupportActionBar(mToolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            event_uid = bundle.getString("event_uid");
            event = EventHelper.get(event_uid);

            setTitle(event.getTitle());

            mViewPager.setAdapter(mDetailTabAdapter);
            mTabLayout.setupWithViewPager(mViewPager);

            mListenerRegistration = createTaskListener(event_uid);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mListenerRegistration != null) {
            mListenerRegistration.remove();
            Log.i(TAG, "onDestroy: " + "remove task listener.");
        }

        menuItem = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_option_menu, menu);

        // need to to use later
        menuItem = menu.getItem(0);

        if (EventHelper.get(event_uid).isJoining()){
            menuItem.setTitle(R.string.text_cancel);
        } else {
            menuItem.setTitle(R.string.text_join);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_join:

                ItemEvent updated = EventHelper.get(event_uid);

                // check user is needed to login or not to continue join a event
                if (mAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    // get event document to join
                    final DocumentReference doc = mFirestore
                            .collection("events")
                            .document(event_uid);

                    if (updated.isJoining()) {

                        // transaction for cancel joining session
                        mFirestore.runTransaction(new Transaction.Function<Void>() {

                            @android.support.annotation.Nullable
                            @Override
                            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                                // read event document
                                DocumentSnapshot snapshot = transaction.get(doc);

                                // get event field join_list
                                Object o = snapshot.get("join_list");

                                if (o instanceof ArrayList) {
                                    ArrayList join_list = (ArrayList) o;

                                    String uid = mAuth.getUid();

                                    // delete user id from join list
                                    join_list.remove(uid);

                                    // update event document
                                    transaction.update(doc, "join_list", join_list);
                                }

                                return null;
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Transaction success!");
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Transaction failure.", e);
                                    }
                                });

                    } else {
                        // transaction for cancel joining session
                        mFirestore.runTransaction(new Transaction.Function<Void>() {

                            @android.support.annotation.Nullable
                            @Override
                            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                                // read event document
                                DocumentSnapshot snapshot = transaction.get(doc);

                                // get event field join_list
                                Object o = snapshot.get("join_list");

                                if (o instanceof ArrayList) {
                                    ArrayList join_list = (ArrayList) o;

                                    String uid = mAuth.getUid();

                                    // to avoid duplicate user
                                    if (!join_list.contains(uid)) {
                                        join_list.add(uid);
                                    }

                                    // update event document
                                    transaction.update(doc, "join_list", join_list);
                                }

                                return null;
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Transaction success!");
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Transaction failure.", e);
                                    }
                                });
                    }
                }

                break;
        }

        return true;
    }

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


                            break;
                        case REMOVED:

                            break;
                    }
                }

                // update recycler view that use this adapter
                ADAPTER.notifyDataSetChanged();

            } else {
                Log.i(TAG, "onEvent: " + "no event receive.");
            }
        }
    }

    private ListenerRegistration createTaskListener(String event_uid) {
        return mFirestore
                .collection("events")
                .document(event_uid)
                .collection("tasks")
                .addSnapshotListener(this);
    }
}
