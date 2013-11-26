package com.android.speedsearch.service;

import java.text.MessageFormat;
import java.util.ArrayList;

import com.android.speedsearch.db.MyDBhelper;
import com.android.speedsearch.ui.Record;
import com.android.speedsearch.ui.speedsearchItem;
import com.android.speedsearch.util.HttpUtil;
import com.android.speedsearch.util.ProductUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.util.Log;

public class SpeedSearchService extends Service implements Runnable{
	  public static ArrayList<IActivity> allActivity = new ArrayList<IActivity>();
	  public static ArrayList<Task> allTask = new ArrayList<Task>();
	  public static SpeedSearchService SpeedsearchService;
	  public Handler hand = new Handler(){
	    public void handleMessage(Message msg){
	      super.handleMessage(msg);
	      switch (msg.what){
	      case Task.TASK_SEARCH_RESULT:
		        IActivity ia = SpeedSearchService.getActivityByName("SearchActivity");
		        if (ia != null){
		          Object[] object = new Object[2];
		          object[0] = Integer.valueOf(Task.TASK_SEARCH_RESULT);
		          object[1] = msg.obj;
		          ia.refresh(object);
		        }
		        break;
	      case Task.TASK_SEARCH_HISTORY:
	          IActivity iaa = SpeedSearchService.getActivityByName("SearchActivity");
	          if (iaa != null){
	            Object[] object1 = new Object[2];
	            object1[0] = Integer.valueOf(Task.TASK_SEARCH_HISTORY);
	            object1[1] = msg.obj;
	            iaa.refresh(object1);
	          }
	          break;
	      case Task.TASK_SEARCH_PRODUCT:
	          IActivity iaaa = SpeedSearchService.getActivityByName("SearchActivity");
	          if (iaaa != null){
	            Object[] object1 = new Object[2];
	            object1[0] = Integer.valueOf(Task.TASK_SEARCH_PRODUCT);
	            object1[1] = msg.obj;
	            iaaa.refresh(object1);
	          }	    	  
	      }
	      return;
	    }
	  };
	  MyDBhelper myDBHelper;

	  public SpeedSearchService(){
		  SpeedsearchService = this;
	  }
	  
	  public static void addNewTask(Task task){
	    allTask.add(task);
	  }
	  
	  public static void exitApp(){
	    for (int i = 0; i < allActivity.size(); i++){
	      ((Activity)allActivity.get(i)).finish();
	    }
	    //context.finish();
        allActivity.clear();
        //context.stopService(new Intent("com.android.speedsearch.service.SpeedSearchService"));
        Process.killProcess(Process.myPid());
        return;
	  }
	  
	  public static void promptExitApp(Activity context){
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    builder.setTitle("提示");
	    builder.setMessage("确定要退出程序?");
	    builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
	      public void onClick(DialogInterface dialog, int which){
	    	  dialog.dismiss();
	    	  SpeedSearchService.exitApp();
	      }
	    });
	    builder.setNeutralButton("取消", new DialogInterface.OnClickListener(){
	      public void onClick(DialogInterface dialog, int which){
	    	  dialog.dismiss();
	      }
	    });
	    builder.show();
	  }
	  
	  public static IActivity getActivityByName(String name){
		  IActivity ia = null;
	      while(allActivity.iterator().hasNext()){
		      ia = allActivity.iterator().next();
		      String act = ia.getClass().getName();
		      if (act.indexOf(name) >= 0){
		        return ia;
		      }
	      }
	      return ia;
	  }
	  
	  public void onCreate(){
	    super.onCreate();
	    SpeedsearchService = this;
	    new Thread(this).start();
	  }
	  
	  public IBinder onBind(Intent paramIntent){
	    return null;
	  }
	  
	  public void doTask(Task task){
	    Message message = hand.obtainMessage();
	    switch (task.getTaskID()){
	    case Task.TASK_SEARCH_RESULT:
	        String url = MessageFormat.format(speedsearchItem.URL, new Object[]{speedsearchItem.API_NUMBER, speedsearchItem.COMPANY_CODE, speedsearchItem.EXP_NO});
	        HttpUtil hu = new HttpUtil();
	        String jsonString = hu.doGet(url);
	        Log.e("sunyy", jsonString);
	        message.what = Task.TASK_SEARCH_RESULT;
	        message.obj = jsonString;
	        break;
	    case Task.TASK_SEARCH_HISTORY:
		    ArrayList<Record> records = new ArrayList<Record>();
		    myDBHelper = new MyDBhelper(this);
		    Cursor cursor = myDBHelper.select(null, null, null);
		    while (cursor.moveToNext()){
		      Record record = new Record();
		      record.setDate(cursor.getString(cursor.getColumnIndex("date")));
		      record.setName(cursor.getString(cursor.getColumnIndex("name")));
		      record.setNumber(cursor.getString(cursor.getColumnIndex("number")));
		      records.add(record);
		    }
	    	message.what = Task.TASK_SEARCH_HISTORY;
	    	message.obj = records;
	    	break;
	    case Task.TASK_SEARCH_PRODUCT:
	    	ProductUtil product = SpeedSearchGetProduct.getProductInfo("6913991300919", "", "", "", "", "", 1, 0, true, true);
	    	message.what = Task.TASK_SEARCH_PRODUCT;
	    	message.obj = product;	
	    	break;
	    }
        allTask.remove(task);
        hand.sendMessage(message);
        return;
	  }
	  
	  public void run(){
	    while (true){
	      Task lasttask = null;
	      synchronized (allTask){
	        if (allTask.size() > 0)
	        	lasttask = (Task)allTask.get(0);
	        if (lasttask != null)
	          doTask(lasttask);
	        try{
	          Thread.sleep(1000L);
	        }catch (Exception e){
	        }
	      }
	    }
	  }
}