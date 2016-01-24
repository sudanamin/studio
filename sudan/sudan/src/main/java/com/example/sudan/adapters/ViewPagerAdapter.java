package com.example.sudan.adapters;

import com.example.sudan.tabs.Tab2;
import com.example.sudan.tabs.Tab3;
import com.example.sudan.tabs.Tab4;
import com.example.sudan.view.LazyListview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Edwin on 15/02/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

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
			LazyListview lazy = newInstance(Titles[position]);
			
			return lazy;
/*		}
		case 1: // As we are having 2 tabs if the position is now 0 it must be 1
				// so we are returning second tab
		{
			Tab2 tab2 = new Tab2();
			return tab2;
		}
		case 2: {
			Tab3 tab3 = new Tab3();
			return tab3;
		}
		case 3: {
			Tab4 tab4 = new Tab4();
			return tab4;
		}

		default:
			return null;
		}*/
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