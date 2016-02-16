package il.co.runnerdevice;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
//http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
	// Sharedpref file name
	private static final String PREF_NAME = "RunnerDevicePref";
	
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";
	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";
	public static final String KEY_Token = "token";
	public static final String KEY_Refresh = "refresh_token";
	public static final String KEY_CurrentDate = "currentdate";
	public static final String KEY_ExpiredDate = "expireddate";
	public static final String KEY_Roles = "roles";
	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(String name, String refresh,String currentdate,String expireddate,String token,String roles){
		// Storing in pref
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_NAME, name);
		editor.putString(KEY_EMAIL, name);
		editor.putString(KEY_Token, token);
		editor.putString(KEY_Refresh, refresh);
		editor.putString(KEY_CurrentDate, currentdate); 
		editor.putString(KEY_ExpiredDate, expireddate);
		editor.putString(KEY_Roles, roles); 
		// commit changes
		editor.commit();
	}	
	public void setRefreshToken( String refresh,String currentdate,String expireddate,String token){
		
		editor.putString(KEY_Token, token);
		editor.putString(KEY_Refresh, refresh);
		editor.putString(KEY_CurrentDate, currentdate); 
		editor.putString(KEY_ExpiredDate, expireddate);
		// commit changes
		editor.commit();
	}	
	
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			// user is not logged in redirect him to Login Activity
			RedirctToLogin();
		}	
	}
	
	public void RedirctToLogin()
	{
		Intent i = new Intent(_context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// Staring Login Activity
		_context.startActivity(i);
	}
	
	/**
	 * Get stored session data
	 * */
	public boolean  IsValidToken(){
		Date local = new Date();
		Date  expiredDate;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String eDate=pref.getString(KEY_ExpiredDate, null);
		
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		local = cal.getTime();
		
		try {
			expiredDate = format.parse(eDate);
			   if(local.before(expiredDate))
				   	return true;
			 	return false;
		} 
		catch (Exception e) {
		    e.printStackTrace();
	return false;
		}
		
	}
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		user.put(KEY_Token, pref.getString(KEY_Token, null));
		user.put(KEY_Refresh, pref.getString(KEY_Refresh, null));
		user.put(KEY_CurrentDate, pref.getString(KEY_CurrentDate, null));
		user.put(KEY_ExpiredDate, pref.getString(KEY_ExpiredDate, null));
		user.put(KEY_Roles, pref.getString(KEY_Roles, null));
		// return user
		return user;
	}
	
	public String GetToken(){
		return  pref.getString(KEY_Token, null);
	}
	
	public String GetCurrentDate(){
		return  pref.getString(KEY_CurrentDate, null);
	}
	
	public String GetExpiredDate(){
		return  pref.getString(KEY_ExpiredDate, null);
	}
	
	public String GetRefreshKeyToken(){
		return  pref.getString(KEY_Refresh, null);
	}
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
		
		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Staring Login Activity
		_context.startActivity(i);
	}
	
	// Get Login State
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}


}
