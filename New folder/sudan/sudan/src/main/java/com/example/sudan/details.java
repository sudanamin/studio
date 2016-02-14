package com.example.sudan;

//import com.mikhaellopez.circularimageview.CircularImageView;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
//import com.mikhaellopez.circularimageview.CircularImageView;
import com.example.sudan.adapters.LazyAdapter;
import com.example.sudan.view.CircleImageView;

import android.view.View.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.annotation.SuppressLint;
//import android.tut.json.JSONParser;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
//import de.hdodenhof.circleimageview.sample.R;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("ResourceAsColor")
public class details extends Activity {
	// import com.rogcg.gridviewexample.R;

	// public class LazyListview extends Fragment {
	protected static final String TAG = "amin!";
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	private static String jsonurl = "http://sudan.besaba.com/detailjsontest.php?id=";
	private static String also_url = "http://sudan.besaba.com/productjsontest.php?id=";
	// private static String jsonurl = "http://sudan.besaba.com/images.txt";
	LazyAdapter adapter;
	GridView gridView;
	String sublist[];
	String catg_name[] = { "Catg 1", "Catg 2", "Catg 3", "Catg 4" };
	String catg_descp[] = { "Catg 1", "Catg 2", "Catg 3", "Catg 4" };
	String hash_tag[];
	// Integer[] images;
	String names[];
	String urls[];
	String imageUrls[];
	
	String also_names [];
	String also_urls [];

	
	
	private static final String TAG_NAME = "name";
	private static final String TAG_url = "url";

	private static final String TAG_IMAGES = "detailimages";

	JSONArray images = null;
	
	JSONArray alsoimages = null;
	LinearLayout layou;
	
	LinearLayout also_layou;
	Context con;

	@Override
	// public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
	// container, @Nullable Bundle savedInstanceState) {
	// View v
	// =inflater.inflate(R.layout.activity_lazy_listview,container,false);
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.detail);
		layou = (LinearLayout) findViewById(R.id.linear);
		also_layou = (LinearLayout) findViewById(R.id.also);
		// setContentView(R.layout.products);
		// gridView = (GridView)v.findViewById(R.id.gridview);
		// gridView = (GridView)findViewById(R.id.gridview);
		con = getApplicationContext();

		Intent i = getIntent();
		String pid = i.getStringExtra("product_id");
		int product_id = Integer.parseInt(pid);

		Log.e("product id is ", "" + product_id);

		GetdetailsData getdetailsdata = new GetdetailsData(this);
		also alsolike = new also(this);
		// new GetData().execute(jsonurl);
        int brand_id = com.example.sudan.products.current_brand_id;
		// getdata.execute(jsonurl);
		getdetailsdata.execute(jsonurl + "" + product_id);
		alsolike.execute(also_url + "" + brand_id);

	}

	// return v;

	@Override
	public void onDestroy() {
		// gridView.setAdapter(null);
		super.onDestroy();
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.lazy_listview, menu); return true; }
	 */

	private class GetdetailsData extends AsyncTask<String, Void, JSONObject> {
		Context cc;

		public GetdetailsData(Context cc) {
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

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);

			// JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			// JSONObject json = result;

			try {
				// Getting Array of Contacts
				images = result.getJSONArray(TAG_IMAGES);
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
		     	ImageView imageView = new ImageView(cc);
				// imageView.setBorderWidth(5);
				// imageView.setBorderColor(27);
				// imageView.addShadow();
			//	imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setId(ii);
				Display display = getWindowManager().getDefaultDisplay();
				// ImageView iv = (LinearLayout) findViewById(R.id.left);
				int width = display.getWidth() * 60 / 100; // ((display.getWidth()*20)/100)
				int height = display.getHeight() * 40 / 100;// ((display.getHeight()*30)/100)
				LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
			    imageView.setLayoutParams(parms);
			    //imageView.
			   
			    
				imageView.setOnClickListener(new OnImageClickListener(ii));
			
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
				layou.addView(imageView);

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
				if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					also_layou.setBackgroundDrawable( getResources().getDrawable(R.drawable.rounded_corner) );
				} else {
					also_layou.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
				}
				imageView.setId(ii);
				Display display = getWindowManager().getDefaultDisplay();
				// ImageView iv = (LinearLayout) findViewById(R.id.left);
				int width = display.getWidth() * 25 / 100; // ((display.getWidth()*20)/100)
				int height = display.getHeight() * 15 / 100;// ((display.getHeight()*30)/100)
				LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
				//parms.leftMargin = 10;
				parms.leftMargin = 10;
			   // String TAG;
				Log.d(TAG, "width =:  "+width+"  ,hewght=:"+height);
				parms.setMargins(width /15, width /10, width/15, width/40);
				imageView.setBorderWidth(3);
				
				imageView.setBorderColor(R.color.aminindicator);
				//int f = 0xff70D517;
				//imageView.setBorderColor(f);
				
			/*	imageView.setBackgroundColor(2290466);
				imageView.setBorderColor(2290466);
				imageView.setPadding(5,5,5,5);*/
			//	imageView.setBorderOverlay(false);
				
				
				
			    imageView.setLayoutParams(parms);
			    
				imageView.setOnClickListener(new OnImageClickListener(ii+10));
			
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

		// constructor
		public OnImageClickListener(int position) {
			this._postion = "" + position;

		}

		@Override
		public void onClick(View v) {
			// on selecting grid view image
			// launch full screen activity
			if (Integer.parseInt(_postion) <10){// to chacke if you came from images details or you might also like
			Intent i = new Intent(details.this, FullScreenViewActivity.class);
			i.putExtra("position", _postion);
			Log.e("posiojjjjjjjjjjtin is ", "" + _postion);
			i.putExtra("images", urls);
			details.this.startActivity(i);
			}
			else // you came from you might also like
			{}
		}

	}
}
