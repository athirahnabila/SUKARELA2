package com.example.robin.sukarela.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.robin.sukarela.MainActivity;
import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.EventModel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    // declare component
    private Map<String, EventModel> map;
    private OnItemClick onItemClick;


    public EventAdapter() {
        this.map = MainActivity.EVENT_MAP;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event, viewGroup, false);

        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventHolder eventHolder, int i) {
        // uid event in EVENT_MAP
        String uid = (String) map.keySet().toArray()[i];

        // data
        EventModel event = map.get(uid);
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH);

        String str_time = "Masa: " + time.format(event.getEnd());
        String str_date = "Tarikh: " + date.format(event.getEnd());
        String str_location = "Tempat: " + event.getLocation();

        // assign data
        Glide
                .with(eventHolder.image.getContext())
                .load(event.getImage())
                .into(eventHolder.image);

        eventHolder.title.setText(event.getTitle());
        eventHolder.date.setText(str_date);
        eventHolder.time.setText(str_time);
        eventHolder.location.setText(str_location);
    }

    @Override
    public int getItemCount() {
        return map.keySet().size();
    }

    public void setOnItemClick(OnItemClick click) {
        this.onItemClick = click;
    }

    // adapter onItemClick callback
    public interface OnItemClick {

        void onItemClick(String uid);
    }

    // adapter view holder
    class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;

        TextView title;
        TextView date;
        TextView time;
        TextView location;

        private EventHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.itemevent_image);

            title = itemView.findViewById(R.id.itemevent_text_title);
            time = itemView.findViewById(R.id.itemevent_text_time);
            date = itemView.findViewById(R.id.itemevent_text_date);
            location = itemView.findViewById(R.id.itemevent_text_location);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // uid event in EVENT_MAP
            String uid = (String) map.keySet().toArray()[getAdapterPosition()];

            // do the callback
            if (onItemClick != null) onItemClick.onItemClick(uid);
        }
    }
}
