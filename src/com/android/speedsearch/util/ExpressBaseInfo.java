package com.android.speedsearch.util;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

public class ExpressBaseInfo extends ImageAble implements Parcelable{
	public static final Parcelable.Creator<ExpressBaseInfo> CREATOR = new Parcelable.Creator<ExpressBaseInfo>(){
	    public ExpressBaseInfo createFromParcel(Parcel source){
	      ExpressBaseInfo info = new ExpressBaseInfo();
	      info.setCompanyName(source.readString());
	      ArrayList<String> urls = new ArrayList<String>();
	      source.readStringList(urls);
	      info.setImageUrl(urls, true);
	      return info;
	    }
	
	    public ExpressBaseInfo[] newArray(int size){
	      return new ExpressBaseInfo[size];
	    }
	};
	
	private String companyName;
	private boolean inEditState = false;
	private boolean isSelected = false;
	private String pinyin;
	
	public void Print(String TAG){
	}

	public int describeContents(){
	    return 0;
	}

	public String getCompanyName(){
	    return companyName;
	}
	
	public String getFirstChar(){
	    if (Validator.isEffective(pinyin)){
	    	return pinyin.substring(0, 1).toUpperCase(Locale.ENGLISH);
	    }
	    return "#";
	}
	
	public String getPinyin(){
	    return pinyin;
	}

	public boolean inEditState(){
	    return inEditState;
	}

	public boolean isSelected(){
	    return isSelected;
	}

	public void setCompanyName(String name){
	    companyName = name;
	}

	public void setEditState(boolean state){
	    inEditState = state;
	}

	public void setPinyin(String pinyin){
	    this.pinyin = pinyin;
	}

	public void setSelected(boolean select){
	    isSelected = select;
	}

	public void writeToParcel(Parcel dest, int flags){
		dest.writeString(companyName);
		dest.writeStringList(getImageUrls());
	}
}