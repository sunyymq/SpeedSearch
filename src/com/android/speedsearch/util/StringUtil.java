package com.android.speedsearch.util;

import java.util.List;

public class StringUtil{
  public static String listToString(List<String> list){
    if (list == null)
    	return null;
    StringBuilder result = new StringBuilder();
    boolean flag = false;
    while(list.iterator().hasNext()){
    	String string = list.iterator().next();
    	if(!flag){
    		result.append(",");
    	}else{
    		flag = true;
    	}
    	result.append(string);
    }
    return result.toString();
  }
}