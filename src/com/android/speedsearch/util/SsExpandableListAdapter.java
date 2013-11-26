package com.android.speedsearch.util;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

public class SsExpandableListAdapter extends BaseExpandableListAdapter{
	  private String TAG = "SsExpandableListAdapter";
	  CheckboxClickListener childCheckboxClickListener;
	  private Context context;
	  protected List<ExpandableInfo> data;
	  int expandableHolderType;
	  ViewHolderFactory factory;
	  CheckboxClickListener groupCheckboxClickListener;
	  int indicatorExpandingResId = -1;
	  int indicatorUnExpandingResId = -1;
	  protected LayoutInflater inflater;
	  Handler invoker;
	  private boolean isShowIndicator = true;
	  private ExpandableListView listView;
	  ImagesNotifyer notifyer;
	  boolean showImage;
	  boolean showcheckbox = true;
	  
	  public static abstract interface CheckboxClickListener{
	    public abstract void OnClick(int paramInt1, int paramInt2);

	    public abstract String getClickMessage(int paramInt1, int paramInt2);
	  }
}