package com.android.speedsearch.ui;

import java.util.ArrayList;
import java.util.List;

import com.android.speedsearch.R;
import com.android.speedsearch.db.MyDBhelper;
import com.android.speedsearch.util.ExpandableInfo;
import com.android.speedsearch.util.ExpressBaseInfo;
import com.android.speedsearch.util.ExpressSheet;
import com.android.speedsearch.util.ImagesNotifyer;
import com.android.speedsearch.util.SpTitleBar;
import com.android.speedsearch.util.SsSearchBarSimple;
import com.android.speedsearch.util.SsVerticalCharacterBar;
import com.android.speedsearch.util.Validator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Toast;

public class ExpressListActivity extends Activity implements AbsListView.OnScrollListener{
	private ImagesNotifyer imagesNotifyer;
	private boolean isEditState = false;
    private List<ExpressBaseInfo> mListAll;
	private List<ExpressBaseInfo> mListTop;
	private static final int TOP_EXPRESS_CODE = 20;
	boolean EffectiveExpressId = true;
	private boolean bShowTopExpress = true;
	private List<String> mListTopExpressName;
	private SsVerticalCharacterBar mcharacters;
	private SsSearchBarSimple searchBar;
	private SpTitleBar titleBar;
	private FrameLayout mFLayoutContent;
	private ExpandableListView mListView;
	private List<ExpandableInfo> expandableInfos;
	private ExpressSheet expressSheet;
	private SsExpandableListAdapter mAdapter;
	
	protected void onCreate(Bundle arg0){
	    super.onCreate(arg0);
	    setContentView(R.layout.expresslist);
	    imagesNotifyer = new ImagesNotifyer();
	    isEditState = getIntent().getBooleanExtra("isEdit", false);
	    mListTop = MyDBhelper.getInstance(this).getTopExpress();
	    EffectiveExpressId = getIntent().getBooleanExtra("hasEffectiveExpressId", true);
	    bShowTopExpress = getIntent().getBooleanExtra("isShowTopExpress", true);
	    mListTopExpressName = new ArrayList<String>();
	    if (mListTop != null){
	      while(mListTop.iterator().hasNext()){
		      ExpressBaseInfo info = (ExpressBaseInfo)mListTop.iterator().next();
		      mListTopExpressName.add(info.getCompanyName());
	      }
	    }
	    findViews();
	    setListeners();
	        this.handler = new Handler()
	        {
	          public void handleMessage(Message paramAnonymousMessage)
	          {
	            try
	            {
	              int i = paramAnonymousMessage.what;
	              switch (i)
	              {
	              default:
	              case 16711689:
	                while (true)
	                {
	                  super.handleMessage(paramAnonymousMessage);
	                  return;
	                  ExpressListActivity.this.imagesNotifyer.UpdateView((String)paramAnonymousMessage.obj);
	                }
	              case 16721581:
	              }
	            }
	            catch (Exception localException1)
	            {
	              while (true)
	              {
	                localException1.printStackTrace();
	                continue;
	                try
	                {
	                  if ("ExpressListActivity".equals((String)paramAnonymousMessage.obj))
	                    ExpressListActivity.this.finish();
	                }
	                catch (Exception localException2)
	                {
	                }
	              }
	            }
	          }
	        };
	        HardWare.getInstance(this).RegisterHandler(this.handler, hashCode());
	        this.mListAll = getExpressData(null);
	        showExpressInfo();
	        return;

	}
	
	private void findViews(){
	    titleBar = ((SpTitleBar)findViewById(R.id.titlebar));
	    searchBar = ((SsSearchBarSimple)findViewById(R.id.express_search_bar));
	    mcharacters = ((SsVerticalCharacterBar)findViewById(R.id.lL_characters));
	    mcharacters.showRightCharacters(new Characterlistener(mcharacters));
	    mFLayoutContent = ((FrameLayout)findViewById(R.id.fL_content));
	}
	
	private void setListeners(){
	    searchBar.setHint(getResources().getString(R.string.exinputex));
	    searchBar.setSearchClickListener(new ExpressSearchClickListener(searchBar));
	    searchBar.clear();
	    titleBar.setCurActivity(this);
	    if (isEditState)
	    	titleBar.setTitle(getResources().getString(R.string.exaddex));
	    else
	    	titleBar.setTitle(getResources().getString(R.string.exlistex));
	    titleBar.setLeftOperationClickListener(new View.OnClickListener(){
	        public void onClick(View v){
	          ExpressListActivity.this.finish();
	        }
	    });
	    return;
	}
	
