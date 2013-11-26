package com.android.speedsearch.util;

import com.android.speedsearch.ui.speedsearchItem;

public class CodeUtil{
  public static String getCodeByName(String name){
	  int i = 0;
	  if(speedsearchItem.name.length < 0){
		  return null;
	  }
	  for (i = 0; i< speedsearchItem.name.length; i++){
		  String s = speedsearchItem.name[i];
		  if(s.equals(name)){
    		return  speedsearchItem.code[i];
		  }
	  }
	  return null;
  }

  public static String getNameByCode(String code){
    int i = 0;
    if(speedsearchItem.code.length < 0){
    	return null;
    }
    for (i = 0; i< speedsearchItem.code.length; i++){
    	String s = speedsearchItem.code[i];
		if(s.equals(code)){
	    	return  speedsearchItem.name[i];
		}
    }
    return null;
  }
}