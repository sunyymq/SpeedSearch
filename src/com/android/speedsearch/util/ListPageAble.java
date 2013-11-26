package com.android.speedsearch.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListPageAble<T> implements Iterable<T>{
	  private int CurPage = 1;
	  private int CurRemotePage = 1;
	  private int CurStartIndex = 0;
	  private int DefaultLimitListLen = 1500;
	  private boolean HasNoMoreDatas = false;
	  List<T> Objects;
	  int PageSize = 30;
	  private int RemotePageSize = 30;
	  private int RemoteTotalPageNums = 1;
	  private String TimeStamp;

	  private void ReleaseRemains(int limitListLen){
	    if (Objects == null){
	    	return;
	    }
	    if (limitListLen <= 0)
	    	limitListLen = DefaultLimitListLen;
	    List<T> tempList = Objects;
	    int len = getSize() - limitListLen;
	    for (int i = 0; i < len; i++)
	    	tempList.remove(getSize() - 1);
	  }

	  public void ReleaseHeadRemains(int limitListLen){
	    if (Objects == null){
	    	return;
	    }
	    if (limitListLen <= 0)
	    	limitListLen = DefaultLimitListLen;
	    List<T> tempList = Objects;
	    int len = getSize() - limitListLen;
	    for (int i = 0; i < len; i++)
	    	tempList.remove(0);
	  }

	  public void addHead(List<T> list){
	    addHead(list, DefaultLimitListLen);
	  }

	  public void addHead(List<T> list, int limitListLen){
	    if (list == null){
	    	return;
	    }
	    if (Objects == null){
	        Objects = list;
	    }else{
	        Objects.addAll(0, list);
	        ReleaseRemains(limitListLen);
	    }
	  }

	  public void addItemAhead(T t){
	    if (Objects == null)
	      Objects = new ArrayList<T>();
	    if ((Objects != null) && (t != null))
	      Objects.add(0, t);
	  }

	  public void addTail(T t){
	    if (Objects == null)
	      Objects = new ArrayList<T>();
	    if ((Objects != null) && (t != null))
	      Objects.add(t);
	  }

	  public void addTail(List<T> list, int limitListLen){
	    if (list == null)
	      return;
	    if (Objects == null){
	        Objects = list;
	    }else{
	        Objects.addAll(list);
	        ReleaseHeadRemains(limitListLen);
	    }
	  }

	  public void clear(){
	    if (Objects != null)
	      Objects.clear();
	  }

	  public int getCurPage(){
	    return CurPage;
	  }

	  public int getCurPageNums(){
	    return size() / PageSize;
	  }

	  public int getCurRemotePage(){
	    return CurRemotePage;
	  }

	  public int getCurStartIndex(){
	    return CurStartIndex;
	  }

	  public List<T> getDatas(){
	    return Objects;
	  }

	  public T getItem(int index){
	    if ((index < 0) || (index >= size())){
	    	return null;
	    }
	    return Objects.get(index);
	  }

	  public T getItem(int posInPage, int PageNum, int PageSize){
	    return getItem(posInPage + PageNum * PageSize);
	  }

	  public int getNextRemotePageNum(){
	    return CurRemotePage + 1;
	  }

	  public int getPageSize(){
	    return PageSize;
	  }

	  public List<T> getPagesOfDatas(int PageNum){
	    CurPage = PageNum;
	    return getPagesOfDatas(PageNum, PageSize);
	  }

	  public List<T> getPagesOfDatas(int PageNum, int PageSize){
	    int size = getSize();
	    if (size == 0){
	    	return null;
	    }
	    int end = PageNum * PageSize;
	    if (size < end)
	    	end = size;
	    return Objects.subList(0, end);
	  }

	  public int getRemotePageSize(){
	    return RemotePageSize;
	  }

	  public int getRemoteTotalPageNum(){
	    return RemoteTotalPageNums;
	  }

	  public int getSize(){
	    if (Objects == null){
	    	return 0;
	    }
	    return Objects.size();
	  }

	  public List<T> getThePage(int thePage){
	    return getThePage(thePage, PageSize);
	  }

	  public List<T> getThePage(int thePage, int pageSize){
	    CurPage = thePage;
	    if (pageSize <= 0)
	    	pageSize = PageSize;
	    int size = getSize();
	    if (size == 0){
	    	return null;
	    }
	    int start = pageSize * (thePage - 1);
	    if (start < 0)
	    	start = 0;
	    int end = thePage * pageSize;
	    if (size < end)
	    	end = size;
	    CurStartIndex = start;
	    return Objects.subList(start, end);
	  }

	  public String getTimeStamp(){
	    if (TimeStamp == null){
	    	return "";
	    }
	    return TimeStamp;
	  }

	  public int getTotalPageNum(){
	    return 1 + (RemoteTotalPageNums * RemotePageSize - 1) / PageSize;
	  }

	  public boolean isNeedLoadRemoteDymaicPage(){
	    if (CurPage < 1 + (size() - 1) / PageSize){
	    	return false;
	    }
	    return true;
	  }

	  public boolean isNeedLoadRemoteStaticPage(){
	    if (CurPage < 1 + (size() - 1) / PageSize){
	    	return false;
	    }
	    if (CurRemotePage < RemoteTotalPageNums)
	        return true;
	    return false;
	  }

	  public boolean isNoMoreDatas(){
	    return HasNoMoreDatas;
	  }

	  public Iterator<T> iterator(){
	    if (Objects != null){
	    	return Objects.iterator();
	    }
	    return null;
	  }

	  public T remove(int index){
	    if ((index < 0) || (index >= size())){
	    	return null;
	    }
	    return Objects.remove(index);
	  }

	  public void setCurRemotePage(int PageNum){
	    CurRemotePage = PageNum;
	  }

	  public void setLimit(int limit){
	    DefaultLimitListLen = limit;
	  }

	  public void setNoMoreDatas(boolean hasNoMoreDatas){
	    HasNoMoreDatas = hasNoMoreDatas;
	  }

	  public void setObjects(List<T> list){
	    Objects = list;
	  }

	  public void setPageSize(int len){
	    if (len >= 1)
	      PageSize = len;
	  }

	  void setRemotePageSize(int PageSize){
	    RemotePageSize = PageSize;
	  }

	  public void setRemoteTotalPageNum(int PageNums){
	    RemoteTotalPageNums = PageNums;
	  }

	  public void setTimeStamp(String timeStamp){
	    TimeStamp = timeStamp;
	  }

	  public int size(){
	    if (Objects != null){
	    	return Objects.size();
	    }
	    return 0;
	  }

	  public Object[] toArray(){
	    if (Objects != null){
	    	return Objects.toArray();
	    }
	    return null;
	  }
}