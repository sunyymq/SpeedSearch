package com.android.speedsearch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

public class ImagesManager{
	static ImagesManager instance;
    static int[] AdvScale;
    static int[] AdvScale2;
    static int[] CardScale;
    static int[] CouponScale;
    static int[] CraftScale;
    static int[] FifthScale;
    static int[] ForthScale;
    static int[] LargeGalleryScale;
    static int[] LargeGalleryScaleForLoad;
    static int[] LicenseScale;
    static int[] MiddleGalleryScale;
    static int[] MiddleGalleryScaleForLoad;
    static int[] PosterScale;
    static int[] QuarterGalleryScale;
    public static long SIZE_LIMIT = 0L;
    static int[] SixthScale;
    static int[] SmallGalleryScale;
    static int[] SmallGalleryScaleForLoad;
    static String TAG = "ImagesManager";
    static int[] ThumCardScale;
    static int[] ThumNailScale;
    static int[] TopicScale;
    static final int cornerSize = 13;
    private SsImageCache imagePool = null;
    private boolean stop = false;
    
    private ImagesManager(){
      if (Build.VERSION.SDK_INT < 11)
        SIZE_LIMIT /= 4L;
      ThumNailScale = new int[]{ 200, 120};
      ThumCardScale = new int[] { 200, 120 };
      TopicScale = new int[] { 480, 240 };
      PosterScale = new int[]{ 200, 120};
      CardScale = new int[]{ 200, 120};
      LicenseScale = new int[]{ 200, 120};
      CraftScale = new int[]{ 200, 120};
      AdvScale = new int[]{ 200, 120};
      AdvScale2 = new int[]{ 200, 120};
      CouponScale = new int[]{ 200, 120};
      int[] arrayOfInt = new int[]{ 200, 120};
      ForthScale = arrayOfInt;
      QuarterGalleryScale = arrayOfInt;
      FifthScale = new int[]{ 200, 120};
      SixthScale = new int[]{ 200, 120};
      SmallGalleryScale = new int[]{ 200, 120};
      if ((SmallGalleryScale[0] > 160) || (SmallGalleryScale[0] == 0)){
    	  SmallGalleryScaleForLoad = SmallGalleryScale;
      }else{
          SmallGalleryScaleForLoad = new int[2];
          SmallGalleryScaleForLoad[0] = 160;
          SmallGalleryScaleForLoad[1] = 160; 	  
      }
      MiddleGalleryScale = new int[]{ 200, 120};
      if ((MiddleGalleryScale[0] <= 240) && (MiddleGalleryScale[0] != 0)){
    	  MiddleGalleryScaleForLoad = MiddleGalleryScale;
      }else{
        MiddleGalleryScaleForLoad = new int[2];
        MiddleGalleryScaleForLoad[0] = 240;
        MiddleGalleryScaleForLoad[1] = 240;
      }
      LargeGalleryScale = new int[]{ 200, 120};
      if ((LargeGalleryScale[0] <= 320) && (LargeGalleryScale[0] != 0)){
    	  LargeGalleryScaleForLoad = LargeGalleryScale;
      }else{
        LargeGalleryScaleForLoad = new int[2];
        LargeGalleryScaleForLoad[0] = 320;
        LargeGalleryScaleForLoad[1] = 320;
      }
      imagePool = new SsImageCache(240);
    }
    
	public static ImagesManager getInstance(){
	    try{
	      if (instance == null)
	        instance = new ImagesManager();
	      return instance;
	    }finally{
	    }
	}
	
	public Bitmap LoadBitmapFromPool(ImageInfo img){
	    String key = img.getFilePath() + "_ScaleType_" + img.ScaleType;
	    return imagePool.getObject(key);
	}
	
	public void freeImage(String filepath, int scaleType){
	    if ((filepath == null) || (imagePool == null))
	      return;
	    String key = filepath + "_ScaleType_" + scaleType;
	    imagePool.delayFreeObject(key);
	}
	
	public void takecare(ImageInfo info){
	    if (info == null)
	      return;
	    if ((info.isPhotoExisted) && (!info.isPhotoLocalized) && (!info.isLoading))
	        pushQueue(info);
	    return;
	}
	
	public Bitmap pushQueue(String key, Bitmap bmp){
	    if ((imagePool != null) && (bmp != null)){
	    	return imagePool.putObject(key, bmp);
	    }
	    return null;
	}

