package com.android.speedsearch.util;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageAble implements Parcelable{
	  public static final Parcelable.Creator<ImageAble> CREATOR = new Parcelable.Creator<ImageAble>(){
	    public ImageAble createFromParcel(Parcel source){
	      ImageAble info = new ImageAble();
	      String[] Strdata = new String[4];
	      source.readStringArray(Strdata);
	      ArrayList<String> imgurls = new ArrayList<String>();
	      source.readStringList(imgurls);
	      try{
	    	  info.setDrawableResid(DataConverter.parseInt(Strdata[0]));
	    	  info.setDirPath(Strdata[1], Strdata[2]);
	    	  info.setImageUrl(imgurls, DataConverter.parseInt(Strdata[3]), true);
	    	  return info;
	      }catch (Exception e){
	          e.printStackTrace();
	          return null;
	      }
	    }

	    public ImageAble[] newArray(int size){
	      return new ImageAble[size];
	    }
	  };
	 String DefaultDIRPATH;
	 int DrawableResid = -1;
	 boolean ImageChanged = false;
	 protected ImageInfo Img;
	 String InDIRPATH;
	 protected boolean isCalledLoad = false;
	 
	 public void setDrawableResid(int resid){
	    DrawableResid = resid;
	 }
	 
	 public void setDirPath(String ExDir, String InDir){
	    DefaultDIRPATH = ExDir;
	    InDIRPATH = InDir;
	 }
	 
	 public void setImageUrl(String imageUrl, int scaleType, boolean Loadlater){
	    if (("".equals(imageUrl)) || ("null".equals(imageUrl)) || ("NULL".equals(imageUrl)) || (imageUrl == null)){
	    	return;
	    }
	    Img = new ImageInfo();
	    Img.setParams(imageUrl, scaleType, DefaultDIRPATH, InDIRPATH);
	    if (!Loadlater)
	       Img.LoadBitmap();
	 }
	 
	public void setImageUrl(List<String> list){
	    if (list == null)
	      return;
	    Img = new ImageInfo();
	    Img.setParams(list, 2, DefaultDIRPATH, InDIRPATH);
	    Img.LoadBitmap();
	}

	public void setImageUrl(List<String> list, int scaleType, boolean Loadlater){
	    if (list == null)
	      return;
	    Img = new ImageInfo();
	    Img.setParams(list, scaleType, DefaultDIRPATH, InDIRPATH);
	    if (!Loadlater)
	        Img.LoadBitmap();
	}

	public void setImageUrl(List<String> list, boolean Loadlater){
	    if (list == null)
	      return;
	    Img = new ImageInfo();
	    Img.setParams(list, 2, DefaultDIRPATH, InDIRPATH);
	    if (!Loadlater)
	       Img.LoadBitmap();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getDrawableResid(){
	    return DrawableResid;
	}
	
	public List<String> getImageUrls(){
	    if (Img != null){
	    	return Img.getUrls();
	    }
	    return null;
	}
	  
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
	    int scaleType = 2;
	    if (this.Img != null)
	    	scaleType = Img.getScaleType();
	    String[] Strdata = new String[4];
	    Strdata[0] = String.valueOf(getDrawableResid());
	    Strdata[1] = this.DefaultDIRPATH;
	    Strdata[2] = this.InDIRPATH;
	    Strdata[3] = String.valueOf(scaleType);
	    dest.writeStringArray(Strdata);
	    dest.writeStringList(getImageUrls());
	}
}