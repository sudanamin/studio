package com.example.sudan;

import com.example.sudan.adapters.FullScreenImageAdapter;

//import com.example.sudan.FullScreenImageAdapter;
//import com.example.sudan.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class FullScreenViewActivity extends Activity{

	//private Utils utils;
	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);

		viewPager = (ViewPager) findViewById(R.id.pager);

		//utils = new Utils(getApplicationContext());

		Intent i = getIntent();
		String  iSelectedItem = i.getStringExtra("position");
		int position = Integer.parseInt(iSelectedItem);
		
		String [] images = i.getStringArrayExtra("images");

		//adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
		//		utils.getFilePaths());
		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
			 images);
		viewPager.setAdapter(adapter);
         Log.e("posiotin is ", "  "  +position);
		// displaying selected image first
		viewPager.setCurrentItem(position);
		
	}
}
