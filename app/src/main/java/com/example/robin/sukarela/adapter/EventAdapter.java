package com.example.robin.sukarela.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robin.sukarela.EventActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.ItemEvent;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private List<ItemEvent> mList;

    public EventAdapter(List<ItemEvent> data) {
        this.mList = data;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final int index = i;

        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", index);

                Intent intent = new Intent(view.getContext(), EventActivity.class);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder eventHolder, int i) {
        ItemEvent event = mList.get(i); // current display item

        eventHolder.image.setImageResource(event.getImage());
        eventHolder.text_title.setText(event.getTitle());
        eventHolder.text_date.setText(event.getDate_event());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class EventHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text_title;
        TextView text_date;

        private EventHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.itemevent_image);
            text_title = itemView.findViewById(R.id.itemevent_text_title);
            text_date = itemView.findViewById(R.id.itemevent_text_date);
        }
    }
}
