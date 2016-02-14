package com.example.sudan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sudan.adapters.LazyAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

public  class GetData extends AsyncTask<String, Void, JSONObject> {

   
    static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	//private static String jsonurl = "http://sudan.besaba.com/brandsjson.txt";
	//private static String jsonurl = "http://sudan.besaba.com/images.txt";
    LazyAdapter adapter;
    GridView gridView;
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
	
	public GetData(GridView gridView,LazyAdapter adapter, Activity activity ,String arrofjson){
		
		this.adapter = adapter;
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
		
        
        
        adapter=new LazyAdapter( this.activity, urls,names,ids);
		//gridView.setnumro
		gridView.setMinimumHeight(1000);
        gridView.setAdapter(adapter);

        

       
        }
  
}