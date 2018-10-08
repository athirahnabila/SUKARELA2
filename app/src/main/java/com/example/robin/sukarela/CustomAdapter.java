package com.example.robin.sukarela;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    String [] descp;
    int[] pic;
    String [] date;
    Context mContext;

    public CustomAdapter(Context context, String[] disc, String[] tarikh, int[] images){
        super(context,R.layout.customlayout);
        this.descp=disc;
        this.pic=images;
        this.date=tarikh;
        this.mContext=context;
    };

    @Override
    public int getCount() {
        return descp.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ViewHolder mViewHolder = new ViewHolder();
        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.customlayout, parent, false);
            mViewHolder.mPic = (ImageView) convertView.findViewById(R.id.ipic);
            mViewHolder.mdesc = (TextView) convertView.findViewById(R.id.tvdesc);
            mViewHolder.mdate = (TextView) convertView.findViewById(R.id.tvdate);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder=(ViewHolder)convertView.getTag();
        }
           mViewHolder.mPic.setImageResource(pic[position]);
           mViewHolder.mdesc.setText(descp[position]);
           mViewHolder.mdate.setText(date[position]);

        return convertView;
    }

    static class ViewHolder{
        ImageView mPic;
        TextView mdesc;
        TextView mdate;
    }
}
