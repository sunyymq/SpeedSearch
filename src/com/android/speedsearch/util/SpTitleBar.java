package com.android.speedsearch.util;

import com.android.speedsearch.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpTitleBar extends LinearLayout{
	  Activity curActivity;
	  Button leftOperation;
	  View rightDefaultOperation;
	  ViewGroup rightOperationLayout;
	  TextView subtitle;
	  View thirdtitle;
	  TextView title;
	  ViewGroup titleLayout;
	  
	  public SpTitleBar(Context context, AttributeSet attrs){
	    super(context, attrs);
	    findViews(context);
	  }
	  
	  private void findViews(Context paramContext){
	    View localView = LayoutInflater.from(paramContext).inflate(R.layout.titlebar, this, true);
	    leftOperation = ((Button)localView.findViewById(2131364853));
	    titleLayout = ((ViewGroup)localView.findViewById(2131364854));
	    title = ((TextView)localView.findViewById(2131364855));
	    subtitle = ((TextView)localView.findViewById(2131364856));
	    thirdtitle = localView.findViewById(2131364857);
	    rightOperationLayout = ((ViewGroup)localView.findViewById(2131364858));
	    rightDefaultOperation = ((Button)localView.findViewById(2131364859));
	    leftOperation.setOnClickListener(new View.OnClickListener(){
	      public void onClick(View v){
	        if (SpTitleBar.this.curActivity != null)
	        	SpTitleBar.this.curActivity.finish();
	      }
	    });
	  }
	  
	  public String getSubTitle(){
	    return subtitle.getText().toString();
	  }

	  public String getTitle(){
	    return title.getText().toString();
	  }

	  public void rightOperationPerformClick(){
	    if ((rightDefaultOperation != null) && (rightDefaultOperation.isShown()))
	      rightDefaultOperation.performClick();
	  }
	  
	  public void setCurActivity(Activity activity){
	    curActivity = activity;
	  }

	  public void setLeftOperation(String str){
	    leftOperation.setText(str);
	  }

	  public void setLeftOperation(String str, View.OnClickListener listener){
	    leftOperation.setText(str);
	    leftOperation.setOnClickListener(listener);
	  }

	  public void setLeftOperation(String str, View.OnClickListener listener, int resid){
	    setLeftOperation(str, listener);
	    setLeftOperationBackgroundResource(resid);
	  }

	  public void setLeftOperationBackgroundResource(int resid){
	    leftOperation.setBackgroundResource(resid);
	  }

	  public void setLeftOperationClickListener(View.OnClickListener listener){
	    leftOperation.setOnClickListener(listener);
	  }

	  public void setLeftOperationVisible(boolean visible){
	    if (visible)
	      leftOperation.setVisibility(View.VISIBLE);
	    else
	    	leftOperation.setVisibility(View.INVISIBLE);
	    return;
	  }

	  public void setRightOperation(View view){
	    if (view != null){
	      rightOperationLayout.removeAllViews();
	      rightDefaultOperation = view;
	      rightOperationLayout.addView(view);
	    }
	  }

	  public void setRightOperation(String str){
	    if (Validator.isEffective(str))
	      rightDefaultOperation.setVisibility(View.VISIBLE);
	    if ((rightDefaultOperation instanceof Button))
	      ((Button)this.rightDefaultOperation).setText(str);
	    if ((rightDefaultOperation instanceof TextView))
	        ((TextView)this.rightDefaultOperation).setText(str);

	  }

	  public void setRightOperation(String str, View.OnClickListener listener){
	    if ((rightDefaultOperation instanceof Button))
	      ((Button)rightDefaultOperation).setText(str);
	    if ((rightDefaultOperation instanceof TextView))
		  ((TextView)rightDefaultOperation).setText(str);
	    rightDefaultOperation.setOnClickListener(listener);
	    if (listener != null)
	      rightDefaultOperation.setVisibility(View.VISIBLE);
	    return;
	  }

	  public void setRightOperationClickListener(View.OnClickListener listener){
	    rightDefaultOperation.setOnClickListener(listener);
	    if (listener != null)
	      rightDefaultOperation.setVisibility(View.VISIBLE);
	  }

	  public void setRightOperationVisible(boolean visible){
	    if (visible)
	    	rightOperationLayout.setVisibility(View.VISIBLE);
	    else {
	    	rightOperationLayout.setVisibility(View.INVISIBLE);
		}
	    return;
	  }

	  public void setSubTitle(String str){
	    subtitle.setText(str);
	    subtitle.setVisibility(View.VISIBLE);
	  }

	  public void setTitle(String str){
	    title.setText(str);
	  }

	  public void setTitleClickable(boolean clickable){
	    titleLayout.setClickable(clickable);
	  }

	  public void setTitleListener(View.OnClickListener listener){
	    titleLayout.setOnClickListener(listener);
	  }

	  public void setTitleTrim(String str, int maxbytelen){
	    title.setText(DataConverter.TrimLongerString(str, maxbytelen));
	  }

	  public void showThirdTitle(boolean show){
	    if (show)
	    	thirdtitle.setVisibility(View.VISIBLE);
	    else {
	    	thirdtitle.setVisibility(View.GONE);
		}
	    return;
	  }
}