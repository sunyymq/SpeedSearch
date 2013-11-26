package com.android.speedsearch.util;

import java.util.List;

public abstract interface ExpandableInfo{
  public abstract List<Object> getChildInfos();

  public abstract int getSize();

  public abstract String getSubTitle();

  public abstract String getTitle();
}