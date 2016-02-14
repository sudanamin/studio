package com.example.sudan.util;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by ammostafa on 09/02/2016.
 */
public class NoData extends Activity {

    //private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // setContentView();
        Toast.makeText(this.getBaseContext(), "this is no data activity No Data",
                Toast.LENGTH_LONG).show();



    }
}
