package com.example.robin.sukarela.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robin.sukarela.R;
import com.example.robin.sukarela.model.EventModel;

import java.util.List;

public class JoinItemAdapter extends RecyclerView.Adapter<JoinItemAdapter.JoinVH> {

    private List<EventModel> mList;


    public JoinItemAdapter(List<EventModel> data) {
        this.mList = data;
    }

    @NonNull
    @Override
    public JoinVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_join, viewGroup, false);

        return new JoinVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinVH joinVH, int i) {
        EventModel event = new EventModel();

        joinVH.text_title.setText(event.getTitle());
        joinVH.text_date.setText("Completed : " + event.getStart());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class JoinVH extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text_title;
        TextView text_date;

        private JoinVH(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.itemjoin_image);
            text_title = itemView.findViewById(R.id.itemjoin_title);
            text_date = itemView.findViewById(R.id.itemjoin_date);
        }
    }
}
