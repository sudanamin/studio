package com.example.sudan.adapters;



import com.bumptech.glide.Glide;
import com.example.sudan.R;
//import com.squareup.picasso.Picasso;
//import com.rogcg.gridviewexample.R;
//import com.rogcg.gridviewexample.MainActivity.MyAdapter.Item;
//import com.example.sudan.SquareImageView;
import com.example.sudan.details;
import com.example.sudan.products;
import com.example.sudan.view.SquareImageView;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//import com.example.sudan.FullScreenViewActivity;
//import info.androidhive.imageslider.adapter.GridViewImageAdapter.OnImageClickListener;
 

 
public class LazyAdapter extends BaseAdapter {
     
    private Activity activity;
    private String[] data;
    private String[] ids;
    private String[] items_name;
    private static LayoutInflater inflater=null;
   // public ImageLoader imageLoader;
     
    public LazyAdapter(Activity a, String[] d,String[] catg_name,String [] id) {
        activity = a;
        
        data=d;
        ids = id;
        items_name = catg_name;
        
        
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.length;
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
     
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        
        
        ImageView picture;
        TextView name;
        Button note;

        if(convertView == null)
            vi = inflater.inflate(R.layout.gridview_item, null);
 
      /*  TextView name=(TextView)vi.findViewById(R.id.title);
        TextView descp = (TextView) vi.findViewById(R.id.artist);
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        
        name.setText(items_name[position]);
        descp.setText(items_descp[position]);*/
        
       /* ProgressBar pb = (ProgressBar)vi.findViewById(R.id.progress);
        imageLoader.DisplayImage(data[position], image,pb);*/

         picture = (SquareImageView)vi.findViewById(R.id.picture);
        name = (TextView)vi.findViewById(R.id.text);
        note = (Button)vi.findViewById(R.id.note);
        //note.setVi
        Double i = Math.random();
        if(i<0.5 ){note.setText("New");note.setVisibility(Button.VISIBLE);}


      //  picture.setImageResource(item.drawableId);
        name.setText(items_name[position]);
        
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "Lato-Regular.ttf");
        name.setTypeface(typeface);
        
        String ccurrent_activity = activity.getLocalClassName();
        Log.e("activity iiiiiiiii is ", " i am hereeeeeeee "  +ccurrent_activity);
        
        Picasso.with(activity.getApplicationContext())
		   .load(data[position])
		   .into(picture);
        
        picture.setOnClickListener(new OnImageClickListener(position));
        
        
        return vi;
    }
    class OnImageClickListener implements OnClickListener {

		int _postion ;

		// constructor
		public OnImageClickListener(int position) {
			this._postion  = position;
			 
		}

		@Override
		public void onClick(View v) {
			// on selecting grid view image
			// launch full screen activity
			
			String current_activity = activity.getLocalClassName();
			String MainActivity = "MainActivity";
			String productactivity = "products";
			Intent i ;
			if (current_activity.equals(MainActivity)){
			//if (true){
				   i = new Intent(activity, products.class); 
	        	//	i.putExtra("position", _postion);
	    		Log.e("activity iiiiiiiii is ", " i am comming from "  +current_activity);
	         	//	i.putExtra("images", data);
	     		i.putExtra("brand_id", ids[_postion]);
	     		activity.startActivity(i);
			}
			if (current_activity.equals(productactivity)){
				   i = new Intent(activity, details.class);
		        	//	i.putExtra("position", _postion);
		    		Log.e("activity iiiiiiiii is ", " i am comming from "  +current_activity);
		         	//	i.putExtra("images", data);
		     		i.putExtra("product_id", ids[_postion]);
		     		activity.startActivity(i);
			}			  
			
			
		}

	}
}