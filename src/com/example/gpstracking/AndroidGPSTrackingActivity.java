package com.example.gpstracking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AndroidGPSTrackingActivity extends Activity {
	
	Button btnShowLocation;
	public static final String PREFS_NAME = "MyPrefsFile";
	private static SMSReceiver p_rcvr;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);

        
        boolean firstrun = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstrun)
        {				       
        	
        	getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .edit()
            .putBoolean("firstrun", false)
            .commit();

        	
            Intent j = new Intent(this, SavePword.class);
            j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(j); 
        
        }	
        
        p_rcvr = new SMSReceiver();
        p_rcvr.sentsmsornot(1);
    }    
}