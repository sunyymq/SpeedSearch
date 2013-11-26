package com.android.speedsearch.ui;

import com.android.speedsearch.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CodebarTextshow extends Activity{
	  private int ScanType;
	  private String TAG = "CodebarTextshow";
	  private String barcode;
	  private Bitmap bmp;
	  private String bmp_name;
	  private Button btn_back;
	  private ImageView img_barcode;
	  private LinearLayout lL_drug;
	  private LinearLayout lL_express;
	  private TextView tv_barcode_string;
	  private TextView tv_barcode_type;
	  private String type;
	  
	  protected void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.codebartextshow);
	    findViews();
	    setListeners();
	    getData();
	    setData();
	  }
	  private void findViews(){
	    btn_back = ((Button)findViewById(R.id.btn_wcc39and128_back));
	    img_barcode = ((ImageView)findViewById(R.id.img_barcode));
	    lL_express = ((LinearLayout)findViewById(R.id.lL_express));
	    lL_drug = ((LinearLayout)findViewById(R.id.lL_drug));
	    tv_barcode_type = ((TextView)findViewById(R.id.tv_barcode_type));
	    tv_barcode_string = ((TextView)findViewById(R.id.tv_barcode_string));
	  }
	  
	  private void setListeners(){
	    btn_back.setOnClickListener(new View.OnClickListener(){
	      public void onClick(View v){
	      CodebarTextshow.this.finish();
	      }
	    });
	    lL_express.setOnClickListener(new View.OnClickListener(){
	      public void onClick(View v){
	        Intent intent = new Intent(CodebarTextshow.this, ExpressListActivity.class);
	        intent.putExtra("ExpressId", CodebarTextshow.this.barcode);
	        CodebarTextshow.this.startActivity(intent);
	      }
	    });
	    lL_drug.setOnClickListener(new View.OnClickListener(){
	      public void onClick(View v){
	        Intent intent = new Intent(CodebarTextshow.this, DrugAdminCodeActivity.class);
	        intent.putExtra("scan_result", CodebarTextshow.this.barcode);
	        intent.putExtra("barcode_bitmap", CodebarTextshow.this.bmp_name);
	        intent.putExtra("result_type", CodebarTextshow.this.type);
	        intent.putExtra("scan_type", CodebarTextshow.this.ScanType);
	        CodebarTextshow.this.startActivity(intent);
	      }
	    });
	  }
	  
	  private void getData(){
	    Intent intent = getIntent();
	    barcode = intent.getStringExtra("scan_result");
	    type = intent.getStringExtra("result_type");
	    bmp_name = intent.getStringExtra("barcode_bitmap");
	    ScanType = intent.getIntExtra("scan_type", 0);
	  }
	  
	  private void setData(){
		 Intent intent = getIntent();
	     bmp = (Bitmap) intent.getParcelableExtra("BitmapImage");
	     if(bmp != null){
	    	 img_barcode.setImageBitmap(bmp);
	     }else{
	    	 img_barcode.setVisibility(View.GONE);
	     }
	     tv_barcode_type.setText(type);
	     tv_barcode_string.setText(barcode);
	  }
	  
	  protected void onDestroy(){
	    if (bmp != null){
	      img_barcode.setImageBitmap(null);
	      bmp.recycle();
	      bmp = null;
	    }
	    super.onDestroy();
	  }

	  protected void onResume(){
	    super.onResume();
	  }
}