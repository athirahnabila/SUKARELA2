package com.example.robin.sukarela;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.robin.sukarela.adapter.DetailTabAdapter;
import com.example.robin.sukarela.adapter.TaskAdapter;
import com.example.robin.sukarela.model.EventModel;
import com.example.robin.sukarela.model.TaskModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class EventActivity extends AppCompatActivity implements EventListener<QuerySnapshot> {

    // declare static component
    private static final String TAG = "EventActivity";
    public static final Map<String, TaskModel> TASK_MODEL_MAP = new HashMap<>();
    public static final TaskAdapter TASK_ADAPTER = new TaskAdapter();

    // declare component
    public static String event_uid;
    private MenuItem menuItem;

    // declare adapter
    DetailTabAdapter detailTabAdapter;

    // declare view
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    // firebase
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    ListenerRegistration listenerRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // initialize adapter
        detailTabAdapter = new DetailTabAdapter(getSupportFragmentManager());

        // initialize child view
        tabLayout = findViewById(R.id.event_tab);
        viewPager = findViewById(R.id.event_pager);
        toolbar = findViewById(R.id.include_toolbar);

        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_option_menu, menu);

        // need to to use later
        menuItem = menu.getItem(0);

        updateUI(MainActivity.EVENT_MAP.get(event_uid));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_join:

                if (auth.getCurrentUser() == null) {
                    // for guest
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    showDialogTask();
                }
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
        // document must not null
        if (queryDocumentSnapshots != null) {

            // checking numbers of event in events collection
            if (!queryDocumentSnapshots.isEmpty()) {

                for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
                    // snapshot and task
                    QueryDocumentSnapshot snapshot = change.getDocument();
                    TaskModel taskModel = snapshot.toObject(TaskModel.class);

                    // checking which type of changes happen
                    switch (change.getType()) {

                        // add and modify is same operation
                        case ADDED:
                        case MODIFIED:

                            TASK_MODEL_MAP.put(snapshot.getId(), taskModel);

                            break;
                        case REMOVED:

                            TASK_MODEL_MAP.remove(snapshot.getId());

                            break;
                    }
                }

                // update recycler view that use this adapter
                TASK_ADAPTER.notifyDataSetChanged();

            } else {
                Log.i(TAG, "onEvent: " + "no event receive.");
            }
        }
    }

    private void initUI() {
        // setup TASK_MODEL_MAP
        TASK_MODEL_MAP.clear();

        // setup activity
        setSupportActionBar(toolbar);

        // get pass bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            event_uid = bundle.getString("event_uid");
            EventModel event = MainActivity.EVENT_MAP.get(event_uid);

            setTitle(event.getTitle());

            listenerRegistration = createTaskListener(event_uid);
        }

        // setup viewPager
        viewPager.setAdapter(detailTabAdapter);

        // setup tabLayout
        tabLayout.setupWithViewPager(viewPager);
    }

    private void updateUI(@NonNull EventModel event) {

        menuItem.setTitle(event.isJoined() ? R.string.text_cancel : R.string.text_join);
    }

    private void showDialogTask() {
        EventModel model = MainActivity.EVENT_MAP.get(event_uid);

        if (model.getStatus()){
            Toast.makeText(this, "Event already past!", Toast.LENGTH_SHORT).show();

            return;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(model.isJoined() ? R.string.dialog_message_cancel_task : R.string.dialog_message_do_task );
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                toggleJoiningTask();
            }
        });
        builder.setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void toggleJoiningTask(){
        // for user
        final DocumentReference doc = firestore
                .collection("events")
                .document(event_uid);

        firestore.runTransaction(new Transaction.Function<EventModel>() {

            @Override
            public EventModel apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                // user that wan to edit this status
                String user_uid = auth.getUid();

                // read database first
                EventModel event = transaction.get(doc).toObject(EventModel.class);

                // event must not null
                assert event != null;

                // check current selected action is join or cancel
                if (event.isJoined()) {
                    // is true, it means current action is cancel
                    event.getJoin_list().remove(user_uid);

                } else {
                    // caution step
                    if (event.getJoin_list().contains(user_uid)) return event;

                    // is false, it means current action is join
                    event.getJoin_list().add(user_uid);
                }

                // write database second
                transaction.update(doc, "join_list", event.getJoin_list());

                // return event
                return event;
            }
        })
                .addOnCompleteListener(new OnCompleteListener<EventModel>() {
                    @Override
                    public void onComplete(@NonNull Task<EventModel> task) {
                        EventModel eventModel = task.getResult();

                        // check complete on success or fail
                        if (task.isSuccessful()) {
                            assert eventModel != null;

                            updateUI(eventModel);
                            Log.i(TAG, "onComplete: Join event is " + eventModel.isJoined());
                        }
                    }
                });
    }

    private ListenerRegistration createTaskListener(String event_uid) {
        return firestore
                .collection("events")
                .document(event_uid)
                .collection("tasks")
                .addSnapshotListener(this);
    }
}
