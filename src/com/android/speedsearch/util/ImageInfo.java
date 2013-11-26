package com.android.speedsearch.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.os.Process;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

public class ImageInfo{
	  String DefaultDIRPATH = Environment.getExternalStorageDirectory().toString()+"/speedsearch/"+"Download/";
	  private String Host;
	  String InDIRPATH = "/data/data/com.android.speedsearch/files/Images/";
	  public boolean MD5filename = true;
	  int ScaleType = 0;
	  OnImageUpdateListener imagelistener;
	  public volatile boolean isFree;
	  public boolean isLoading = false;
	  public boolean isPhotoExisted = false;
	  public boolean isPhotoLocalized = false;
	  Bitmap mBitmap;
	  List<String> mUrls;
	  public boolean needSet = true;
	  private String sub_url;
	  int urlPos = 0;
	  
	  private String getFileName(String url){
	    if (!MD5filename){
	    	return DataConverter.FilterFileChar(url);
	    }
	    return DataConverter.getMD5(url.getBytes()) + ".ss";
	  }
	  
	  public void Clear(){
	    try{
	      isFree = true;
	      recycleBitmap();
	    }finally{
	    }
	  }
	  
	  public Bitmap LoadBitmap(int scaleType){
	    ScaleType = scaleType;
	    LoadBitmap(true);
	    return getBitmap();
	  }
	  
	  public void LoadBitmap(){
	    LoadBitmap(true);
	  }
	  
	  public void LoadBitmap(OnImageUpdateListener listener){
	    this.imagelistener = listener;
	    LoadBitmap(true);
	  }
	  
	  public void LoadBitmap(boolean notify){
	    Bitmap bmp = ImagesManager.getInstance().LoadBitmapFromPool(this);
	    if (bmp != null){
	      setBitmap(bmp, notify);
	      return;
	    }
	    AsyncTaskManager.executeImgAsyncTask(new LoadImgAsyncTask(), Boolean.valueOf(notify));
	  }
	  
	  public String getFilePath(){
	    return DefaultDIRPATH + getFileName(getFullUrl());
	  }

	  public String getFullUrl(){
	    if ((Host == null) && (sub_url != null)){
	      if (!sub_url.startsWith("file://")){
	    	  if (!sub_url.startsWith("http://")){
		        if (sub_url.startsWith("/"))
		          Host = "http://img.wochacha.com";
		        else
		          Host = ("http://img.wochacha.com" + "/");
		      }else
		        Host = "";
	      }else
	    	  Host = "";
	    }
	    return Host + sub_url;
	 }
	  
	 String getCurUrl(){
	    int len = 0;
	    if (mUrls != null)
	    	len = mUrls.size();
	    if ((len > 0) && (urlPos < len)){
	    	String url = mUrls.get(urlPos);
		    if ((url != null) && (!"".equals(url)) && (!"NULL".equals(url)) && (!"null".equals(url))){
			    sub_url = url;
			    return getFullUrl();
		    }
	    }
	    return null;
	  }
	  
	 public void recycleBitmap(){
	    if (mBitmap != null){
	      ImagesManager.getInstance().freeImage(getFilePath(), ScaleType);
	      mBitmap = null;
	    }
	 }
	  
	 public void setBitmap(Bitmap bmp){
	    mBitmap = bmp;
	    if ((bmp != null) && (imagelistener != null))
	      imagelistener.OnImageUpdate();
	 }
	 
	 public void setBitmap(Bitmap bmp, boolean notify){
	    mBitmap = bmp;
	    if ((bmp != null) && (imagelistener != null) && (notify))
	      imagelistener.OnImageUpdate();
	 }
	 
	 public Bitmap getBitmap(){
	    return mBitmap;
	 }
	 
	 public void threadLoadBitmap(boolean notify){
	    isPhotoLocalized = false;
	    boolean fileExists = false;
	    String url = getCurUrl();
	    if ((!fileExists) && (url == null)){
	    	File filepath = new File(getFilePath());
	    	if (filepath.exists())
	    	  fileExists = true;
	    	else{
	    		String FilterStr = getFileName(url);
	    		File fIn = new File(InDIRPATH + FilterStr);
	    		if(fIn.exists()){
	    			fileExists = true;
	    			DefaultDIRPATH = InDIRPATH;
	    		}
	    	}
	    }
	    if ((fileExists != false) && (isPhotoExisted))
	        isPhotoLocalized = true;
	    if (isPhotoLocalized){
	    	loadAndSetBmp(notify);
	    }else{
	    	ImagesManager.getInstance().takecare(this);
	    }
	    return; 
	 }
	 
