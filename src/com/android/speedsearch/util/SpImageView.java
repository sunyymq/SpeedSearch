package com.android.speedsearch.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SpImageView extends ImageView{
  public SpImageView(Context context){
    super(context);
  }

  public SpImageView(Context context, AttributeSet attr){
    super(context, attr);
  }

  protected void onDraw(Canvas canvas){
    try{
      super.onDraw(canvas);
    }catch (Exception e){
        e.printStackTrace();
    }
  }
}