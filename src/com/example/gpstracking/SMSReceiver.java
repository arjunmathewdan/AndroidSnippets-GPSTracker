package com.example.gpstracking;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import android.content.SharedPreferences;


public class SMSReceiver extends BroadcastReceiver
{
	
	public static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_PASSWORD = "password";
	// GPSTracker class
	GPSTracker gps;
	private static GetNumber GN;
	private static int SENTSMS = 0;
	AndroidGPSTrackingActivity acti;
	
    public void setNumber(GetNumber number) {
    	String tag = "AMD";
    	Log.d(tag, "INSIDE setNumber()");
    	this.GN = number;
    	if (this.GN == null)
    		Log.d(tag,"setNumber - GN null");
        
    }
    
    public void sentsmsornot (int option) {
    	this.SENTSMS = option;   	
    }
			
	  public static String[] split(final String string, final String delim)
	  {
			     // get the count of delim in string, if count is > limit 
			     // then use limit for count.  The number of delimiters is less by one
			     // than the number of elements, so add one to count.
			     int count = 2;
			     int dontproceed = 0;

			     String strings[] = new String[count];
			     int begin = 0;

			     for (int i = 0; i < count; i++)
			     {
			        // get the next index of delim
			        int end = string.indexOf(delim, begin);
			        
			        // if the end index is -1 or if this is the last element
			        // then use the string's length for the end index
			        if (end == -1 || i + 1 == count) {
			        	if(end == -1 && i == 0) {
			        		dontproceed = 1;
			        		break;
			        	}
			        	end = string.length();
			        }
			           

			        // if end is 0, then the first element is empty
			        if (end == 0)
			           strings[i] = "empty";
			        else
			           strings[i] = string.substring(begin, end);

			        // update the begining index
			        begin = end + 1;
			     }
			     if (dontproceed == 0) {
			    	 return strings;
			     } else {
			    	 strings[0] = "abcd";
			    	 strings[1] = "abcd";
			    	 return strings;
			     }
			  }

	
	@Override
	public void onReceive(Context context, Intent intent) 
    {		
		//---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String tag = "AMD";
        
        String str = "";
        String ContactName = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            Log.d(tag, "pdus.length = " + (pdus.length));
            Log.d(tag, "msgs.length = " + (msgs.length));
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                //str += "SMS from " + msgs[i].getOriginatingAddress();                     
                //str += " :";
                str += msgs[i].getMessageBody().toString();
                //str += "\n";        
            }
            //---display the new SMS message---

            String string_array[] = new String[2];
            string_array = split(str, ":");

            str += "\n";
            Toast.makeText(context, "SMS FROM " + msgs[0].getOriginatingAddress() + " :" + str, Toast.LENGTH_SHORT).show();
                 

            //ContactName += msgs[0].getMessageBody().toString();
            ContactName += string_array[1];

            Bundle extras = new Bundle();
            extras.putString("Name", ContactName);
            extras.putString("Number", msgs[0].getOriginatingAddress());
            
            Intent ii = new Intent(context, GetNumber.class);
            ii.putExtras(extras);
            ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
            String secret_code = "";
            SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);   
            secret_code = (pref.getString(PREF_PASSWORD, null));
            
            if(this.SENTSMS == 1)
            {
            	if(string_array[0].equals(secret_code))
            	{             	
            		// create class object
            		gps = new GPSTracker(context);

            		// 	check if GPS enabled		
            		if(gps.canGetLocation()){
            			
            			double latitude = gps.getLatitude();
            			double longitude = gps.getLongitude();
		        	
            			// \n is for new line
            			Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	

                    	// SEND BACK SMS HERE....
                    	if(this.GN != null)
                    	{
                    		this.GN.sendSMS("5556", "F...U...");
                    	} else {
                    		Log.d(tag,"GN NULLLLLLLL");
                    	}
            		
            		}else{
            			// can't get location
            			// GPS or Network is not enabled
            			// Ask user to enable GPS/network in settings
            			gps.showSettingsAlert();
            		}            		
            	}            	
            }
        }                   
	}		
}
