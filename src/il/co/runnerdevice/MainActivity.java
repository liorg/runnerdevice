package il.co.runnerdevice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONObject;





import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.Button;

//http://www.techrepublic.com/blog/software-engineer/calling-restful-services-from-your-android-app/

//https://zackehh.com/andrest-simple-rest-client-implementation-android/
//http://avilyne.com/?p=105
public class MainActivity  extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.my_button).setOnClickListener(this);
        findViewById(R.id.my_login).setOnClickListener(this);
        findViewById(R.id.my_order).setOnClickListener(this);
    }
    public void onClick(View arg0) {
    	Button b = null;
    	 switch(arg0.getId()) {
         case R.id.my_button:
        	  b = (Button)findViewById(R.id.my_button);
        	 b.setClickable(false);
         	new LongRunningGetIO().execute();
         break;
         case R.id.my_login:
        	  b = (Button)findViewById(R.id.my_login);
        	  b.setClickable(false);
        	  new LoginIo().execute();
         break;
         case R.id.my_order:
       	  b = (Button)findViewById(R.id.my_order);
       	  b.setClickable(false);
       	  new OrderIO().execute();
        break;
         
 }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
   
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
private class LoginIo extends AsyncTask <Void, Void, String> {
		
		protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException,JSONException {
	       InputStream in = entity.getContent();
	         StringBuffer out = new StringBuffer();
	         int n = 1;
	         while (n>0) {
	             byte[] b = new byte[4096];
	             n =  in.read(b);
	             if (n>0) out.append(new String(b, 0, n));
	         }
	         return out.toString();
	    }
		
		@Override
		protected String doInBackground(Void... params) {
			 String text = null; String token="";
			 JSONObject  access_token= null;
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		    nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
		    nameValuePairs.add(new BasicNameValuePair("username", "r"));
		    nameValuePairs.add(new BasicNameValuePair("password", "1"));
		    nameValuePairs.add(new BasicNameValuePair("client_id", "ngAutoApp"));
			 HttpClient httpClient = new DefaultHttpClient();
			 HttpContext localContext = new BasicHttpContext();
			 HttpPost httppost = new HttpPost("http://kipodeal.co.il:4545/token");
			 
			 httppost.setHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=UTF-8");
			 try {
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			    } catch (UnsupportedEncodingException e) {
			        e.printStackTrace();
			    }
			 
			// Execute HTTP Post Request
			    try {
			        HttpResponse response = httpClient.execute(httppost);
			        Log.d("Response:" , response.toString());
			        text = getASCIIContentFromEntity(response.getEntity());
			        access_token = new JSONObject(text);
			        token = access_token.getString("access_token");
			    } 
			    catch (JSONException eej) {
			        eej.printStackTrace();
			    }
			    catch (IOException e) {
			        e.printStackTrace();
			    }
			 /*
             HttpGet httpGet = new HttpGet("http://kipodeal.co.il:4545/token");
             String text = null;
             try {
                   HttpResponse response = httpClient.execute(httpGet, localContext);
                   HttpEntity entity = response.getEntity();
                   text = getASCIIContentFromEntity(entity);
             } catch (Exception e) {
            	 return e.getLocalizedMessage();
             }
             */
             return token;
		}	 
		 
		protected void onPostExecute(String results) {
			if (results!=null) {
				TextView et = (TextView)findViewById(R.id.tvToken);
				et.setText(results);
			}
			Button b = (Button)findViewById(R.id.my_login);
			b.setClickable(true);
		}
    }
private class LongRunningGetIO extends AsyncTask <Void, Void, String> {
		
		protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
	       InputStream in = entity.getContent();
	         StringBuffer out = new StringBuffer();
	         int n = 1;
	         while (n>0) {
	             byte[] b = new byte[4096];
	             n =  in.read(b);
	             if (n>0) out.append(new String(b, 0, n));
	         }
	         return out.toString();
	    }
		
		@Override
		protected String doInBackground(Void... params) {
			 HttpClient httpClient = new DefaultHttpClient();
			 HttpContext localContext = new BasicHttpContext();
             HttpGet httpGet = new HttpGet("http://kipodeal.co.il:4545/api/ping/complex");
             String text = null;
             try {
                   HttpResponse response = httpClient.execute(httpGet, localContext);
                   HttpEntity entity = response.getEntity();
                   text = getASCIIContentFromEntity(entity);
             } catch (Exception e) {
            	 return e.getLocalizedMessage();
             }
             return text;
		}	
		
		protected void onPostExecute(String results) {
			if (results!=null) {
				EditText et = (EditText)findViewById(R.id.my_edit);
				et.setText(results);
			}
			Button b = (Button)findViewById(R.id.my_button);
			b.setClickable(true);
		}
    }
private class OrderIO extends AsyncTask <Void, Void, String> {
	
	protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
       InputStream in = entity.getContent();
         StringBuffer out = new StringBuffer();
         int n = 1;
         while (n>0) {
             byte[] b = new byte[4096];
             n =  in.read(b);
             if (n>0) out.append(new String(b, 0, n));
         }
         return out.toString();
    }
	
	@Override
	protected String doInBackground(Void... params) {
		 HttpClient httpClient = new DefaultHttpClient();
		 HttpContext localContext = new BasicHttpContext();
         HttpGet httpGet = new HttpGet("http://kipodeal.co.il:4545/api/orders");
         httpGet.setHeader("Accept","application/json");
         httpGet.setHeader("Content-Type","application/json");
         TextView et = (TextView)findViewById(R.id.tvToken);
         String token=et.getText().toString();
         httpGet.setHeader("Authorization","Bearer "+token );
         String text = null;
         try {
               HttpResponse response = httpClient.execute(httpGet, localContext);
               HttpEntity entity = response.getEntity();
               text = getASCIIContentFromEntity(entity);
         } catch (Exception e) {
        	 return e.getLocalizedMessage();
         }
         return text;
	}	
	
	protected void onPostExecute(String results) {
		if (results!=null) {
			EditText et = (EditText)findViewById(R.id.my_edit);
			et.setText(results);
		}
		Button b = (Button)findViewById(R.id.my_button);
		b.setClickable(true);
	}
}
}



