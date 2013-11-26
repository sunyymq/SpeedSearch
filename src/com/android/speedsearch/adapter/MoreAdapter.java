package com.android.speedsearch.adapter;

import com.android.speedsearch.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreAdapter extends BaseAdapter{
  private Context context;

  public MoreAdapter(Context context){
    this.context = context;
  }

  public int getCount(){
    return 0;
  }

  public Object getItem(int position){
    return Integer.valueOf(position);
  }

  public long getItemId(int position){
    return position;
  }

  public View getView(int position, View convertView, ViewGroup parent){
    convertView = LayoutInflater.from(context).inflate(R.layout.listview_more_item, null);
    ImageView mImageView = (ImageView)convertView.findViewById(R.id.iv_more);
    TextView mTextView = ((TextView)convertView.findViewById(R.id.tv_more));
    mImageView.setBackgroundResource(com.android.speedsearch.ui.speedsearchItem.more_image[position]);
    return convertView;
  }
}