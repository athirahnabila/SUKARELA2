package com.example.robin.sukarela.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskVH> {

    private static final String TAG = "TaskAdapter";


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
        EventModel event = MainActivity.EVENT_MAP.get(EventActivity.event_uid);
        TaskModel task = EventActivity.TASKS.get(i);

        if (event != null) {
            boolean status_join = event.isStatus();

            // fill task data
            taskVH.title.setText(task.getTitle());
            taskVH.description.setText(task.getDescription());

            // button select task avalaible to joined user for this event
            if (status_join) taskVH.select.setVisibility(View.VISIBLE);
            else taskVH.select.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return EventActivity.TASKS.size();
    }


    class TaskVH extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;

        Button select;


        private TaskVH(@NonNull View view) {
            super(view);

            title = view.findViewById(R.id.taskitem_text_title);
            description = view.findViewById(R.id.taskitem_text_description);

            select = view.findViewById(R.id.taskitem_button_select);
        }
    }
}
