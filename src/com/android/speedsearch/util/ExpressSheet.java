package com.android.speedsearch.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressSheet extends ListPageAble<ExpressBaseInfo>{
	  private List<ExpandableInfo> Cates;
	  private List<ExpressBaseInfo> expressList;
	  private List<ExpressBaseInfo> topExpressList;
	  
	  public List<ExpandableInfo> getCates(Boolean isShowTopExpress){
	    if (Cates == null){
	    	Cates = new ArrayList<ExpandableInfo>();
	    }else{
	    	Cates.clear();
	    }
	    HashMap<String, Object> hashmap = new HashMap<String, Object>();
	    ArrayList<String> listTopExpressName = new ArrayList<String>();
	    if (topExpressList != null){
	        while (topExpressList.iterator().hasNext()){
	        	ExpressBaseInfo info = (ExpressBaseInfo)topExpressList.iterator().next();
	        	listTopExpressName.add(info.getCompanyName());
	        }
	    }
	    while(expressList.iterator().hasNext()){
	    	ExpressBaseInfo info = (ExpressBaseInfo)expressList.iterator().next();
	    	List<Object> sheet = null;
	    	String key;
	    	if(listTopExpressName !=null && (isShowTopExpress.booleanValue())){
	    		if (listTopExpressName.contains(info.getCompanyName())){
	    			key = " ³£ÓÃ¿ìµÝ";
	    		}else{
	    			key = info.getFirstChar();
	    		}
	    	}else{
	    		key = info.getFirstChar();
	    	}
	    	if (hashmap.containsKey(key)){
	    		sheet = (List<Object>)hashmap.get(key);
	    	}else{
	    		sheet = new ArrayList<Object>();
	    		hashmap.put(key, sheet);
	    	}
	    	sheet.add(info);
	    }
	    while(hashmap.entrySet().iterator().hasNext()){
		      Map.Entry<String,Object> entry = (Map.Entry<String,Object>)hashmap.entrySet().iterator().next();
		      ExpressExandable cate = new ExpressExandable();
		      cate.setTitle((String)entry.getKey());
		      cate.setChilds((List<Object>)entry.getValue());
		      Cates.add(cate);
	    }
  	  	hashmap.clear();
  	  	Collections.sort(Cates, new Comparator(){
  	  		public int compare(Object lhs, Object rhs){
  	  			return ((ExpandableInfo)lhs).getTitle().compareToIgnoreCase(((ExpandableInfo)rhs).getTitle());
  	  		}
  	  	});
  	  	return Cates;
	  }
	  
	  public List<ExpressBaseInfo> getExpressList(){
	    return expressList;
	  }

	  public List<ExpressBaseInfo> getTopExpressList(){
	    return topExpressList;
	  }

	  public void setExpressList(List<ExpressBaseInfo> list){
	    expressList = list;
	  }

	  public void setTopExpressList(List<ExpressBaseInfo> list){
	    topExpressList = list;
	  }

	  public class ExpressExandable implements ExpandableInfo{
	    String Title;
	    List<Object> childs;

	    public ExpressExandable(){
	    }

	    public List<Object> getChildInfos(){
	      return childs;
	    }

	    public int getSize(){
	      if (childs == null){
	    	  return 0;
	      }
	      return childs.size();
	    }

	    public String getSubTitle(){
	      return null;
	    }

	    public String getTitle(){
	      return Title;
	    }

	    void setChilds(List<Object> list){
	      childs = list;
	    }

	    void setTitle(String title){
	      Title = title;
	    }
	  }
}