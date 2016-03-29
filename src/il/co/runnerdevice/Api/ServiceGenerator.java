package il.co.runnerdevice.Api;

import il.co.runnerdevice.CustomHttpClient;
import il.co.runnerdevice.Authentication.AccountGeneral;
import il.co.runnerdevice.Pojo.AccessToken;
import il.co.runnerdevice.Services.SessionService;
import il.co.runnerdevice.Utils.CommonUtilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.util.Base64;
import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.*;

import retrofit2.converter.gson.GsonConverterFactory;
//https://futurestud.io/blog/android-basic-authentication-with-retrofit

public class ServiceGenerator {

    public static final  String API_BASE_URL = "http://testkipo.kipodeal.co.il";
    public static final  String  TAG_CLASS="ServiceGenerator";
	private static final String authToken = null;
	private static final String APP_NAME = "runner.co.il";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder  =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }


    public static <S> S createService(Class<S> serviceClass, final 	AccountManager am,final	Account account) {  
        
    	//final String authToken=session.GetToken();
    
    	//final String oldToken=am.peekAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);//(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, false);
    	//Log.d(APP_NAME, TAG + "> createService =>oldToken return  "+oldToken );
    	
    	//if (authToken != null)
    	{
        	httpClient.addInterceptor(new Interceptor() {

				@Override
				public Response intercept(Chain chain) throws IOException {
					// TODO Auto-generated method stub
					  Request original = chain.request();
					  	//http://stackoverflow.com/questions/22450036/refreshing-oauth-token-using-retrofit-without-modifying-all-calls
					  String authToken=null;
					try {
						String expiredDate= am.getUserData(account, AccountGeneral.PARAM_EXPIRED);
						 boolean isvalidToken=CommonUtilities.IsValidToken(expiredDate);
						Log.d(APP_NAME, TAG_CLASS + "> createService =>isValid return    "+isvalidToken );
						if(!isvalidToken)
						{
							String oldToken=am.peekAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);//(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, false);
						    Log.d(APP_NAME, TAG_CLASS + "> .createService =>invalidateAuthToken   "+oldToken );
						    //remove from the cache
							am.invalidateAuthToken(AccountGeneral.ACCOUNT_TYPE, oldToken);
						}
						//from the cache
						authToken = am.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
						Log.d(APP_NAME, TAG_CLASS + "> createService =>blockingGetAuthToken   "+authToken );
						am.setAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, authToken);
						
					} catch (OperationCanceledException e) {
					
						e.printStackTrace();
					} catch (AuthenticatorException e) {
					
						e.printStackTrace();
					}
				        final String authTokenHeader="Bearer "+ authToken;
	                    Request.Builder requestBuilder = original.newBuilder()
	                        .header("Authorization", authTokenHeader)
	                        .header("Accept", "application/json")
	                        .header("content-type", "application/json")
	                        .method(original.method(), original.body());

	                    Request request = requestBuilder.build();
	                    return chain.proceed(request);
				}
             });
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
  
    
    public static <S> S createService(Class<S> serviceClass, final SessionService session) {  
       
    	//final String authToken=session.GetToken();
    	final String refrshToken=session.GetRefreshKeyToken();
    	final boolean isValidToken=   session.IsValidToken();
    	
    	//if (authToken != null) {
        	httpClient.addInterceptor(new Interceptor() {

				@Override
				public Response intercept(Chain chain) throws IOException {
					// TODO Auto-generated method stub
					  Request original = chain.request();
					  	//http://stackoverflow.com/questions/22450036/refreshing-oauth-token-using-retrofit-without-modifying-all-calls

				        if (!isValidToken) 
				        {
				        	 List<NameValuePair> nameValuePairs=il.co.runnerdevice.CustomHttpClient.GetRefreshToken(session);
							 
							 HttpClient httpClient = new DefaultHttpClient();
							 HttpContext localContext = new BasicHttpContext();
					      
					        
					         HttpPost httpRefrshToken=new HttpPost("http://testkipo.kipodeal.co.il/token");
					         httpRefrshToken.setHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=UTF-8");
							 try 
							 {
								 httpRefrshToken.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
							 } 
							 catch (UnsupportedEncodingException e) 
							 {
							        e.printStackTrace();
							 }
							// Execute HTTP Post Request
							 HttpClient httpClient2 = new DefaultHttpClient();
							 HttpResponse response=null;
							 
							    try 
							    {
							        response = httpClient2.execute(httpRefrshToken);
							        Log.d("Response RefreshToken:" , response.toString());
							        String body = CustomHttpClient.GetASCIIContentFromEntity(response.getEntity());
							        JSONObject access_token = new JSONObject(body);
							        
							        if(access_token.has("error")){
							        	//isErr=true;
							        	throw new IllegalArgumentException("token error");
							        }
							        
							        String refresh_token=access_token.getString("refresh_token");
							        String token=access_token.getString("access_token");
							        String currentDate=access_token.getString("m:currentTime");
							        String expiredDate=access_token.getString("m:expiredOn");
							        session.setRefreshToken(refresh_token,currentDate ,expiredDate,token);
							    } 
							    catch (JSONException eej) {
							        eej.printStackTrace();
							    }
							    catch (IOException e) {
							        e.printStackTrace();
							      
							    }
					         
					        
				        	/*
				        	 Retrofit retrofit = new Retrofit.Builder()
				             .baseUrl(API_BASE_URL)
				             .addConverterFactory(GsonConverterFactory.create())
				             .build();
				        	ShipApi refeshTokenNow =retrofit.create(ShipApi.class);
				        	
				        	  String client_id="ngAutoApp";
				  		 	String grant_type="grant_type";
				        	 
				        	Call<AccessToken> newTokenService= refeshTokenNow.RefreshToken(session.GetRefreshKeyToken(),client_id,grant_type);
				        	retrofit2.Response<AccessToken> responseNewToken= newTokenService.execute();
				            // get a new token (I use a synchronous Retrofit call)
				        	AccessToken newToken=responseNewToken.body();
				        	if(newToken==null){
				        	session.RedirctToLogin();
				        	}
				        	*/
				        	
				            // create a new request and modify it accordingly using the new token
				          //  _refresh_token=access_token.getString("refresh_token");
						      //  _token=access_token.getString("access_token");
						       // _currentDate=access_token.getString("m:currentTime");
						       // _expiredDate=access_token.getString("m:expiredOn");
						     //  session.setRefreshToken(newToken.getRefreshToken(),newToken.getMCurrentTime() ,newToken.getMExpiredOn(),newToken.getAccessToken());
				        }
				        final String authToken="Bearer "+ session.GetToken();
				        
	                    Request.Builder requestBuilder = original.newBuilder()
	                        .header("Authorization", authToken)
	                        .header("Accept", "application/json")
	                        .header("content-type", "application/json")
	                        .method(original.method(), original.body());

	                    Request request = requestBuilder.build();
	                    return chain.proceed(request);
				}
                
             });
       // }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
   
    
}
