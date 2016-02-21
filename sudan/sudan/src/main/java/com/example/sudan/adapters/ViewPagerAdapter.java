package com.example.sudan.adapters;

import com.example.sudan.tabs.Tab2;
import com.example.sudan.tabs.Tab3;
import com.example.sudan.tabs.Tab4;
import com.example.sudan.view.LazyListview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.LinkedHashMap;

/**
 * Created by Edwin on 15/02/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter  {


	LinkedHashMap<Integer, Fragment> mFragmentCache = new LinkedHashMap<>();

	CharSequence Titles[]; // This will Store the Titles of the Tabs which are
							// Going to be passed when ViewPagerAdapter is
							// created
	// int icons[] = {R.drawable}
	int NumbOfTabs; // Store the number of tabs, this will also be passed when
					// the ViewPagerAdapter is created

	// Build a Constructor and assign the passed Values to appropriate values in
	// the class
	public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
		super(fm);

		this.Titles = mTitles;
		this.NumbOfTabs = mNumbOfTabsumb;

	}

	// This method return the fragment for the every position in the View Pager
	@Override
	public Fragment getItem(int position) {
	//	switch (position) {
//		case 0: // if the position is 0 we are returning the First tab
	//	{


		Fragment f = mFragmentCache.containsKey(position) ? mFragmentCache.get(position)
				:   newInstance(Titles[position]);
		Log.e("test", "getItem:" + position + " from cache" + mFragmentCache.containsKey
				(position));
	/*	if (savedState == null || f.getArguments() == null) {
			Bundle bundle = new Bundle();
			bundle.putString("title", (String) Titles[position]);
			f.setArguments(bundle);
			Log.e("test", "setArguments:" + Titles[position]);
		} else if (!mFragmentCache.containsKey(position)) {
			f.setInitialSavedState(savedState);
			Log.e("test", "setInitialSavedState:" + position);
		}*/
		//f= newInstance(Titles[position]);
		mFragmentCache.put(position, f);
		return f;
		//	LazyListview lazy = newInstance(Titles[position]);
			
		//	return lazy;

	}

	// This method return the titles for the Tabs in the Tab Strip

	@Override
	public CharSequence getPageTitle(int position) {
		return Titles[position];
	}

	// This method return the Number of tabs for the tabs Strip

	@Override
	public int getCount() {
		return NumbOfTabs;
	}
	public static LazyListview newInstance(CharSequence tit) {
		LazyListview myFragment = new LazyListview();
        String t = (String) tit;
	    Bundle args = new Bundle();
	    args.putString("title", t);
	    myFragment.setArguments(args);

	    return myFragment;
	}
}