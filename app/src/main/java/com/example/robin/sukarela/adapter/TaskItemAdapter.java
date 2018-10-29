package com.example.robin.sukarela.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.ItemEvent;
import com.example.robin.sukarela.model.ItemTask;

import java.util.List;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.TaskVH> {

    private List<ItemTask> mTasks;


    public TaskItemAdapter(List<ItemTask> list) {
        this.mTasks = list;
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // item view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);

        return new TaskVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH taskVH, int i) {
        // require datas
        ItemEvent event = EventActivity.event;
        ItemTask task = mTasks.get(i);

        if (event != null) {
            boolean status_join = event.isJoining();

            // fill task data
            taskVH.title.setText(task.getTitle());
            taskVH.description.setText(task.getDescription());

            // button select task avalaible to joined user for this event
            if (status_join) taskVH.select.setVisibility(View.VISIBLE);
            else taskVH.select.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
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