	public void pushQueue(ImageInfo info){
	    if ((imagePool != null) && (info != null)){
	    	info.isLoading = true;
	      String key = info.getFilePath() + "_ScaleType_" + info.getScaleType();
	      imagePool.operateImageJobQueue(key, 0, info);
	    }
	}
	
	public Bitmap LoadBitmap(String filename, int scaleType){
	    Bitmap bmp = null;
	    if (filename == null){
	    	return bmp;
	    }
	    String key = filename + "_ScaleType_" + scaleType;
	    bmp = imagePool.getObject(key);
	    if (bmp == null){
	    	bmp = DirectLoadBitmap(filename, scaleType);
	        if (bmp != null)
	        	bmp = imagePool.putObject(key, bmp);
	        else{
	        	try{
	        		new File(filename).delete();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
	    }
	    return bmp;
	}
	
	public static Bitmap DirectLoadBitmap(String filename, int scaleType){
	    if (filename == null){
	    	return null;
	    }
	    return LoadBitmap(filename, new int[]{ 200, 120}, getRGBConfigForLoad(scaleType));
	}
	
	private static Bitmap.Config getRGBConfigForLoad(int scaleType){
	    return Bitmap.Config.RGB_565;
	}
	
	private static Bitmap LoadBitmap(String filename, int[] Scale, Bitmap.Config RGBConfig){
	    long size = getFileSize(filename);
	    if ((size < 100L) || (size > 2048000L))
	      return null;
	    try{
	        BitmapFactory.Options opt = getBitmapOptions(filename);
	        int sample = 1;
	        if ((Scale[0] == 0) && (Scale[1] == 0)){
	          if (opt.outHeight * opt.outWidth > SIZE_LIMIT){
	        	  sample = 2;
	          }else{
	        	  sample = 1;
	          }
	        }else if ((Scale[0] != -1) && (Scale[1] != -1)){
		        int ratiow = (opt.outWidth + Scale[0] / 2) / Scale[0];
		        int ratioh = (opt.outHeight + Scale[1] / 2) / Scale[1];
		        if (ratiow > ratioh){
		        	sample = ratioh;
		        }else{
		        	sample = ratioh;
		        }
	        }
	        return decodeFile(filename, sample, RGBConfig);
	    }catch (OutOfMemoryError e){
	        e.printStackTrace();
	    }catch (Exception e){
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public static long getFileSize(String filename){
		if(filename !=null){
			File file = new File(filename);
			return file.length();
		}
		return (Long) null;
	}
	
	public static Bitmap decodeFile(String filename, int sample, Bitmap.Config RGBConfig){
		FileInputStream is;
		Bitmap bmp;
	    try{
	    	is = new FileInputStream(new File(filename));
	    	bmp = decodeBitmap(is, sample, RGBConfig);
	    }catch (Exception e){
	    	e.printStackTrace();
	    	return null;
	    }
	    try{
	    	is.close();
	        return bmp;
	    }catch (Exception e){
	    	return null;
	    }
	}
	
	private static BitmapFactory.Options getBitmapOptions(FileInputStream BitmapInputStream){
		BitmapFactory.Options opt;
	    try{
	    	opt = new BitmapFactory.Options();
	    	opt.inJustDecodeBounds = true;
	    	BitmapFactory.decodeStream(BitmapInputStream, null, opt);
	    }catch (Exception e){
	        e.printStackTrace();
	        return null;
	    }
	    try{
	        BitmapInputStream.close();
	        return opt;
	    }catch (Exception e){
	       return null;
	    }
	}

	private static BitmapFactory.Options getBitmapOptions(String filename){
	    try{
	      return getBitmapOptions(new FileInputStream(new File(filename)));
	    } catch (Exception e){
	        e.printStackTrace();
	        return null;
	    }
	}
	
	private static Bitmap decodeBitmap(InputStream is, int sample, Bitmap.Config RGBConfig){
	    BitmapFactory.Options opt = new BitmapFactory.Options();
	    opt.inJustDecodeBounds = false;
	    opt.inPreferredConfig = RGBConfig;
	    opt.inPurgeable = true;
	    opt.inInputShareable = true;
	    opt.inSampleSize = sample;
	    try{
	      BitmapFactory.Options.class.getField("inNativeAlloc").setBoolean(opt, true);
	    }catch (Exception e){
		}
	    try{
	        return BitmapFactory.decodeStream(is, null, opt);
	    }catch (Exception e){
	        return null;
	    }catch (OutOfMemoryError e){
	        e.printStackTrace();
	        System.gc();
	        return null;
	    }
	}
}