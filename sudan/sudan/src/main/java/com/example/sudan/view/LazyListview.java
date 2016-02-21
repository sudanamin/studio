package com.example.sudan.view;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sudan.R;
import com.example.sudan.adapters.LazyAdapter;
import com.example.sudan.adapters.ViewPagerAdapter;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//import android.tut.json.JSONParser;


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
	RelativeLayout layou;

	RelativeLayout maintxt;

	String tabname;
	RecyclerView gridrv;
	Typeface typeface ;
	Typeface type;
	Typeface viewsale;
	TextView mt;
	Button mainbutton;
	ImageView mainim;
	int height;
	int width;

	SimpleStringRecyclerViewAdapter sadapter;

	LinearLayout also_layou;
	private static String also_url = "http://sudan.besaba.com/productjsontest.php?id=";
	Context con;
	private int mIndex;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_lazy_listview, container, false);
        Context thiscontext = container.getContext();
         tabname = getArguments().getString("title", "man");
        mainim = (ImageView) v.findViewById(R.id.mainimag);
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		 width = metrics.widthPixels;

		 height = metrics.heightPixels ;
        System.out.println("title from fragment " +tabname);
        
        //gridView = (GridView)v.findViewById(R.id.gridview);
		 gridrv = (RecyclerView) v.findViewById(R.id.recyclerview);




		if (savedInstanceState == null) {
			tabname = getArguments().getString("title");

		} else {
			tabname = savedInstanceState.getString("title");

		}
		 mt = (TextView) v.findViewById (R.id.txtframe);
		mainbutton =(Button) v.findViewById(R.id.btnAddTitle );
		typeface = Typeface.createFromAsset(getActivity().getAssets(), "Zurich.ttf");
		viewsale = Typeface.createFromAsset(getActivity().getAssets(), "Lato-Regular.ttf");
        also_layou = (LinearLayout) v.findViewById(R.id.also);
        also alsolike = new also(thiscontext);
        alsolike.execute(also_url + "" + 18);
        
        
        
    	layou = (RelativeLayout) v.findViewById(R.id.main);
		maintxt = (RelativeLayout) v.findViewById(R.id.maintxt);
		// mainim = (ImageView) v.findViewById(R.id.mainim);
        
        String imaurl = "http://sudan.besaba.com/mainimage.txt";
		//String imaurl = "http://sudan.besaba.com/titlesjson.php";
        
       
        mainimage ima = new mainimage(thiscontext);
        ima.execute(imaurl);



		//SimpleStringRecyclerViewAdapter sadapter = new LazyListview.SimpleStringRecyclerViewAdapter(getActivity(),"http://sudan.besaba.com/brands/org.jpg","amin","46");
		//sadapter.notifyDataSetChanged();
		gridrv.setAdapter(new LazyListview.SimpleStringRecyclerViewAdapter(getActivity(),"http://sudan.besaba.com/brands/org.jpg","amin","46"));
		gridrv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		//
        gridrv.setLongClickable(true);
		GridData g = new GridData(gridrv , getActivity(),tabname);
       //new GetData().execute(jsonurl);
		g.execute(jsonurl + "" + tabname);

		//sadapter.notifyDataSetChanged();
		TextView txtyour = (TextView) v.findViewById(R.id.most);
		 type = Typeface.createFromAsset(getContext().getAssets(),"Lato-Regular.ttf");
		txtyour.setTypeface(type);
        return v;
                      
    }
     
    @Override
    public void onDestroy()
    {
    	//gridView.setAdapter(null);
        super.onDestroy();
    }
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("title", tabname);
		Log.d("test", "call onSaveInstanceState:" + tabname);
	}





	public static class SimpleStringRecyclerViewAdapter
			extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

		private Activity activity;
		private String[] data;
		private String[] ids;
		private String[] items_name;

		private List<String> ddata;
		private List<String> iids;
		private List<String> iitems_name;


		private static LayoutInflater inflater=null;

		private final TypedValue mTypedValue = new TypedValue();
		private int mBackground;

		public SimpleStringRecyclerViewAdapter(FragmentActivity activity, String s, String amin, String s1) {
		}
		//	private List<String> mValues;

		public static class ViewHolder extends RecyclerView.ViewHolder {
			public String mBoundString;

			public final View mView;
			public final ImageView picture;
			public final Button note;
			public final TextView name;


			public ViewHolder(View view) {
				super(view);
				mView = view;

				picture = (SquareImageView) mView.findViewById(R.id.picture);
				name = (TextView) mView.findViewById(R.id.text);
				note = (Button) mView.findViewById(R.id.note);
			}
		}




		public SimpleStringRecyclerViewAdapter(Context context, List<String> d,List<String> catg_name,List<String> id) {
			context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
			//this.activity = (Activity) context;

			this.ddata=d;
			this.iids = id;
			this.iitems_name = catg_name;



			//System.out.println( " gggggggggggggggg"+ data[0]+"iiiiiiiiiiiiiii"+items_name[0]);
			//inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}







		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.gridview_item, parent, false);
			//view.setBackgroundResource(mBackground);
			return new ViewHolder(view);


		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, int position) {
			//holder.mBoundString = (iitems_name[position]);
			holder.name.setText(iitems_name.get(position));

			holder.name.setVisibility(View.VISIBLE);

			//Typeface type = Typeface.createFromAsset(activity.getAssets(),"Lato-Regular.ttf");

			//holder.name.setTypeface(type);
			Double i = Math.random();
			if(i<0.5 ){holder.note.setText("New");holder.note.setVisibility(Button.VISIBLE);}



		//	System.out.println(" GGGGGGGGGGG" + data[0] + "IIIIIIIIIIIII" + items_name[0]);
					Picasso.with(holder.picture.getContext())
							.load(this.ddata.get(position))
							.placeholder(R.drawable.img_def)
							.into(holder.picture);


			holder.picture.setVisibility(View.VISIBLE);
			/*Glide.with(holder.picture.getContext())
					.load(Cheeses.getRandomCheeseDrawable())
					.fitCenter()
					.into(holder.picture);*/
				}

				@Override
				public int getItemCount() {
					return (null != ddata ? ddata.size() : 0);
				}



		@Override
		public long getItemId(int position) {
			return position;
		}
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

					//String name = c.getString("mainima");
				   String name =   "http://sudan.besaba.com/titles/"+tabname;
				//String name =  "http:\/\/sudan.besaba.com\/brands\/slide_01.jpg";
                    System.out.println("hi i am name:"+name+" tab name is"+tabname);
					//imageurl[0] = name;




					//ImageView mainim = new ImageView(cc);
				    // mainim.setImageResource(R.drawable.img_def);
			       /* DisplayMetrics metrics = new DisplayMetrics();
			        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			        int width = metrics.widthPixels; 
			        
			        int height = metrics.heightPixels *20/100;*/
				     int  height_of_main_image = height *28/100;
			     	RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width, height_of_main_image);
				    mainim.setLayoutParams(parms);
				   // String url = name;
				    //String url = "http://sudan.besaba.com/brands/slide_01.jpg";
			    	mainim.setScaleType(ScaleType.FIT_XY);

				    String url = name;



				    Picasso.with(cc)
					    .load(url)

						.placeholder(R.drawable.img_def)
					    .into(mainim);
				    
				   // layou.addView(mainim);




				
				

				//TextView maintext = new TextView(cc);
				//TextView mt = (TextView) v.findViewById (R.id.txtframe);

				/*RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mt.getLayoutParams();
				lp.addRule(RelativeLayout.BELOW,mainim);
				mt.setLayoutParams(lp);*/
				mt.setText("Club L & More");
               // mt.setTextColor(0x347513c);
				//Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "Lato-Regular.ttf");
				mt.setTypeface(typeface);
				mainbutton.setTypeface(viewsale);

				//mt.setTypeface();
				//maintxt.add


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
			/*	 DisplayMetrics metrics = new DisplayMetrics();
			        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			        int width = metrics.widthPixels; 
			        
			        int height = metrics.heightPixels *15/100;*/

				int most_popular = height *15/100;
			     	LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, most_popular);
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

	public  class GridData extends AsyncTask<String, Void, JSONObject> {


		 InputStream is = null;
		 JSONObject jObj = null;
		 String json = "";


		private List<String> ddata;
		private List<String> iids;
		private List<String> iitems_name;
		//private static String jsonurl = "http://sudan.besaba.com/brandsjson.txt";
		//private static String jsonurl = "http://sudan.besaba.com/images.txt";
		LazyAdapter adapter;
		RecyclerView  gridView;
		//   ImageView imageview = null;
		String sublist[];
		String catg_name[]= {"Catg 1","Catg 2","Catg 3","Catg 4"};
		String catg_descp[]= {"Catg 1","Catg 2","Catg 3","Catg 4"};
		String hash_tag[];
		Activity activity;
		//Integer[] images;
		String names[];
		String ids[];
		String urls[];
		String imageUrls[];
		String TAG_IMAGES ;
		private static final String TAG_NAME = "name";
		private static final String TAG_url = "url";
		private static final String TAG_ID = "id";


		JSONArray images = null;

		public GridData(RecyclerView  gridView, Activity activity ,String arrofjson){

			//this.adapter = adapter;
			this.gridView = gridView;
			this.activity = activity;
			this.TAG_IMAGES = arrofjson;

		}
		/*public GetData(GridView gridView,LazyAdapter adapter, Activity activity ,String arrofjson){

                this.adapter = adapter;
                 this.gridView = gridView;
            // 	this.imageview = ima;
                this.activity = activity;
                this.TAG_IMAGES = arrofjson;

                }*/
		@Override
		protected void onPreExecute() {
			super.onPreExecute();



		}
	/*	private void setupRecyclerView(RecyclerView recyclerView) {
			recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
			recyclerView.setAdapter(new LazyListview.SimpleStringRecyclerViewAdapter(this.activity, urls, names, ids));
		}*/

		@Override
		protected JSONObject doInBackground(String...  params) {


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
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				//reader.reset();
				//is.reset();
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
		protected void onPostExecute(JSONObject result)
		{
			super.onPostExecute(result);

			//  JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			//JSONObject json = result;


			try {
				// Getting Array of Contacts
				images = result.getJSONArray(TAG_IMAGES);
				names= new String[images.length()];
				urls= new String[images.length()];
				ids = new String[images.length()];


				// looping through All Contacts
				for(int i = 0; i < images.length(); i++){




					JSONObject c = images.getJSONObject(i);

					// Storing each json item in variable
					String id= c.getString(TAG_ID);
					ids[i]= id;

					String fullname = c.getString(TAG_NAME);
					StringTokenizer tokens = new StringTokenizer(fullname, ".");
					String name = tokens.nextToken();
					names[i]= name;

					String url = c.getString(TAG_url);
					urls[i]= url;








					//	String title = c.getString("title");
					System.out.println("I am here brandid  "+ url);

					// Phone number is agin JSON Object
			/*	JSONObject phone = c.getJSONObject(TAG_PHONE);
				String mobile = phone.getString(TAG_PHONE_MOBILE);
				String home = phone.getString(TAG_PHONE_HOME);
				String office = phone.getString(TAG_PHONE_OFFICE);*/

					// creating new HashMap
					//	HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value

					//			map.put(TAG_NAME, name);
					//			map.put(TAG_url, url);


					// adding HashList to ArrayList

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}


		//	setupRecyclerView(gridView);
			//recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
			//recyclerView.setAdapter(new LazyListview.SimpleStringRecyclerViewAdapter(this.activity, urls, names, ids));






			ArrayList<String> ddata = new ArrayList<>(urls.length);
			ArrayList<String> iids = new ArrayList<>(urls.length);
			ArrayList<String> iitems_name = new ArrayList<>(urls.length);


			int i =0;
			while (ddata.size() < urls.length) {
				ddata.add(this.urls[i]);
				iids.add(this.ids[i]);
				iitems_name.add(this.names[i]);
				i++;
			}


			sadapter = new LazyListview.SimpleStringRecyclerViewAdapter(activity.getBaseContext(), ddata, iitems_name, iids);

			//gridrv.setLayoutManager(new GridLayoutManager(this.activity, 2));
			gridrv.setAdapter(sadapter);
			/*adapter=new LazyAdapter( this.activity, urls,names,ids);
			//gridView.setnumro
			gridView.setMinimumHeight(1000);
			gridView.setAdapter(adapter);*/




		}



	}
}


 



