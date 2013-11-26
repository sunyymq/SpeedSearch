package com.android.speedsearch.adapter;

import java.util.List;

import com.android.speedsearch.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter{
  private Context context;
  private List<String> names;

  public SpinnerAdapter(Context context, List<String> list){
    this.context = context;
    this.names = list;
  }

  public int getCount(){
    return names.size();
  }

  public Object getItem(int position){
    return names.get(position);
  }

  public long getItemId(int position){
    return position;
  }

  public View getView(int position, View convertView, ViewGroup parent){
    ViewHolder holder;
    if (convertView == null){
    	convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
    	holder = new ViewHolder();
    	holder.text = ((TextView)convertView.findViewById(R.id.spinner_item_textview));
    	convertView.setTag(holder);
    }else{
    	holder = (ViewHolder)convertView.getTag();
    }
    holder.text.setText((CharSequence)names.get(position));
    return convertView;
  }

  static class ViewHolder{
    TextView text;
  }
}