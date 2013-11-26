package com.android.speedsearch.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.google.demo.CaptureActivity;
import com.android.speedsearch.R;
import com.android.speedsearch.adapter.HistoryAdapter;
import com.android.speedsearch.adapter.ResultAdapter;
import com.android.speedsearch.adapter.SpinnerAdapter;
import com.android.speedsearch.db.MyDBhelper;
import com.android.speedsearch.service.IActivity;
import com.android.speedsearch.service.SpeedSearchGetProduct;
import com.android.speedsearch.service.SpeedSearchService;
import com.android.speedsearch.service.Task;
import com.android.speedsearch.util.CodeUtil;
import com.android.speedsearch.util.JasonUtil;

public class SearchActivity extends Activity implements IActivity, AdapterView.OnItemSelectedListener{
	  Button btnScan;
	  Button btnSearch;
	  EditText etExpNo;
	  Intent intentService;
	  ListView lvHistory;
	  ListView lvResult;
	  MyDBhelper myDBHelper;
	  private List<String> names = new ArrayList<String>();
	  ProgressDialog pd;
	  Spinner spinner;
	  TextView tvHistory;
	  TextView tvResult;
	  
	  public void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    etExpNo = ((EditText)findViewById(R.id.etExpNo));
	    btnSearch = ((Button)findViewById(R.id.btnSearch));
	    btnScan = ((Button)findViewById(R.id.btnScan));
	    lvResult = ((ListView)findViewById(R.id.lv_result));
	    lvResult.setCacheColorHint(0);
	    lvHistory = ((ListView)findViewById(R.id.lv_history));
	    lvHistory.setCacheColorHint(0);
	    String companys = getSharedPreferences("codestiny", 0).getString("common", "");
	    if (!companys.equals(""))
	      names = Arrays.asList(companys.split(","));
	    else{
		    for (int i = 0; i < speedsearchItem.name.length; i++)
			    names.add(speedsearchItem.name[i]);
	    }
	    spinner = ((Spinner)findViewById(R.id.spinnerCompany));
	    SpinnerAdapter sa = new SpinnerAdapter(this, names);
	    spinner.setAdapter(sa);
	    spinner.setOnItemSelectedListener(this);
	    SpeedSearchService.allActivity.add(this);
	    btnSearch.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v){
	          ((InputMethodManager)SearchActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(etExpNo.getWindowToken(), 0);
	          /*if ((etExpNo.getText().toString().equals("")) || (etExpNo.getText().toString().replaceAll("\\s*", "").equals(""))){
	            etExpNo.setError("运单号不能为空！");
	            etExpNo.setText("");
	          }else{*/
	           // speedsearchItem.EXP_NO = etExpNo.getText().toString();
	           // speedsearchItem.COMPANY_CODE = CodeUtil.getCodeByName(spinner.getSelectedItem().toString());
	            SearchActivity.this.searchJob();
	          //}
	          return;
	        }
	      });
	    btnScan.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v){
	          Intent intent = new Intent();
	          intent.setClass(SearchActivity.this, CaptureActivity.class);
	          SearchActivity.this.finish();
	          SearchActivity.this.startActivity(intent);
	        }
	      });
	    tvResult = ((TextView)findViewById(R.id.tv_result));
	    tvResult.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v){
	          tvResult.setBackgroundResource(R.color.glue);
	          lvResult.setVisibility(View.VISIBLE);
	          tvHistory.setBackgroundResource(R.color.white);
	          lvHistory.setVisibility(View.GONE);
	        }
	      });
	    tvHistory = ((TextView)findViewById(R.id.tv_history));
	    tvHistory.setBackgroundResource(R.color.white);
	    tvHistory.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v){
	          tvResult.setBackgroundResource(R.color.white);
	          lvResult.setVisibility(View.GONE);
	          tvHistory.setBackgroundResource(R.color.glue);
	          lvHistory.setVisibility(View.VISIBLE);
	          SearchActivity.this.selectDB();
	        }
	      });
	    lvHistory.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
	        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
	        	menu.setHeaderTitle("功能菜单");
	        	menu.add(0, 0, 0, "重新查询此条记录");
	        	menu.add(0, 1, 0, "删除此条记录");
	        	menu.add(0, 2, 0, "删除全部记录");
	        }
	      });
	    etExpNo.setText(speedsearchItem.EXP_NO);
	    return;
	  }
	  
	  protected void onDestroy(){
	    super.onDestroy();
	  }
	  
	  public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id){
	    speedsearchItem.COMPANY_CODE = CodeUtil.getCodeByName(spinner.getSelectedItem().toString());
	  }
	  
	  protected void onResume(){
	    super.onResume();
	    init();
	  }
	  
	  public void init(){
	    intentService = new Intent("com.android.speedsearch.service.SpeedSearchService");
	    startService(intentService);
	  }
	  
	  private void deleteDB(String date, View view){
	    myDBHelper = new MyDBhelper(this);
	    myDBHelper.delete(date);
	    selectDB();
	  }
	  
	  private void searchJob(){
		SpeedSearchService.addNewTask(new Task(Task.TASK_SEARCH_PRODUCT));
	    /*pd = new ProgressDialog(this);
	    pd.setMessage(getResources().getString(R.string.app_name));
	    pd.setTitle(getResources().getString(R.string.progress_result));
	    pd.show();
	    tvResult.setBackgroundResource(R.color.glue);
	    lvResult.setVisibility(View.VISIBLE);
	    tvHistory.setBackgroundResource(R.color.white);
	    lvHistory.setVisibility(View.GONE);
	    insertDB();*/
	    //SpeedSearchGetProduct.a("6913991300919", "", "", "", "", "", 1, 0, true, true);
	  }
	  
	  protected void insertDB(){
	    String str = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss").format(new Date());
	    myDBHelper = new MyDBhelper(this);
	    myDBHelper.insert(str, CodeUtil.getNameByCode(speedsearchItem.COMPANY_CODE), speedsearchItem.EXP_NO);
	  }
	  
	  public boolean onContextItemSelected(MenuItem item){
	    switch (item.getItemId()){
	    case 0:
		    AdapterView.AdapterContextMenuInfo menuInfo0 = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		    speedsearchItem.COMPANY_CODE = CodeUtil.getCodeByName(((TextView)menuInfo0.targetView.findViewById(R.id.tv_name)).getText().toString());
		    speedsearchItem.EXP_NO = ((TextView)menuInfo0.targetView.findViewById(R.id.tv_number)).getText().toString();
		    searchJob();
		    break;
	    case 1:
		    AdapterView.AdapterContextMenuInfo menuInfo1 = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		    deleteDB(((TextView)menuInfo1.targetView.findViewById(R.id.tv_date)).getText().toString(), menuInfo1.targetView);  	
	    	break;
	    case 2:
		    myDBHelper = new MyDBhelper(this);
		    myDBHelper.delete();
		    selectDB();
	    	break;
	    }
	    return super.onContextItemSelected(item);
	  }
	  
	  public boolean onKeyDown(int keyCode, KeyEvent event){
	    if (keyCode == KeyEvent.KEYCODE_BACK){
	      SpeedSearchService.promptExitApp(this);
	      return true;
	    }
	    return super.onKeyDown(keyCode, event);
	  }
	  
	  public void onNothingSelected(AdapterView<?> adapterView){
	    speedsearchItem.COMPANY_CODE = speedsearchItem.code[0];
	  }
	  
	  public void refresh(Object[] param){
		if(pd!=null)
	    pd.dismiss();
	    switch (Integer.parseInt(param[0].toString())){
	    case Task.TASK_SEARCH_RESULT:
	    	JasonUtil rf = new JasonUtil();
	    	String string = null;
	    	String[] strings = null;
	    	try{
	    		string = rf.getString((String)param[1], "status");
	    		if(string.equals("1")){
	    			strings = rf.getStrings((String)param[1]);
	    			ResultAdapter ra = new ResultAdapter(this, strings);
	    			lvResult.setAdapter(ra);
	    		}else{
	    			string = rf.getString((String)param[1], "message");
	    			Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	    		}
	    	}catch (JSONException e) {
	    		Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
	    	break;
	    case Task.TASK_SEARCH_HISTORY:
		    HistoryAdapter ha = new HistoryAdapter(this, (ArrayList<Record>)param[1]);
		    lvHistory.setAdapter(ha);
	    	break;
	    case Task.TASK_SEARCH_PRODUCT:
	    	
	    	break;
	    }
	    return;
	  }
	  
	  protected void selectDB(){
	    SpeedSearchService.addNewTask(new Task(Task.TASK_SEARCH_HISTORY));
	    pd = new ProgressDialog(this);
	    pd.setMessage(getResources().getString(R.string.app_name));
	    pd.setTitle(getResources().getString(R.string.progress_history));
	    pd.show();
	  }
}