package com.example.robin.sukarela.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.Task;

import java.util.List;

public class TaskAdapter extends ArrayAdapter{



    private Context mContext;
    private List<Task> mList;

    public TaskAdapter(Context context, List<Task> events) {
        super(context, R.layout.item_task,events);

        mList = events;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // current item model
        Task task = mList.get(position);

        // create item view holder
        TaskAdapter.ViewHolder vh = new TaskAdapter.ViewHolder();

        if (convertView == null) {
            // create view for first time
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(R.layout.item_task, parent, false);
            vh.textTask = convertView.findViewById(R.id.tvTask);
            vh.textDesc = convertView.findViewById(R.id.tvDesc);


            vh.textTask.setText(task.getTitle());
            vh.textDesc.setText(task.getDescription());
        } else {
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(R.layout.item_task_title, parent, false);
            vh.textTask = convertView.findViewById(R.id.tvTitle);
            vh.textTask.setText(task.getTitle());
        }

        return convertView;
    }

    static class ViewHolder {

        TextView textTask;
        TextView textDesc;
    }
}

