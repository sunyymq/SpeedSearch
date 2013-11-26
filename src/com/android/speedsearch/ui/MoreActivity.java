package com.android.speedsearch.ui;

import java.util.ArrayList;
import java.util.List;

import com.android.speedsearch.R;
import com.android.speedsearch.service.SpeedSearchService;
import com.android.speedsearch.util.StringUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MoreActivity extends Activity{
	  List<String> list = new ArrayList<String>();
	  ListView mListView;
	  
	  protected void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.more);
	    mListView = ((ListView)findViewById(R.id.more_listview));
	    View view = LayoutInflater.from(this).inflate(R.layout.footer, null);
	    mListView.addFooterView(view);
	    mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, speedsearchItem.name));
	    mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    String companys = getSharedPreferences("codestiny", 0).getString("common", "");
	    if (!companys.equals("")){
	      for(int i = 0;i < speedsearchItem.name.length;i++){
	    	  for(int j = 0; j < companys.split(",").length; j++){
	    	      if (speedsearchItem.name[i].equals(companys.split(",")[j])){
	    	        mListView.setSelected(true);
	    	        mListView.setSelection(i);
	    	        mListView.setItemChecked(i, true);
	    	      }
	    	  }
	      }
	    }
	    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
	        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
	          if (id == -1L);
	          for (int i = 0; i < speedsearchItem.name.length; i++){
	            if (mListView.getCheckedItemPositions().get(i)){
	              String name = mListView.getAdapter().getItem(i).toString();
	              list.add(name);
	            }
	          }
              MoreActivity.this.recordSetting();
              return;
	        }
	      });
	      return;
	  }
	  
	  private void recordSetting(){
	    if (list.size() == 0){
	      Toast.makeText(this, "您未选择任何常用的快递公司！", Toast.LENGTH_LONG).show();
	      return;
	    }
	    SharedPreferences.Editor editor = getSharedPreferences("codestiny", 0).edit();
	    editor.putString("common", StringUtil.listToString(list));
	    editor.commit();
	    Toast.makeText(this, "设置成功！", Toast.LENGTH_LONG).show();
	    Intent intent = new Intent();
	    intent.setClass(this, MainActivity.class);
	    startActivity(intent);
	  }
	  
	  public boolean onKeyDown(int keyCode, KeyEvent event){
	    if (keyCode == KeyEvent.KEYCODE_BACK){
	      SpeedSearchService.promptExitApp(this);
	      return true;
	    }
	    return super.onKeyDown(keyCode, event);
	  }
}