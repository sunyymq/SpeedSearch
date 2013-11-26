package com.android.speedsearch.util;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.util.Log;

public class SsImageCache{
	  public static final int DELAY_FREE = 6;
	  public static final int FREE = 2;
	  public static final int GET = 1;
	  public static final int GET_KEY = 3;
	  public static final int GET_LIST = 4;
	  public static final int PUT = 0;
	  public static final int UPDATE = 5;
	  private static long USED_MEM = 16777216L;
	  private Map<String, CacheImageInfo> cache = new HashMap<String, CacheImageInfo>();
	  private Map<String, DelayFreeCount> freeCountMap = new HashMap<String, DelayFreeCount>();
	  private Map<String, Integer> imageJobKeyQueue = new HashMap<String, Integer>();
	  private Map<String, ArrayList<ImageInfo>> imageJobQueue = new HashMap<String, ArrayList<ImageInfo>>();
	  private Timer timer = new Timer();
	  
	  private class CacheImageInfo{
	    public int ref;
	    public SoftReference<Bitmap> soft;

	    private CacheImageInfo(){
	    }
	  }
	  
	  public static class DelayFreeCount{
	    public long bmpSize;
	    public String key;
	  }
	  
	  public SsImageCache(int densityDpi){
	    timer.schedule(new CheckHeapTask(freeCountMap, cache), 0L, 15000L);
	    if (densityDpi <= 160)
	      USED_MEM = 16777216L;
	    else if ((densityDpi > 160) && (densityDpi < 320))
	        USED_MEM = 25165824L;
	    else if (densityDpi >= 320)
	        USED_MEM = 33554432L;
	    return;
	  }
	  
	  private Bitmap operateMap(String key, int action, Bitmap data){
	    CacheImageInfo obj = (CacheImageInfo)this.cache.get(key);
	    if (action != DELAY_FREE)
	      freeCountMap.remove(key);
	    if (action == PUT){
	    	obj = new CacheImageInfo();
	    	obj.soft = new SoftReference<Bitmap>(data);
	    	obj.ref = 0;
	    	cache.put(key, obj);
	    	return (Bitmap)obj.soft.get();
	    }
	    if (action == GET){
	        if (obj != null){
	        	obj.ref = (1 + obj.ref);
	        	return (Bitmap)obj.soft.get();
	        }
	    }else if (action == UPDATE){
	        if (obj != null){
	          Bitmap bmp = (Bitmap)obj.soft.get();
	          if ((bmp != null) && (!bmp.isRecycled()))
	        	  bmp.recycle();
	          cache.remove(key);
	        }
	        obj = new CacheImageInfo();
	        obj.soft = new SoftReference<Bitmap>(data);
	        obj.ref = 0;
	        cache.put(key, obj);
	        return (Bitmap)obj.soft.get();
	    }else if (action == FREE){
	        if (obj != null){
	        	obj.ref = obj.ref - 1;
	          if (obj.ref < 0){
	            Bitmap bmp = (Bitmap)obj.soft.get();
	            if ((bmp != null) && (!bmp.isRecycled()))
	            	bmp.recycle();
	            cache.remove(key);
	          }
	        }
	    }else if ((action == DELAY_FREE) && (obj != null)){
	    	obj.ref = (-1 + obj.ref);
	        if (obj.ref < 0){
	          Bitmap bmp = (Bitmap)obj.soft.get();
	          if ((bmp != null) && (!bmp.isRecycled()) && ((DelayFreeCount)freeCountMap.get(key) == null)){
	            DelayFreeCount delay = new DelayFreeCount();
	            delay.bmpSize = (2 * (bmp.getHeight() * bmp.getWidth()));
	            delay.key = key;
	            freeCountMap.put(key, delay);
	          }
	        }
	    }
	    return null;
	  }
	  
	  public void delayFreeObject(String key){
	    operateMap(key, DELAY_FREE, null);
	  }
	  
	  public void freeAll(){
	    try{
	      if (timer != null)
	        timer.cancel();
	      timer = null;
	      Iterator<Entry<String, CacheImageInfo>>  iterator = cache.entrySet().iterator();
	      while(iterator.hasNext()){
		        Bitmap bitmap = (Bitmap)((CacheImageInfo)((Map.Entry<String, CacheImageInfo>)iterator.next()).getValue()).soft.get();
		        if ((bitmap != null) && (!bitmap.isRecycled()))
		        	bitmap.recycle();	    	  
	      }
	      cache.clear();
	      freeCountMap.clear();
	      imageJobQueue.clear();
	      imageJobKeyQueue.clear();
	      return;
	    }catch (Exception e){
	        e.printStackTrace();
	    }

	  }
	  
	  public void freeObject(String key){
	      operateMap(key, FREE, null);
	  }
	  
