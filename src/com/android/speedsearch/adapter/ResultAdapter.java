package com.android.speedsearch.adapter;

import com.android.speedsearch.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResultAdapter extends BaseAdapter{
	  private Context context;
	  private String[] name;
	  static class ViewHolder{
	    TextView text;
	  }
	  public ResultAdapter(Context context, String[] strings){
	    this.context = context;
	    this.name = strings;
	  }
	
	  public int getCount(){
	    return name.length;
	  }
	  
	  public Object getItem(int position){
	    return name[position];
	  }
	  
	  public long getItemId(int position){
	    return position;
	  }
	  
	  public View getView(int position, View convertView, ViewGroup parent){
	    ViewHolder holder;
	    if (convertView == null){
	    	convertView = LayoutInflater.from(context).inflate(R.layout.listview_result_item, null);
	    	holder = new ViewHolder();
	    	holder.text = ((TextView)convertView.findViewById(R.id.listview_item_textview));
	    	convertView.setTag(holder);
	    }else{
	    	holder = (ViewHolder)convertView.getTag();
	    }
	    holder.text.setText(name[position]);
	    return convertView;
	  }
	  
}