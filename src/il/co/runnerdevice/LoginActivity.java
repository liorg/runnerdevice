package il.co.runnerdevice;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
public class LoginActivity extends Activity {
	
	// Email, password edittext
	EditText txtUsername, txtPassword;
	
	// login button
	Button btnLogin;
	
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Session Manager Class
	SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); 
        
        // Session Manager
        session = new SessionManager(getApplicationContext());                
        
        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword); 
        
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        
        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);
        
        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				String username = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();
						
				if(username.trim().length() > 0 && password.trim().length() > 0){
					
					new LoginRunner(username.trim(),password.trim(),session).execute();
					
				}
			else{
					// user didn't entered username or password
					// Show alert asking him to enter the details
					alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
				}
			}
		});
    }
    
    public class LoginRunner extends AsyncTask <Void, Void, String> {
	
    	String _user, _pws;
    	SessionManager _session;
    	JSONObject  _access_token;
        ProgressDialog _dialog;
         
         @Override
         protected void onPreExecute()
         {
             super.onPreExecute();
             _dialog = ProgressDialog.show(LoginActivity.this, "נא המתן", "נא המתן שיתבצע התהליך", true, false);
         }
         
	    public LoginRunner(String user,String pws,SessionManager session){
			super();
			_user=user;
			_pws=pws;
			_session=session;
	    }
	
		@Override
		protected String doInBackground(Void... params) {
			String text = null; String token="";
			 List<NameValuePair> nameValuePairs=CustomHttpClient.GetLoginData(_user, _pws);
			 
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
			        text = CustomHttpClient.GetASCIIContentFromEntity(response.getEntity());
			        _access_token = new JSONObject(text);
			    } 
			    catch (JSONException eej) {
			        eej.printStackTrace();
			    }
			    catch (IOException e) {
			        e.printStackTrace();
			    }
			
             return token;
		}	 
		 
		protected void onPostExecute(String results) {
			_dialog.dismiss();
			String err;
			String errDesc="";
			if(_access_token==null)
			{
				alert.showAlertDialog(LoginActivity.this, "Login failed..", "Error Ocuur", false);
				return;
			}
			try {
				
				
				if(_access_token.has("error")){
					err = _access_token.getString("error");
					errDesc = _access_token.has("error_description")? _access_token.getString("error_description"):"error occur!!!";
					alert.showAlertDialog(LoginActivity.this, "Login failed..", errDesc, false);
				}
				else{
				String token = _access_token.getString("access_token");
				String refresh_token = _access_token.getString("refresh_token");
				
				_session.createLoginSession(token, refresh_token);
				//alert.showAlertDialog(LoginActivity.this, "Ok", "Ok", false);
				// Staring MainActivity
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
				finish();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}