package com.android.speedsearch.ui;

import com.android.speedsearch.R;
import com.android.speedsearch.service.SpeedSearchService;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class AboutActivity extends Activity{
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about);
  }

  public boolean onKeyDown(int keyCode, KeyEvent event){
    if (keyCode == KeyEvent.KEYCODE_BACK){
      SpeedSearchService.promptExitApp(this);
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }
}