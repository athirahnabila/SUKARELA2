package com.example.robin.sukarela.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.Event;

import java.util.List;

public class EventAdapter extends ArrayAdapter {

    private Context mContext;

    public EventAdapter(Context context) {
        super(context, R.layout.item_event);

        mContext = context;
    }

    @Override
    public int getCount() {
        return Event.EVENTS.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // current item model
        Event event = Event.EVENTS.get(position);

        // create item view holder
        ViewHolder vh = new ViewHolder();

        if (convertView == null) {
            // create view for first time
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(R.layout.item_event, parent, false);
            vh.image = convertView.findViewById(R.id.image_event);
            vh.text_title = convertView.findViewById(R.id.text_title);
            vh.text_date_event = convertView.findViewById(R.id.text_date_event);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.image.setImageResource(event.getImage());
        vh.text_title.setText(event.getTitle());
        vh.text_date_event.setText(event.getDate_event());

        return convertView;
    }

    static class ViewHolder {
        ImageView image;

        TextView text_title;
        TextView text_date_event;
    }
}
