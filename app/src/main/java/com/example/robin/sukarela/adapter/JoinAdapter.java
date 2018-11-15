package com.example.robin.sukarela.adapter;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class JoinAdapter extends RecyclerView.Adapter<JoinAdapter.VH> {

    // declare component
    private List<EventModel> list;


    public JoinAdapter() {
        // initialize component
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_join, parent, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, int position) {
        final EventModel model = list.get(position);

        holder.title.setText(model.getTitle());
        holder.status.setText("Status : " + (model.getStatus() ? "Finish" : "Ongoing"));
        holder.detail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("event_uid", model.toString());

                Intent intent = new Intent(holder.itemView.getContext(), EventActivity.class);
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateFinish() {
        // clear list
        list.clear();

        for (EventModel model : MainActivity.EVENT_MAP.values()) {

            if (model.getStatus() && model.isJoined()) {
                list.add(model);
            }
        }

        Log.i("mytest", "updateFinish: " + list.size());

        notifyDataSetChanged();
    }

    public void updateOngoing() {
        // clear list
        list.clear();

        for (EventModel model : MainActivity.EVENT_MAP.values()) {

            if (!model.getStatus() && model.isJoined()) {
                list.add(model);
            }
        }

        Log.i("mytest", "updateOngoing: " + list.size());

        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView status;

        private Button detail;

        private VH(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemjoin_title);
            status = itemView.findViewById(R.id.itemjoin_status);

            detail = itemView.findViewById(R.id.itemjoin_button);
        }
    }
}
