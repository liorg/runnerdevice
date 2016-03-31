package il.co.runnerdevice.Authentication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import il.co.runnerdevice.Tutorial.CustomHttpClient;
import il.co.runnerdevice.Api.ShipApi;
import il.co.runnerdevice.Controllers.AuthenticatorActivity;
import il.co.runnerdevice.Pojo.AccessToken;
import il.co.runnerdevice.Utils.CommonUtilities;
import android.accounts.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;


import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;

/**
https://github.com/Udinic/AccountAuthenticator/blob/master/src/com/udinic/accounts_authenticator_example/authentication/UdinicAuthenticator.java
 */
public class RunnerAuthenticatorAdaptor extends AbstractAccountAuthenticator {
	
    private String TAG_CLASS = "RunnerAuthenticatorAdaptor";
    private final Context mContext;

    public RunnerAuthenticatorAdaptor(Context context) {
        super(context);

        // I hate you! Google - set mContext as protected!
        this.mContext = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> addAccount");

        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> getAuthToken");
        HttpResponse response2=null;
        // If the caller requested an authToken type we don't support, then
        // return an error
        if (!authTokenType.equals(AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY) && !authTokenType.equals(AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(mContext);
        String refresh_token="";
        String authToken = am.peekAuthToken(account, authTokenType);
        Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> peekAuthToken returned - " + authToken);
        String expired = am.getUserData(account, AccountGeneral.PARAM_EXPIRED);
        Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> expired returned - " + expired);
        

        final String password = am.getPassword(account);
        Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> getAuthToken password - " + password);
        boolean isvalidToken=CommonUtilities.IsValidToken(expired);
        //https://gist.github.com/burgalon/dd289d54098068701aee
       // if(!isvalidToken || TextUtils.isEmpty(authToken))
        {  
        	Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> IsValidToken(expired)  return " + isvalidToken);
        	
        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
     	    nameValuePairs.add(new BasicNameValuePair("grant_type", AccountGeneral.GRANT_TYPE_REFRESH_TOKEN));
     	    nameValuePairs.add(new BasicNameValuePair("refresh_token", password));
     	    nameValuePairs.add(new BasicNameValuePair("client_id", AccountGeneral.CLIENT_ID));
     	    
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			
	        HttpPost httpRefrshToken=new HttpPost(CommonUtilities.URL+"/token");
	        httpRefrshToken.setHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=UTF-8");
			 try 
			 {
				 httpRefrshToken.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			 } 
			 catch (UnsupportedEncodingException e) 
			 {
			        Log.e(TAG_CLASS,e.getMessage());
			 }
			// Execute HTTP Post Request
			 HttpClient httpClient2 = new DefaultHttpClient();
			
			    try 
			    {
			    	response2 = httpClient2.execute(httpRefrshToken);
			    	 Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> getAuthToken RefreshToken  - " + response2.toString());
			        String body = CustomHttpClient.GetASCIIContentFromEntity(response2.getEntity());
			        JSONObject access_token = new JSONObject(body);
			        
			        if(access_token.has("error")){
			        	 Log.e(CommonUtilities.APP_NAME, TAG_CLASS + "> getAuthToken access_token error ");
			        	//throw new IllegalArgumentException("token error");
			        	return  IfFailedReplaceCurrentUser(response,account,authTokenType);
			        }
			        authToken=access_token.getString("access_token");
			        
			      //  am.blockingGetAuthToken(account, authTokenType, notifyAuthFailure)
			        refresh_token=access_token.getString("refresh_token");
			        String currentDate=access_token.getString("m:currentTime");
			        String expiredDate=access_token.getString("m:expiredOn");
			        
			      am.setPassword(account, refresh_token);
			      Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> getAuthToken am.setPassword  - " + refresh_token);
			     am.setUserData(account, AccountGeneral.PARAM_EXPIRED, expiredDate);
			      Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> getAuthToken am.setUserData PARAM_EXPIRED, - " + expiredDate);
			      // am.setAuthToken(account, authTokenType, authToken);
			  
			    } 
			    catch (JSONException eej) {
			    	   Log.e(TAG_CLASS,eej.getMessage());
			      //  eej.printStackTrace();
			    }
			    catch (IOException e) {
			    	   Log.e(TAG_CLASS,e.getMessage());
			        e.printStackTrace();
			    }
        }
        // for refrsh token
        //http://stackoverflow.com/questions/20642153/android-oauth2-bearer-token-best-practices
        //https://gist.github.com/burgalon/dd289d54098068701aee
        
        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
          
            result.putString(AccountManager.KEY_PASSWORD, refresh_token);
            Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> getAuthToken "+refresh_token );
            Log.d(CommonUtilities.APP_NAME, TAG_CLASS + "> getAuthToken back" );
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_NAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }
    
    public Bundle IfFailedReplaceCurrentUser(AccountAuthenticatorResponse response,Account account, String authTokenType){
    	 // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_NAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        if ( AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType))
            return AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
        else if (AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType))
            return AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY_LABEL;
        else
            return authTokenType + " (Label)";
    }
      
    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }
}