	private int getClickedPosInListvew(String character){
	    int result = 0;
	    boolean ismatched = false;
	    if ((Validator.isEffective(character)) && (expandableInfos != null)){
	    	int count = expandableInfos.size();
	    	for(int i=0;i<count;i++){
	    		if(!ismatched){
		    		ExpandableInfo info = (ExpandableInfo)this.expandableInfos.get(i);
		  	      	if (!character.equals(info.getTitle())){
		  	    	  result += 1 + info.getSize();
		  	      	}else{
		  	      		ismatched = true;
		  	      		break;
		  	      	}
	    		}
	    	}
	    }
	    return result;
	}
	
	private List<ExpressBaseInfo> getExpressData(String keyWord){
		List<ExpressBaseInfo> list = null;
	    if ((keyWord == null) || (keyWord.length() == 0)){
	    	list = MyDBhelper.getInstance(this).getAllExpress();
	    }else{
	    	list = MyDBhelper.getInstance(this).getAllExpress(keyWord);
	    }
	    if ((list == null) || (list.size() <= 0))
	    	list = null;
	    return list;
	}
	
	private void showExpressInfo(){
	    mFLayoutContent.removeAllViews();
	    if ((mListAll == null) || (mListAll.size() <= 0)){
	      mcharacters.setVisibility(View.GONE);
	      mFLayoutContent.setVisibility(View.GONE);
	      Toast.makeText(getApplicationContext(), R.string.exlistnoinfo, Toast.LENGTH_SHORT).show();
	      return;
	    }
	    mcharacters.setVisibility(View.VISIBLE);
	    mFLayoutContent.setVisibility(View.VISIBLE);
	    if (expressSheet == null)
	      expressSheet = new ExpressSheet();
	    else{
	    	expressSheet.clear();
	    }
	    expressSheet.setExpressList(mListAll);
	    expressSheet.setTopExpressList(mListTop);
	    expandableInfos = expressSheet.getCates(Boolean.valueOf(bShowTopExpress));
	    if (mListView == null){
	        mListView = new ExpandableListView(this);
	        mListView.setCacheColorHint(R.color.transparent);
	        mListView.setDivider(getResources().getDrawable(R.color.bg_list_divider_color));
	        mListView.setChildDivider(getResources().getDrawable(R.color.bg_list_divider_color));
	        mListView.setDividerHeight(1);
	        mListView.setFadingEdgeLength(0);
	        mListView.requestFocus();
	        mListView.setOnScrollListener(this);
	        if (this.mAdapter == null)
	          this.mAdapter = new WccExpandableListAdapter(this.mListView, LayoutInflater.from(getApplicationContext()), this.handler, this.imagesNotifyer, 7, true, 2130837976, 2130837977, this.context);
	      }
	      this.mListView.setAdapter(this.mAdapter);
	      this.mFLayoutContent.addView(this.mListView);
	      setListViewListener();
	      this.mAdapter.setData(this.expandableInfos);
	      this.mAdapter.notifyDataSetChanged();
	      expandAll();
	}
	  
	class Characterlistener extends SsVerticalCharacterBar.clickCharacterListenner{
	    protected Characterlistener(SsVerticalCharacterBar wccrightcharacterbar){
	      super();
	    }

	    public void getSelectedCharacter(String selectedCharacter){
	      int i = ExpressListActivity.this.getClickedPosInListvew(selectedCharacter);
	      if (i != -1)
	        ExpressListActivity.this.mListView.setSelection(i);
	    }
	}
	
	class ExpressSearchClickListener extends SsSearchBarSimple.SearchClickListener{
	    ExpressSearchClickListener(SsSearchBarSimple ssSearchBar){
	      super();
	    }

	    public void startSearch(String KeyWords, int searchType){
	      ExpressListActivity.this.mListAll = ExpressListActivity.this.getExpressData(KeyWords);
	      ExpressListActivity.this.showExpressInfo();
	      ExpressListActivity.this.searchBar.hideButton();
	    }

	    public void storeKeywords(String keyword){
	    }
	}
}