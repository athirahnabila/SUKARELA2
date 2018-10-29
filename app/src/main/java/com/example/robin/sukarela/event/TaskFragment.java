package com.example.robin.sukarela.event;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.adapter.TaskItemAdapter;
import com.example.robin.sukarela.model.ItemTask;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static android.support.constraint.Constraints.TAG;


public class TaskFragment extends Fragment implements EventListener<QuerySnapshot> {

    // datas
    List<ItemTask> mTasks;
    LinearLayoutManager mLinearLayout;
    DividerItemDecoration mDecoration;

    // adapters
    TaskItemAdapter mAdapter;

    // views
    RecyclerView mRecyclerView;

    // firebases
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    ListenerRegistration mListenerRegistration;


    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove annoying message
        assert getContext() != null;

        // initiliaze datas
        String description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";

        mTasks = new ArrayList<>();
        ItemTask task1 = new ItemTask("Memasak", description);
        ItemTask task2 = new ItemTask("Mengemas", description);
        ItemTask task3 = new ItemTask("Membina", description);
        ItemTask task4 = new ItemTask("Memberi", description);
        mTasks.add(task1);
        mTasks.add(task2);
        mTasks.add(task3);
        mTasks.add(task4);


        mLinearLayout = new LinearLayoutManager(getContext());
        mDecoration = new DividerItemDecoration(getContext(), mLinearLayout.getOrientation());

        // initiliaze adapters
        mAdapter = new TaskItemAdapter(mTasks);

        mListenerRegistration = createTaskListener(EventActivity.event_uid);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        // initialize child views
        mRecyclerView = view.findViewById(R.id.task_recyclerview);

        // setup fragment
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.addItemDecoration(mDecoration);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mListenerRegistration != null) {
            mListenerRegistration.remove();
        }
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
                mAdapter.notifyDataSetChanged();

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

