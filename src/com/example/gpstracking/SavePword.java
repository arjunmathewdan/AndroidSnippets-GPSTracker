package com.example.gpstracking;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SavePword extends Activity {
	
	Button Savebtn;		
	IntentFilter intentFilter;
	EditText pword;
	
	public static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_PASSWORD = "password";

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_pword); 
                
        pword = (EditText) findViewById(R.id.savepwordtxt);
        Savebtn = (Button) findViewById(R.id.savepwordbtn);        
        
        Savebtn.setOnClickListener(new OnClickListener() 
        {			
				public void onClick(View v) 
				{
					String text = pword.getText().toString();
					if (text!=null && text.length()>0) 
					{      
   				        /* Save the Security Code for the Application */
				        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
				        .edit()
			            .putString(PREF_PASSWORD, text)
			            .commit();
			            Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();			            				                  																																										
					}
					
					finish();
				}
		});
        
    }
}