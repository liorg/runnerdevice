package il.co.runnerdevice.Controllers;

import il.co.runnerdevice.R;

import il.co.runnerdevice.Api.ServiceGenerator;
import il.co.runnerdevice.Api.ShipApi;
import il.co.runnerdevice.Authentication.AccountGeneral;
import il.co.runnerdevice.Pojo.AccessToken;
import il.co.runnerdevice.Pojo.WhoAmI;
import il.co.runnerdevice.Pojo.WhoAmIResponse;
import il.co.runnerdevice.Services.SessionService;
import il.co.runnerdevice.Utils.CommonUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



//import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.android.gcm.GCMRegistrar;


//http://www.androidwarriors.com/2015/12/retrofit-20-android-example-web.html
public class WhoAmIActivity  extends FragmentActivity {
	  String url = CommonUtilities.URL;
	    TextView  txt_pressure;
		// Session Manager Class
		SessionService session;
		  private AccountManager mAccountManager;
		
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_retrofit);
		 mAccountManager =  AccountManager.get(this);
		 
		 
		Account[] accounts = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
		if(accounts.length==0)
		{
		 Toast.makeText(getApplicationContext(), "no has account", 3);
		    addNewAccount(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
		}
		else{
		session = new SessionService(getApplicationContext()); 
		
		txt_pressure = (TextView) findViewById(R.id.txt_press);
		  //getReport();
		Account account = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
		getWhoAmi2(account);
		}
		
		 findViewById(R.id.btnwhoami).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	 txt_pressure.setText("user  : ... " );
		            	Account[] accounts = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
		        		if(accounts.length==0)
		        		{
		        		// Toast.makeText(getApplicationContext(), "no has account");
		        	          Log.d("runnerdevice", "no has account ");
		        		    addNewAccount(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
		        		}
		        		else{
		        			getWhoAmi2(accounts[0]);
		        		}
	            }
	        });
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	
	void getWhoAmi2(Account account) {
		
		 Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
		 
		 ShipApi loginService =  ServiceGenerator.createService(ShipApi.class, mAccountManager,account);
		 
	     Call<WhoAmIResponse> call = loginService.WhoAmi();

	     call.enqueue(new Callback<WhoAmIResponse>() {	 
				@Override
				public void onFailure(Call<WhoAmIResponse> arg0, Throwable arg1) {
					// TODO Auto-generated method stub
				}
				@Override
				public void onResponse(Call<WhoAmIResponse> arg0,
						Response<WhoAmIResponse> arg1) {
					// TODO Auto-generated method stub
					 try {
						 if(!arg1.body().isIsAuthenticated()){
							 txt_pressure.setText("user  : NO Authenticated " );
						 addNewAccount(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
							// _session.RedirctToLogin();
						 }
						 else{
		                    String pressure = arg1.body().getModel().getFullName();
		                    txt_pressure.setText("user  :  " + pressure);
						 }
						 } 
					 catch (Exception e) {
		                    e.printStackTrace();
		                }
					
				}
	        	 });
   }
	
	 /**
     * Add new account to the account manager
     * @param accountType
     * @param authTokenType
     */
    private void addNewAccount(String accountType, String authTokenType) {
        final AccountManagerFuture<Bundle> future = mAccountManager.addAccount(accountType, authTokenType, null, null, this, new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture<Bundle> future) {
                try {
                    Bundle bnd = future.getResult();
                  //  showMessage("Account was created");
                    Log.d("runnerdevice", "AddNewAccount Bundle is " + bnd);
                    Account account = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
            		getWhoAmi2(account);

                } catch (Exception e) {
                    e.printStackTrace();
                    //showMessage(e.getMessage());
                }
            }
        }, null);
    }
	
   
}
