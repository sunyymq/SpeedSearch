package com.android.speedsearch.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.android.speedsearch.R;
import com.android.speedsearch.adapter.MoreAdapter;

public class MainActivity extends TabActivity{
  private MoreAdapter moreAdapter;

  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tab);
    TabHost th = getTabHost();
    View view1 = View.inflate(this, R.layout.tab_item, null);
    ((ImageView)view1.findViewById(R.id.tab_item_ImageView)).setImageResource(R.drawable.tab_search_selector);
    ((TextView)view1.findViewById(R.id.tab_item_TextView)).setText("查询");
    th.addTab(th.newTabSpec("Test1").setIndicator(view1).setContent(new Intent(this, SearchActivity.class)));
    View view2 = View.inflate(this, R.layout.tab_item, null);
    ((ImageView)view2.findViewById(R.id.tab_item_ImageView)).setImageResource(R.drawable.tab_setting_selector);
    ((TextView)view2.findViewById(R.id.tab_item_TextView)).setText("常用快递公司");
    th.addTab(th.newTabSpec("Test2").setIndicator(view2).setContent(new Intent(this, MoreActivity.class)));
    View view3 = View.inflate(this, R.layout.tab_item, null);
    ((ImageView)view3.findViewById(R.id.tab_item_ImageView)).setImageResource(R.drawable.tab_about_selector);
    ((TextView)view3.findViewById(R.id.tab_item_TextView)).setText("关于");
    th.addTab(th.newTabSpec("Test3").setIndicator(view3).setContent(new Intent(this, AboutActivity.class)));
  }
}