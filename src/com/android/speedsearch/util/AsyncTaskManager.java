package com.android.speedsearch.util;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.AsyncTask;

public class AsyncTaskManager{
  private static final ExecutorService DATA_TASK_EXECUTOR = Executors.newFixedThreadPool(5);
  private static final ExecutorService IMG_TASK_EXECUTOR = Executors.newFixedThreadPool(15);

  public static void executeAsyncTask(AsyncTask<Object, Object, Object> task, Object obj){
    executeAsyncTask(task, DATA_TASK_EXECUTOR, obj);
  }

  private static void executeAsyncTask(AsyncTask<Object, Object, Object> task, Executor paramExecutor, Object obj){
    //if (Build.VERSION.SDK_INT >= 11)
    //	task.executeOnExecutor(paramExecutor, new Object[] { obj });
      task.execute(new Object[] { obj });
  }

  public static void executeImgAsyncTask(AsyncTask<Object, Object, Object> task, Object obj){
    executeAsyncTask(task, IMG_TASK_EXECUTOR, obj);
  }

  public static void shutdown(){
    DATA_TASK_EXECUTOR.shutdown();
  }
}