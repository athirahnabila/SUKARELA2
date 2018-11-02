package com.example.robin.sukarela.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.MainActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.EventModel;
import com.example.robin.sukarela.model.TaskModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskVH> {

    // firebase
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // item view
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);

        return new TaskVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH taskVH, int i) {
        // require datas
        String task_uid = (String) EventActivity.TASK_MODEL_MAP.keySet().toArray()[i];
        EventModel event = MainActivity.EVENT_MAP.get(EventActivity.event_uid);
        TaskModel task = EventActivity.TASK_MODEL_MAP.get(task_uid);

        if (event != null) {
            boolean status_join = event.isStatus();

            // fill task data
            taskVH.title.setText(task.getTitle());
            taskVH.description.setText(task.getDescription());

            // button select task avalaible to joined user for this event
            if (status_join) taskVH.select.setVisibility(View.VISIBLE);
            else taskVH.select.setVisibility(View.INVISIBLE);

            // if user select a task, button of that task will disable
            boolean status = event.getTask_assign().containsKey(auth.getUid()) && event.getTask_assign().get(auth.getUid()).equals(task_uid);

            if (event.getJoin_list() != null) taskVH.select.setEnabled(!status);
        }
    }

    @Override
    public int getItemCount() {
        return EventActivity.TASK_MODEL_MAP.size();
    }


    class TaskVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView description;

        Button select;


        private TaskVH(@NonNull View view) {
            super(view);

            title = view.findViewById(R.id.taskitem_text_title);
            description = view.findViewById(R.id.taskitem_text_description);

            select = view.findViewById(R.id.taskitem_button_select);
            select.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final DocumentReference doc = firestore.collection("events").document(EventActivity.event_uid);

            firestore.runTransaction(new Transaction.Function<EventModel>() {

                @Nullable
                @Override
                public EventModel apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                    // read database
                    EventModel eventModel = transaction.get(doc).toObject(EventModel.class);

                    if (eventModel != null) {
                        String task_uid = (String) EventActivity.TASK_MODEL_MAP.keySet().toArray()[getAdapterPosition()];
                        Map<String, String> map = eventModel.getTask_assign();

                        // create new map object if get map is null
                        if (map == null) map = new HashMap<>();

                        map.put(auth.getUid(), task_uid);

                        // wrapper
                        Map<String, Object> data = new HashMap<>();
                        data.put("task_assign", map);

                        transaction.update(doc, data);
                    }

                    return null;
                }
            });
        }
    }
}
