package com.example.robin.sukarela.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.ItemTask;

import java.util.List;

public class TaskItemAdapter extends ArrayAdapter<ItemTask> {

    private Context mContext;
    private List<ItemTask> mList;

    public TaskItemAdapter(Context context, List<ItemTask> events) {
        super(context, R.layout.item_task, events);

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
        ItemTask task = mList.get(position);

        // create item view holder
        TaskItemAdapter.ViewHolder vh = new TaskItemAdapter.ViewHolder();

        if (convertView == null) {
            // create view for first time
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(R.layout.item_task, parent, false);
            convertView.setTag(vh);

            vh.textTask = convertView.findViewById(R.id.task_text_title);
            vh.textDesc = convertView.findViewById(R.id.task_text_description);
            vh.button_select = convertView.findViewById(R.id.task_button_select);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.textTask.setText(task.getTitle());
        vh.textDesc.setText(task.getDescription());

        if (EventActivity.join) {
            vh.button_select.setVisibility(View.VISIBLE);
        } else {
            vh.button_select.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {

        TextView textTask;
        TextView textDesc;
        Button button_select;
    }
}

