package com.android.speedsearch.util;

import java.util.ArrayList;
import java.util.List;

import com.android.speedsearch.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class SsSearchBarSimple extends LinearLayout{
	private String TAG = "SsSearchBarSimple";
	protected Button btnSearch;
	protected SpImageView imgCancel;
	protected SpImageView imgSearch;
	private InputMethodManager inputManager;
	private boolean isShowImgCancel = true;
	protected int keywordType = 0;
	private List<String> localList;
	protected Context mcontext;
	protected AutoCompleteTextView mkeywordInput;
	private List<String> serverList;
	private String tmpKeyword = "";
  
	public SsSearchBarSimple(Context context){
		super(context);
		initData(context);
		findViews(context);
		setListeners();
	}

	public SsSearchBarSimple(Context context, AttributeSet attrs){
		super(context, attrs);
		initData(context);
		findViews(context);
		setListeners();
	}
	  
	public void Release(){
    	inputManager = null;
    	localList = null;
    	serverList = null;
	}

	public void clear(){
    	mkeywordInput.clearFocus();
    	mkeywordInput.setText("");
	}
	
	protected void initData(Context context){
    	mcontext = context;
    	inputManager = ((InputMethodManager)mcontext.getSystemService("input_method"));
	}
	
	protected void findViews(Context context){
		View localView = LayoutInflater.from(context).inflate(R.layout.sssearchbar_simple, this, true);
    	mkeywordInput = ((AutoCompleteTextView)localView.findViewById(R.id.input_keyword));
    	btnSearch = ((Button)localView.findViewById(R.id.btn_search));
    	imgCancel = ((SpImageView)localView.findViewById(R.id.img_cancel));
    	imgSearch = ((SpImageView)localView.findViewById(R.id.img_search));
	}
	
	public void closeSoftkb(){
	    try{
	      inputManager.hideSoftInputFromWindow(this.mkeywordInput.getWindowToken(), 0);
	    }catch (Exception e){
	    }
	}
	 
	public void getFocus(){
	    mkeywordInput.requestFocus();
	    mkeywordInput.setText("");
	}

	public CharSequence getText(){
	    return mkeywordInput.getText();
	}

	public void hideButton(){
	    String content = mkeywordInput.getText().toString().trim();
	    btnSearch.setVisibility(View.GONE);
	    mkeywordInput.clearFocus();
	    if (Validator.isEffective(content)){
	      mkeywordInput.setText(content);
	      if (isShowImgCancel)
	        imgCancel.setVisibility(View.VISIBLE);
	    }else{
	    	mkeywordInput.setText("");
	    }
	    closeSoftkb();
	    return;
	}
	
	public void setHint(String text){
	    mkeywordInput.setHint(text);
	}

	public void setImageCancel(boolean show){
	    isShowImgCancel = show;
	}

	public void setImageSearch(boolean show){
	    if (show)
	    	imgSearch.setVisibility(View.VISIBLE);
	    else {
	    	imgSearch.setVisibility(View.GONE);
		}
	    return;
	}

	public void setKeywords(List<String> list1, List<String> list2){
	    localList = list1;
	    serverList = list2;
	    ArrayAdapter<String> keywordsAdapter = new ArrayAdapter<String>(mcontext, R.layout.historyitem, list1);
	    mkeywordInput.setAdapter(keywordsAdapter);
	}
	
	public void setSearchClickListener(View.OnClickListener listner){
	    btnSearch.setOnClickListener(listner);
	}

	public void setSearchType(int searchType){
	    keywordType = searchType;
	}
	
	protected void setListeners(){
    	mkeywordInput.setOnKeyListener(new View.OnKeyListener(){
    		public boolean onKey(View v, int keyCode, KeyEvent event){
    			if (keyCode == KeyEvent.KEYCODE_ENTER )
    				closeSoftkb();
    			return false;
    		}
    	});
    	mkeywordInput.setOnTouchListener(new View.OnTouchListener(){
    		public boolean onTouch(View v, MotionEvent event){
    			mkeywordInput.showDropDown();
    			return false;
    		}
    	});
    	mkeywordInput.addTextChangedListener(new TextWatcher(){
    		int end = 0;
    		int start = 0;
    		CharSequence str_text = "";

    		public void afterTextChanged(Editable s){
    			if (Validator.isEffective(s.toString().trim())){
    				btnSearch.setText(R.string.exsearch);
    				if (isShowImgCancel)
    					imgCancel.setVisibility(View.VISIBLE);
    			}else{
					btnSearch.setText(R.string.button_cancel);
					imgCancel.setVisibility(View.GONE);		
    			}
    			start = mkeywordInput.getSelectionStart();
    			end = mkeywordInput.getSelectionEnd();
    			try{
    				if (str_text.length() >= 21){
    					Toast.makeText(mcontext, R.string.exsearchhint, Toast.LENGTH_SHORT).show();
    					((Editable)str_text).delete(start - 1, end);
    					int tempSelection = start;
    					mkeywordInput.setText(str_text);
    					mkeywordInput.setSelection(tempSelection);
    				}
    			}catch (Exception e){
    			}
    			return;
    		}

    		public void beforeTextChanged(CharSequence s, int start, int count, int after){
    			SsBeforeTextChanged(s, start, count, after);
    			str_text = s;
    		}

    		public void onTextChanged(CharSequence s, int start, int before, int count){
    			String newword = mkeywordInput.getText().toString();
    			if (newword.contains("'")){
    				mkeywordInput.setText(tmpKeyword);
    				mkeywordInput.setSelection(start);
    			}else{
    				tmpKeyword = newword;
    			}
    			return;
    		}
    	});
    	imgCancel.setOnClickListener(new View.OnClickListener(){
    		public void onClick(View v){
    			mkeywordInput.setText("");
    		}
    	});
    	mkeywordInput.setOnFocusChangeListener(new View.OnFocusChangeListener(){
    		public void onFocusChange(View v, boolean hasFocus){
    			if (hasFocus)
    				btnSearch.setVisibility(0);
    		}
    	});
	}
	
	protected void SsBeforeTextChanged(CharSequence s, int start, int count, int after){
	  if ((s.length() < 1) && (localList != null) && (serverList != null)){
	    ArrayList<String> list = new ArrayList<String>(localList);
	    if (this.serverList.size() > 0)
	    	list.addAll(localList.size(), serverList);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mcontext, R.layout.historyitem, list);
	    mkeywordInput.setAdapter(adapter);
	  }
	}
	
	public abstract class SearchClickListener implements View.OnClickListener{
	    protected SearchClickListener(){
	    }

	    public final void onClick(View v){
	      closeSoftkb();
	      String keyword = SsSearchBarSimple.this.getText().toString().trim();
	      storeKeywords(keyword);
	      startSearch(keyword, keywordType);
	    }

	    public abstract void startSearch(String string, int start);

	    public abstract void storeKeywords(String key);
	}
}