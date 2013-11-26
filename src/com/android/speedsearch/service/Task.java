package com.android.speedsearch.service;

public class Task{
  public static final int TASK_SEARCH_HISTORY = 1;
  public static final int TASK_SEARCH_RESULT = 0;
  public static final int TASK_SEARCH_PRODUCT = 2;
  private int taskID;

  public Task(int id){
    taskID = id;
  }

  public int getTaskID(){
    return taskID;
  }
}