	  public int getCacheNum(){
	    int ret = 0;
	    if (cache != null){
	    	ret = cache.size();
	    	Log.e("ImagesManager", "cur CacheNum =" + ret);
	    }
	    return ret;
	  }
	  
	  public int getLoadImageNums(){
	    return imageJobKeyQueue.size();
	  }
	  
	  public Bitmap getObject(String key){
	    return operateMap(key, GET, null);
	  }
	  
	  public Object operateImageJobQueue(String key, int action, ImageInfo info){
	    switch (action){
	    case PUT:
	    	if ((key != null) && (info != null)){
		        ArrayList<ImageInfo> sameImages = (ArrayList<ImageInfo>)this.imageJobQueue.get(key);
		        if (sameImages == null){
		        	sameImages = new ArrayList<ImageInfo>();
		        	imageJobQueue.put(key, sameImages);
		        	imageJobKeyQueue.put(key, Integer.valueOf(0));
		        }
		        sameImages.add(info);
	    	}
	    	break;
	    case GET:
	    	if (imageJobKeyQueue.entrySet().iterator().hasNext()){
	    		Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)(imageJobKeyQueue.entrySet().iterator().next());
	    		String k = entry.getKey();
	    		imageJobKeyQueue.remove(k);
	    	}
	    	break;
	    case FREE:
	    	if(key != null){
	    		return imageJobQueue.get(key);
	    	}
	    	break;
	    case GET_KEY:
	    	if (key != null){
	    		imageJobQueue.remove(key);
	    	}
	    	break;
	    }
	    return null;
	  }
	  
	  public Bitmap putObject(String key, Bitmap obj){
	    return operateMap(key, PUT, obj);
	  }

	  public Bitmap updateObject(String key, Bitmap obj){
	    return operateMap(key, UPDATE, obj);
	  }
	  public static class BmpCacheComparable implements Comparator<SsImageCache.DelayFreeCount>{
	    public static boolean sortASC = false;
	    public static boolean sortBySize = true;

	    public int compare(SsImageCache.DelayFreeCount obj1, SsImageCache.DelayFreeCount obj2){
	      int result = 0;
	      if (sortASC){
	    	  if (sortBySize){
	    		  if (obj1.bmpSize <= obj2.bmpSize){
	    			  if (obj1.bmpSize == obj2.bmpSize){
	    		        	result = 0;
	    		        }else{
	    		        	result = -1;
	    		        }
	    		  }else{
	    			  result = 1;
	    		  }
	    	  }
	      }else{
	    	  if (sortBySize){
		            if (obj1.bmpSize > obj2.bmpSize)
		            	result = -1;
		            else if (obj1.bmpSize == obj2.bmpSize)
		            	result = 0;
		            else
		            	result = 1;	    		  
	    	  }
	      }
	      return result;
	      }
	  }
	  
	  public static class CheckHeapTask extends TimerTask{
	    private Map<String, SsImageCache.CacheImageInfo> mCache;
	    private Map<String, SsImageCache.DelayFreeCount> mMap;
	    private SsImageCache.BmpCacheComparable sort;

	    CheckHeapTask(Map<String, SsImageCache.DelayFreeCount> map, Map<String, SsImageCache.CacheImageInfo> paramMap1){
	      this.mMap = map;
	      this.mCache = paramMap1;
	      this.sort = new SsImageCache.BmpCacheComparable();
	    }

	    public void run(){
	    	long totalMemory = Runtime.getRuntime().totalMemory();
	    	long freeMemory = Runtime.getRuntime().freeMemory();
	      long diff = totalMemory - freeMemory - SsImageCache.USED_MEM;
	      ArrayList<SsImageCache.DelayFreeCount> list;
	      if (diff > 0L){
		        if (mMap.size() > 0){
		        	list = new ArrayList<SsImageCache.DelayFreeCount>(mMap.values());
			        Collections.sort(list, sort);
			        while(list.iterator().hasNext()){
			        	SsImageCache.DelayFreeCount delay = (SsImageCache.DelayFreeCount)list.iterator().next();
			        	if(diff > 0L){
			        		SsImageCache.CacheImageInfo obj = (SsImageCache.CacheImageInfo)this.mCache.get(delay.key);
			        		if (obj != null){
			        			Bitmap bmp = (Bitmap)obj.soft.get();
			      	          	if ((bmp != null) && (!bmp.isRecycled())){
			      	        	  bmp.recycle();
			      	        	  diff -= delay.bmpSize;
			      	          	}
			      	          	mCache.remove(delay.key);
			        		}
			        		mMap.remove(delay.key);
			        	}
			        }
		        	list.clear();
			        System.gc();
			    }    	  
	      }
	      return;
	    }
	  }
}