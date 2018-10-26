package com.example.robin.sukarela.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.ItemEvent;
import com.example.robin.sukarela.utility.EventHelper;

public class EventItemAdapter extends RecyclerView.Adapter<EventItemAdapter.EventHolder> {

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event, viewGroup, false);

        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventHolder eventHolder, int i) {
        ItemEvent event = EventHelper.get((String) EventHelper.list()[i]);
        final int index = i;

        eventHolder.root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", (String) EventHelper.list()[index]);

                Log.i("MainActivity", "onClick: " + EventHelper.list()[index]);
                Log.i("MainActivity", "onClick: " + index);

                Intent intent = new Intent(eventHolder.root.getContext(), EventActivity.class);
                intent.putExtras(bundle);
                eventHolder.root.getContext().startActivity(intent);
            }
        });

        Glide
                .with(eventHolder.image.getContext())
                .load(event.getImage())
                .into(eventHolder.image);

        eventHolder.text_title.setText(event.getTitle());
        eventHolder.text_date.setText(event.getDate_event().toString());
    }

    @Override
    public int getItemCount() {
        return EventHelper.size();
    }

    class EventHolder extends RecyclerView.ViewHolder {

        View root;

        ImageView image;
        TextView text_title;
        TextView text_date;

        private EventHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView;

            image = itemView.findViewById(R.id.itemevent_image);
            text_title = itemView.findViewById(R.id.itemevent_text_title);
            text_date = itemView.findViewById(R.id.itemevent_text_date);
        }
    }
}
