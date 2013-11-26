package com.android.speedsearch.ui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.google.demo.CaptureActivity;
import com.android.speedsearch.adapter.Speedbutton;
import com.android.speedsearch.adapter.SpeedsearchView;
import com.android.speedsearch.R;
public class Launcher extends Activity implements Speedbutton.OnClickListener  {

    private SpeedsearchView layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        layout = (SpeedsearchView)findViewById(R.id.specail_view);
        layout.setOnItemClick(this);
    }

    @Override
    public void onClick(View v, boolean checked) {
        //String text = ((Speedbutton)v).getTextString();
        int i = v.getId();
        switch (i) {
		case R.id.speedbutton1:
	        Intent intent = new Intent();
	        intent.setClass(this, CaptureActivity.class);
	        startActivity(intent);
			break;
		case R.id.speedbutton2:
			Toast.makeText(this, "speedbutton2", Toast.LENGTH_SHORT).show();
			break;
		case R.id.speedbutton3:
			Toast.makeText(this, "speedbutton3", Toast.LENGTH_SHORT).show();
			break;
		case R.id.speedbutton4:
			Toast.makeText(this, "speedbutton4", Toast.LENGTH_SHORT).show();
			break;
		case R.id.speedbutton5:
			Toast.makeText(this, "speedbutton5", Toast.LENGTH_SHORT).show();
			break;
		case R.id.speedbutton6:
			Toast.makeText(this, "speedbutton6", Toast.LENGTH_SHORT).show();
			break;
		case R.id.speedbutton7:
			Toast.makeText(this, "speedbutton7", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
        //Toast.makeText(this, String.valueOf(i) + checked, Toast.LENGTH_SHORT).show();
    }
    public static void changeLight(ImageView imageView, int brightness) {
    	ColorMatrix cMatrix = new ColorMatrix();
    	cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
    	brightness,// ¸Ä±äÁÁ¶È
    	0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
    	imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
    	}
}