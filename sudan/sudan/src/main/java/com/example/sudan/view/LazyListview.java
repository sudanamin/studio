package com.example.sudan.view;



import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.example.sudan.FullScreenViewActivity;
import com.example.sudan.R;
import com.example.sudan.details;
import com.example.sudan.adapters.LazyAdapter;
import com.example.sudan.adapters.ViewPagerAdapter;

import com.example.sudan.util.GetData;
import com.example.sudan.util.SlidingTabLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.annotation.SuppressLint;
//import android.tut.json.JSONParser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


//import com.rogcg.gridviewexample.R;


public class LazyListview extends Fragment {
	
	/*static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";*/
	private static String jsonurl = "http://sudan.besaba.com/brandsjson.php?maintitle=";
	LazyAdapter adapter;
    GridView gridView;
    String names[];
	String urls[];
	String imageUrls[];
	private static final String TAG_url = "url";
	protected static final String TAG = "this is main activity!";
  /*  String sublist[];
	String catg_name[]= {"Catg 1","Catg 2","Catg 3","Catg 4"};
	String catg_descp[]= {"Catg 1","Catg 2","Catg 3","Catg 4"};
	String hash_tag[];
	//Integer[] images;
	String names[];
	String urls[];
	String imageUrls[];*/
	static InputStream is = null;
	static JSONObject jObj = null;
	private static final String TAG_NAME = "name";
	static String json = "";
	JSONArray images = null;
	String TAG_mainimage = "mainimage";
	JSONArray mainimage = null;
	LinearLayout layou;
	LinearLayout also_layou;
	private static String also_url = "http://sudan.besaba.com/productjsontest.php?id=";
	Context con;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_lazy_listview,container,false);
        Context thiscontext = container.getContext();
        String tabname = getArguments().getString("title", "man");
        
        
        System.out.println("title from fragment " +tabname);
        
        gridView = (GridView)v.findViewById(R.id.gridview);
        
        also_layou = (LinearLayout) v.findViewById(R.id.also);
        also alsolike = new also(thiscontext);
        alsolike.execute(also_url + "" + 18);
        
        
        
    	layou = (LinearLayout) v.findViewById(R.id.linear);
        
        String imaurl = "http://sudan.besaba.com/mainimage.txt";
        
       
        mainimage ima = new mainimage(thiscontext);
        ima.execute(imaurl);
        
        
      GetData getdata = new GetData(gridView, adapter , getActivity(),tabname);
       // new GetData().execute(jsonurl);
        getdata.execute(jsonurl+""+tabname);
		TextView txtyour = (TextView) v.findViewById(R.id.most);
		Typeface type = Typeface.createFromAsset(getContext().getAssets(),"Lato-Regular.ttf");
		txtyour.setTypeface(type);
        return v;
                      
    }
     
    @Override
    public void onDestroy()
    {
    	gridView.setAdapter(null);
        super.onDestroy();
    }
    
    class mainimage extends AsyncTask<String, Void, JSONObject> {

		public String[] imageurl;
		public String[] tabsN = { "hassab ", " asdf ", " sdf ", " sdf " };
	//	String TAG_tabs = "titles";
		ViewPagerAdapter adapter;
		Activity activity;
		ViewPager pager;
		Context cc;

		public mainimage(Context cc) {
			this.cc = cc;
		}
		/*public mainimage(Activity activity, ViewPagerAdapter adapter, ViewPager pager) {

			this.adapter = adapter;
			// this.gridView = gridView;
			this.activity = activity;
			// this.TAG_tabs = arrofjson;
			this.pager = pager;

		}*/

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
				mainimage = result.getJSONArray( TAG_mainimage );
				imageurl = new String[mainimage.length()];

				// looping through All Contacts
			//	for (int i = 0; i < mainimage.length(); i++) {

					JSONObject c = mainimage.getJSONObject(0);

					// Storing each json item in variable

					String name = c.getString("mainima");
					//imageurl[0] = name;

					ImageView imageView = new ImageView(cc);
			        DisplayMetrics metrics = new DisplayMetrics();
			        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			        int width = metrics.widthPixels; 
			        
			        int height = metrics.heightPixels *30/100;
			     	LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
				    imageView.setLayoutParams(parms);
				    String url = name;
				    //String url = "http://sudan.besaba.com/brands/slide_01.jpg";
				    imageView.setScaleType(ScaleType.FIT_XY);
				    Glide.with(cc)
					   .load(url)
					   .into(imageView);
				    
				    layou.addView(imageView);
				    
			} catch (JSONException e) {
				e.printStackTrace();
			}
		
                  
	

		}
	}
    
    
	private class also extends AsyncTask<String, Void, JSONObject> {
		Context cc;

		public also(Context cc) {
			this.cc = cc;
		}

		@Override

		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected JSONObject doInBackground(String... params) {

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

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);

			// JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			// JSONObject json = result;

			try {
				// Getting Array of Contacts
				String tag_image = "products";
				images = result.getJSONArray(tag_image);
				names = new String[images.length()];
				urls = new String[images.length()];

				// looping through All Contacts
				for (int i = 0; i < images.length(); i++) {

					JSONObject c = images.getJSONObject(i);

					// Storing each json item in variable

					String name = c.getString(TAG_NAME);
					names[i] = name;

					String url = c.getString(TAG_url);
					urls[i] = url;
					System.out.println("I am here url " + url);

					// Phone number is agin JSON Object
					/*
					 * JSONObject phone = c.getJSONObject(TAG_PHONE); String
					 * mobile = phone.getString(TAG_PHONE_MOBILE); String home =
					 * phone.getString(TAG_PHONE_HOME); String office =
					 * phone.getString(TAG_PHONE_OFFICE);
					 */

					// creating new HashMap
					// HashMap<String, String> map = new HashMap<String,
					// String>();

					// adding each child node to HashMap key => value

					// map.put(TAG_NAME, name);
					// map.put(TAG_url, url);

					// adding HashList to ArrayList
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			// adapter=new LazyAdapter( this.getActivity(), urls,names);
			// gridView.setAdapter(adapter);
			System.out.println("names -" + names.length);
			for (int ii = 0; ii < names.length; ii++) {
				CircleImageView imageView = new CircleImageView(cc);
				// imageView.setBorderWidth(5);
				// imageView.setBorderColor(27);
				// imageView.addShadow();
				//imageView.setScaleType(ScaleType.FIT_CENTER);
				final int sdk = android.os.Build.VERSION.SDK_INT;
				
				imageView.setId(ii);
			//	Display display = getWindowManager().getDefaultDisplay();
				// ImageView iv = (LinearLayout) findViewById(R.id.left);
				 DisplayMetrics metrics = new DisplayMetrics();
			        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			        int width = metrics.widthPixels; 
			        
			        int height = metrics.heightPixels *20/100;
			     	LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
				//parms.leftMargin = 10;
				parms.leftMargin = 0;
			   // String TAG;
				Log.d(TAG, "width =:  "+width+"  ,hewght=:"+height);
				parms.setMargins(1, 1, 1, 1);
				parms.setMarginStart(1);
				imageView.setBorderWidth(3);
				
				imageView.setBorderColor(552290466);
				//int f = 0xff70D517;
				//imageView.setBorderColor(f);
				
			/*	imageView.setBackgroundColor(2290466);
				imageView.setBorderColor(2290466);
				imageView.setPadding(5,5,5,5);*/
			//	imageView.setBorderOverlay(false);
				
				
				
			    imageView.setLayoutParams(parms);
			    
				imageView.setOnClickListener(new OnImageClickListener(ii+10,cc));
			
				  Glide.with(cc) .load(urls[ii]) .into(imageView);
				 
				/*
				 * Bitmap theBitmap; try { theBitmap = Glide. with(cc).
				 * load(urls[ii]). asBitmap(). into(100, 100). // Width and
				 * height get();
				 * 
				 * imageView.setImageBitmap(theBitmap); } catch
				 * (InterruptedException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } catch (ExecutionException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 * 
				 */
				 also_layou.addView(imageView);

			}

		}


	}
	class OnImageClickListener implements OnClickListener {

		String _postion;
		Context cc;
		// constructor
		public OnImageClickListener(int position,Context cc) {
			this._postion = "" + position;
            this.cc = cc;
		}

		@Override
		public void onClick(View v) {
			// on selecting grid view image
			// launch full screen activity
		    String brandid="18";
			Intent i = new Intent(this.cc, com.example.sudan.products.class);
		    i.putExtra("brand_id", brandid);
			this.cc.startActivity(i);
			}
			
		}

	}


 



