package com.android.speedsearch.util;

import com.android.speedsearch.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Gravity;
import android.view.View;

public class SsVerticalCharacterBar extends LinearLayout{
	  final String[] charactersArr = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	  Context mcontext;
	  
	  public SsVerticalCharacterBar(Context context, AttributeSet attrs){
	    super(context, attrs);
	    mcontext = context;
	  }

	  public void showRightCharacters(clickCharacterListenner listener){
	    int count = charactersArr.length;
	    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    params.weight = 1.0F;
	    params.gravity = 1;
	    for (int i = 0; i<count; i++){
	      TextView textView = new TextView(mcontext);
	      textView.setTextColor(getResources().getColor(R.color.wcc_color_7));
	      textView.setTextSize(15.0F);
	      textView.setText(charactersArr[i]);
	      textView.setGravity(Gravity.CENTER);
	      textView.setLayoutParams(params);
	      textView.setTag(Integer.valueOf(i));
	      textView.setOnClickListener(listener);
	      addView(textView);
	    }
	    return;
	  }

	  public abstract class clickCharacterListenner implements View.OnClickListener{
	    public clickCharacterListenner(){
	    }

	    public abstract void getSelectedCharacter(String paramString);

	    public final void onClick(View v){
	      int i = ((Integer)v.getTag()).intValue();
	      getSelectedCharacter(charactersArr[i]);
	    }
	  }
}