	 public int getScaleType(){
	    return ScaleType;
	 }
	 
	 public void loadAndSetBmp(boolean notify){
	    if (!needSet)
	      return;
	    if (!isFree)
	      setBitmap(ImagesManager.getInstance().LoadBitmap(getFilePath(), ScaleType), notify);
	    return;
	 }
	 
	  String getNextUrl(){
	    int len = 0;
	    if (mUrls != null)
	    	len = mUrls.size();
	    String url;
	    if ((len > 1) && (1 + urlPos < len)){
	      urlPos = (1 + urlPos);
	      url = mUrls.get(urlPos);
	      if ((url != null) && (!"".equals(url)) && (!"NULL".equals(url)) && (!"null".equals(url))){
	    	  sub_url = url;
	    	  boolean fileExists = false;
	    	  if(!fileExists && url !=null){
	    		  File filepath = new File(getFilePath());
	    		  if(filepath.exists()){
	    			  fileExists = true;
	    		  }else{
	    			  String FilterStr = getFileName(getFullUrl());
	    			  File fIn = new File(InDIRPATH + FilterStr);
	    			  if(fIn.exists()){
	    				  fileExists = true;
	    				  DefaultDIRPATH = InDIRPATH;
	    			  }
	    		  }
	    		  if ((fileExists) && (isPhotoExisted)){
	    			  isPhotoLocalized = true;
	    		  }
	    		  return getFullUrl();
	    	  }
	      }
	    }
	    return null;
	 }
	 
	 boolean isUrlValiable(){
	    String url = getCurUrl();
	    if ((url != null) && (!"".equals(url)) && (!"NULL".equals(url)) && (!"null".equals(url))){
	    	return true;
	    }
	    url = getNextUrl();
	    if ((url == null) || ("".equals(url)) || ("NULL".equals(url)) || ("null".equals(url))){
	        return false;
	    }
	    return true;
	 }
	 
	 public void setParams(String url, int scaleType, String DefalutDir1, String DefalutDir2){
	    isFree = false;
	    ScaleType = scaleType;
	    if ((url != null) && (!"".equals(url)) && (!"NULL".equals(url)) && (!"null".equals(url))){
	      mUrls = new ArrayList<String>();
	      String str = url.replace(" ", "%20");
	      mUrls.add(str);
	      sub_url = str;
	    }
	    if (DefalutDir1 != null)
	      DefaultDIRPATH = DefalutDir1;
	    if (DefalutDir2 != null)
	      InDIRPATH = DefalutDir2;
	    isPhotoExisted = isUrlValiable();
	 }
	 
	 public void setParams(List<String> list, int scaleType, String DefalutDir1, String DefalutDir2){
	    isFree = false;
	    mUrls = list;
	    ScaleType = scaleType;
	    if (DefalutDir1 != null){
	      DefaultDIRPATH = DefalutDir1;
	      InDIRPATH = DefalutDir2;
	    }else{
		  DefaultDIRPATH = Environment.getExternalStorageDirectory().toString()+"/.speedsearchCache/";
		  InDIRPATH = "/data/data/com.android.speedsearch/files/Images/";    	
	    }
	    if (mUrls != null){
	    	isPhotoExisted = isUrlValiable();
	    	urlPos = 0;
	    }
	    return;
	}
	 
	public List<String> getUrls(){
	    return mUrls;
	}
	
	private class LoadImgAsyncTask extends AsyncTask<Object, Object, Object>{
	    private LoadImgAsyncTask(){
	    }
	    
	    protected Object doInBackground(Object[] params){
	      boolean thisNotify = ((Boolean)params[0]).booleanValue();
	      try{
	          Process.setThreadPriority(6);
	      }catch (Exception e){
	    	  e.printStackTrace();
	      }
	      try{
	          ImageInfo.this.threadLoadBitmap(thisNotify);
	          return null;
	      } catch (Exception e1){
	            e1.printStackTrace();
	      }
	      return null;
	    }
   }
}