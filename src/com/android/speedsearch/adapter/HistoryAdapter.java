package com.android.speedsearch.adapter;

import java.util.ArrayList;

import com.android.speedsearch.R;
import com.android.speedsearch.ui.Record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryAdapter extends BaseAdapter{
	  private Context context;
	  private ArrayList<Record> records;
	  static class ViewHolder{
	    TextView tv_date;
	    TextView tv_name;
	    TextView tv_number;
	  }
	  public HistoryAdapter(Context context, ArrayList<Record> records){
	    this.context = context;
	    this.records = records;
	  }
	  
	  public int getCount(){
	    return records.size();
	  }
	  
	  public Object getItem(int position){
	    return records.get(position);
	  }
	  
	  public long getItemId(int position){
	    return position;
	  }
	  
	  public View getView(int position, View convertView, ViewGroup parent){
	    ViewHolder holder;
	    if (convertView == null){
	    	convertView = LayoutInflater.from(context).inflate(R.layout.listview_history_item, null);
	    	holder = new ViewHolder();
	    	holder.tv_date = ((TextView)convertView.findViewById(R.id.tv_date));
	    	holder.tv_name = ((TextView)convertView.findViewById(R.id.tv_name));
	    	holder.tv_number = ((TextView)convertView.findViewById(R.id.tv_number));
	    	convertView.setTag(holder);
	    }else{
	    	holder = (ViewHolder)convertView.getTag();
	    }
	    holder.tv_date.setText(((Record)records.get(position)).getDate());
	    holder.tv_name.setText(((Record)records.get(position)).getName());
	    holder.tv_number.setText(((Record)records.get(position)).getNumber());
	    return convertView;
	  }
}