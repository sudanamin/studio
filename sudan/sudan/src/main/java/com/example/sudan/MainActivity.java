package com.example.sudan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sudan.adapters.ViewPagerAdapter;
import com.example.sudan.util.GetData;
import com.example.sudan.util.SlidingTabLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import com.example.sudan.R;
//import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Edwin on 15/02/2015.
 */
@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {

	// Declaring Your View and Variables
	///////////////////////////////////////////////

	// SlidingTabLayout tabs;
	JSONArray tabbb = null;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	// please enter your sender id
	String SENDER_ID = "1084508405640";

	static final String TAG = "GCMDemo";
	GoogleCloudMessaging gcm;
	String TAG_tabs = "titles";
	TextView mDisplay;
	Context context;
	String regid;

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	///////////////////////////////////////////////

	Toolbar toolbar;
	ViewPager pager;
	ViewPagerAdapter adapter;
	SlidingTabLayout tabs;
	CharSequence Titles[] = { "Home ", " Events ", " Montana ", " Alhilal " };
	int Numboftabs = 4;

	// @SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getTabsName getabs = new getTabsName(this, adapter, pager);
		String tabsurl = "http://sudan.besaba.com/titlesjson.php";
		getabs.execute(tabsurl);

		/////////////////////////////////////////////////////////////////

		// mDisplay = (TextView) findViewById(R.id.display);
		context = getApplicationContext();
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);
			Toast.makeText(getApplicationContext(), regid, Toast.LENGTH_LONG).show();
			if (regid.isEmpty()) {
				new RegisterBackground().execute();
			}

		}

		////////////////////////////////////////////////////////////////

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	/*
	 * public void settadapter(String[] tabsname){ String tabsName[] = tabsname;
	 * int Numboftabs = tabsName.length; CharSequence Titles[]; Titles =
	 * tabsName; adapter = new ViewPagerAdapter(getSupportFragmentManager(),
	 * Titles, Numboftabs); // return tabsName; }
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	class RegisterBackground extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String msg = "";
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(context);
				}
				regid = gcm.register(SENDER_ID);
				msg = "Dvice registered, registration ID=" + regid;
				Log.d("111", msg);
				sendRegistrationIdToBackend();

				// Persist the regID - no need to register again.
				storeRegistrationId(context, regid);
			} catch (IOException ex) {
				msg = "Error :" + ex.getMessage();
			}
			return msg;
		}

		@Override
		protected void onPostExecute(String msg) {
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

		}

		private void sendRegistrationIdToBackend() {
			// Your implementation here.

			String url = "http://sudan.besaba.com/tech/getdevice.php";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("regid", regid));
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				HttpResponse httpResponse = httpClient.execute(httpPost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		private void storeRegistrationId(Context context, String regId) {
			final SharedPreferences prefs = getGCMPreferences(context);
			int appVersion = getAppVersion(context);
			Log.i(TAG, "Saving regId on app version " + appVersion);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(PROPERTY_REG_ID, regId);
			editor.putInt(PROPERTY_APP_VERSION, appVersion);
			editor.commit();
		}
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}

		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private SharedPreferences getGCMPreferences(Context context) {

		return getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	////////////////////////////////////////////////////////////////////////////

	class getTabsName extends AsyncTask<String, Void, JSONObject> {

		public String[] tabsName;
		public String[] tabsN = { "hassab ", " asdf ", " sdf ", " sdf " };
	//	String TAG_tabs = "titles";
		ViewPagerAdapter adapter;
		Activity activity;
		ViewPager pager;

		public getTabsName(Activity activity, ViewPagerAdapter adapter, ViewPager pager) {

			this.adapter = adapter;
			// this.gridView = gridView;
			this.activity = activity;
			// this.TAG_tabs = arrofjson;
			this.pager = pager;

		}

		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urll = params[0];
			// Making HTTP request
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(urll);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				// reader.reset();
				// is.reset();
				is.close();
				json = sb.toString();
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}

			// try parse the string to a JSON object
			try {
				jObj = new JSONObject(json);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}

			// return JSON String
			return jObj;

		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);

			try {
				// Getting Array of Contacts
				tabbb = result.getJSONArray( TAG_tabs );
				tabsName = new String[tabbb.length()];

				// looping through All Contacts
				for (int i = 0; i < tabbb.length(); i++) {

					JSONObject c = tabbb.getJSONObject(i);

					// Storing each json item in variable

					String name = c.getString("title");
					tabsName[i] = name;

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// tabsN = tabsName;
			// Log.d("tabsN", tabsN[0]);
			// System.out.println("tabbbbnnnnn"+tabsN);
			// settadapter(tabsname);
			// gettabs();
			// . gettabs(tabsname);
			/*
			 * int Numboftabs = tabsname.length; CharSequence Titles[]; Titles =
			 * tabsname; // FragmentManager fm =
			 * this.activity.FragmentManager();
			 * 
			 * adapter = new ViewPagerAdapter(getSupportFragmentManager(),
			 * Titles, Numboftabs);
			 */
			// adapter=new ViewPagerAdapter( this.activity, urls,tabsname,ids);
			// gridView.setAdapter(adapter);

			setContentView(R.layout.activity_main);

			// Creating The Toolbar and setting it as the Toolbar for the
			// activity

			System.out.println("kkkkkkkkkkkkkkkk" + tabsName[0]);
			int Numboftabs = tabsName.length;
			CharSequence Titles[];
			Titles = tabsName;

			adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
			// Numboftabs);
			pager = (ViewPager) findViewById(R.id.pager);

			pager.setAdapter(adapter);
			toolbar = (Toolbar) findViewById(R.id.tool_bar);
			// getSupportActionBar().setDisplayShowHomeEnabled(true);
			// getSupportActionBar().setIcon(R.drawable.ic_action);
			// toolbar.setic();
			toolbar.setNavigationIcon(R.drawable.splash);
			TextView title = (TextView) findViewById(R.id.title);

			Typeface type = Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf");
			Spanned text = Html.fromHtml("   <i>MARKAVip</i> ");

			title.setTypeface(type);
			title.setText(text);
			title.setTextSize(17);
			title.setTextColor(0xFFffffff);
			//toolbar.setTitle("amin");
			setSupportActionBar(toolbar);
			// toolbar.settext

			// Creating The ViewPagerAdapter and Passing Fragment Manager,
			// Titles
			// fot the Tabs and Number Of Tabs.
			// Activity activity = (Activity) context;

			// new GetData().execute(jsonurl);
			// new GetData().execute(jsonurl);
			tabs = (SlidingTabLayout) findViewById(R.id.tabs);

			tabs.setViewPager(pager);

			// String [] tab = getabs.gettabs();
			// Log.d("main activity gettabs", tab[0]);
			// int Numboftabs = tab.length;
			// CharSequence Titles[];
			// Titles = tab;
			// AsyncTask<String, String, String> tabsnames =
			// getabs.execute(tabsurl);

			// adapter = new ViewPagerAdapter(getSupportFragmentManager(),
			// Titles,
			// Numboftabs);

			// Assigning ViewPager View and setting the adapter
			// pager = (ViewPager) findViewById(R.id.pager);
			// pager.setAdapter(adapter);
			//
			// Assiging the Sliding Tab Layout View

			// tabs.setDistributeEvenly(true); // To make the Tabs Fixed set
			// this true,
			// This makes the tabs Space Evenly in
			// Available width

			// Setting Custom Color for the Scroll bar indicator of the Tab View
			/*
			 * tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
			 * 
			 * @Override public int getIndicatorColor(int position) { return
			 * getResources().getColor(R.color.tabsScrollColor); } });
			 */
			tabs.setSelectedIndicatorColors(getResources().getColor(R.color.indicator));

			tabs.setDividerColors(getResources().getColor(R.color.Dividercolor));
			tabs.setBackgroundColor(getResources().getColor(R.color.aminbackground));
			// tabs.setDividerColors(getResources().getColor(
			// R.color.Dividercolor));
			/*
			 * tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
			 * 
			 * @Override public int getIndicatorColor(int position) { return
			 * getResources().getColor(R.color.white); //define any color in xml
			 * resources and set it here, I have used white }
			 * 
			 * @Override public int getDividerColor(int position) { return
			 * getResources().getColor(R.color.white); } });
			 */
			// Setting the ViewPager For the SlidingTabsLayout

		}
	}
}
