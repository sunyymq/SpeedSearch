package com.android.speedsearch.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JasonUtil{
  public String[] BuildSearchResult(JSONArray ja) throws JSONException{
    int n = ja.length();
    String[] strings = new String[n];
    for (int i = 0; i< n; i++) {
      JSONObject obj = new JSONObject(ja.getString(i));
      String time = obj.getString("time");
      String context = obj.getString("context");
      strings[i] = (time + context);
    }
    return strings;
  }

  public String getString(String json, String tag) throws JSONException{
    JSONObject obj = new JSONObject(json);
    String string = null;
    if (tag.equals("status")){
    	string = obj.getString("status");
    }else{
    	string = obj.getString("message");
    }
    return string;
  }

  public String[] getStrings(String json) throws JSONException {
    return BuildSearchResult(new JSONObject(json).getJSONArray("data"));
  }
}