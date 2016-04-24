package il.co.runnerdevice.Controllers;

import il.co.runnerdevice.R;

import il.co.runnerdevice.Api.ServiceGenerator;
import il.co.runnerdevice.Api.ShipApi;
import il.co.runnerdevice.Authentication.AccountGeneral;
import il.co.runnerdevice.Dblocal.DatabaseHelper;
import il.co.runnerdevice.Dblocal.ShippingContentProvider;
import il.co.runnerdevice.Dblocal.ShippingContract;
import il.co.runnerdevice.Pojo.AccessToken;
import il.co.runnerdevice.Pojo.ItemSyncGeneric;
import il.co.runnerdevice.Pojo.ResponseItem;
import il.co.runnerdevice.Pojo.WhoAmI;

import il.co.runnerdevice.Services.SessionService;
import il.co.runnerdevice.Services.UserService;
import il.co.runnerdevice.Utils.CommonUtilities;
import il.co.runnerdevice.Utils.ObjectTableCode;
import il.co.runnerdevice.Utils.SyncStateRecord;
import il.co.runnerdevice.Utils.SyncStatus;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SyncStatusObserver;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.android.gcm.GCMRegistrar;
//https://github.com/Udinic/SyncAdapter/blob/master/SyncAdapterExample/src/com/udinic/sync_adapter_example_app/Main.java

public class MainAsync extends FragmentActivity {
	String url = CommonUtilities.URL;
	TextView txt_pressure;
	TextView txt_time;
	EditText txt_firstName;
	EditText txt_lastName;
	// Session Manager Class
	SessionService session;
	AccountManager mAccountManager;
	UserService mUserService;
	WhoAmI m_Who = null;
	Object handleSyncObserver;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_async);
		mAccountManager = AccountManager.get(this);
		mUserService = new UserService(this);

		
		Account[] accounts = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
		if (accounts.length == 0) {
			if (accounts.length == 0) {
				// Toast.makeText(getApplicationContext(),
				// "no has account");
				Toast.makeText(getApplicationContext(), "no has account", 3);
				Log.d("runnerdevice", "no has account ");
				addNewAccount(AccountGeneral.ACCOUNT_TYPE,AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
			} 
			
			
		} else {
			initButtonsAfterConnect();
			session = new SessionService(getApplicationContext());
			txt_pressure = (TextView) findViewById(R.id.txt_descSync);

			txt_time = (TextView) findViewById(R.id.txt_timeSync);
			txt_firstName = (EditText) findViewById(R.id.editFirstNameSync);
			txt_lastName = (EditText) findViewById(R.id.editLastNameSync);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			txt_time.setText("begin time  : " + cal.getTime());
		
			//Account account = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
		
			
			
		}
		
        findViewById(R.id.btnSync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Account account = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
                if (account == null) {
                	txt_pressure.setText("Please connect first");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
                ContentResolver.requestSync(account, ShippingContract.AUTHORITY, bundle);
            }
        });
        
        findViewById(R.id.btnsendsyncwhoamiSync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Account account = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
                if (account == null) {
                	txt_pressure.setText("Please connect first");
                    return;
                }
                String userName = account.name;
				String userId = mAccountManager.getUserData(account,
						AccountGeneral.PARAM_USER_ID);

				String firstName = txt_firstName.getText().toString();
				String lastName = txt_lastName.getText().toString();

				WhoAmI who = mUserService.UpdateUser(userId, firstName,
						lastName, userName);
				if (who == null)
					txt_pressure.setText("no has sql  data  :"
							+ userName + "(" + userId + ")");

				else {
					txt_firstName.setText(who.getFirstName());
					txt_lastName.setText(who.getLastName());
					String fullname = who.getFirstName() + " "
							+ who.getLastName();

					txt_pressure.setText("sql  update data  :  "
							+ fullname);
					boolean syncToNetwork=true;
					String url = "content://" + ShippingContract.AUTHORITY
							+ "/" + ShippingContentProvider.PATH_USER + "/"
							+ userId;
					Uri currentUser = Uri.parse(url);
					//ContentResolver.notifyChange
					getContentResolver().notifyChange(currentUser, null, syncToNetwork);
				}
				
             //   Bundle bundle = new Bundle();
               // bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
                //bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
                //ContentResolver.requestSync(account, ShippingContract.AUTHORITY, bundle);
            }
        });
        
		findViewById(R.id.btnGetDataSync).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Account account = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
						String userName = account.name;
						String userId = mAccountManager.getUserData(account,
								AccountGeneral.PARAM_USER_ID);

						String firstName = txt_firstName.getText().toString();
						String lastName = txt_lastName.getText().toString();

						WhoAmI who = mUserService.IfGetMeIsEmpty(userId,firstName, lastName, userName);
						txt_firstName.setText(who.getFirstName());
						txt_lastName.setText(who.getLastName());
						String fullname = who.getFirstName() + " "+ who.getLastName();
						txt_pressure.setText("sql  data  :  " + fullname);
					}
				});
		
		
	}
	SyncStatusObserver syncObserver = new SyncStatusObserver() {
        @Override
        public void onStatusChanged(final int which) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refreshSyncStatus();
                }
            });
        }
    };
    
    @Override
    protected void onResume() {
        super.onResume();
        handleSyncObserver = ContentResolver.addStatusChangeListener(ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE |
                ContentResolver.SYNC_OBSERVER_TYPE_PENDING, syncObserver);
    }
    @Override
    protected void onPause() {
        if (handleSyncObserver != null)
            ContentResolver.removeStatusChangeListener(handleSyncObserver);
        super.onStop();
    }
    
    private void refreshSyncStatus() {
        String status;
        Account account = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
        if (ContentResolver.isSyncActive(account, ShippingContract.AUTHORITY))
            status = "Status: Syncing..";
        else if (ContentResolver.isSyncPending(account, ShippingContract.AUTHORITY))
            status = "Status: Pending..";
        else
            status = "Status: Idle";

        ((TextView) findViewById(R.id.status)).setText(status);
        Log.d("udinic", "refreshSyncStatus> " + status);
    }
    private void initButtonsAfterConnect() {
        String authority = ShippingContract.AUTHORITY;
        Account account = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
        // Get the syncadapter settings and init the checkboxes accordingly
        int isSyncable = ContentResolver.getIsSyncable(account, authority);
        boolean autSync = ContentResolver.getSyncAutomatically(account, authority);

        ((CheckBox)findViewById(R.id.cbIsSyncable)).setChecked(isSyncable > 0);
        ((CheckBox)findViewById(R.id.cbAutoSync)).setChecked(autSync);

        findViewById(R.id.cbIsSyncable).setEnabled(true);
        findViewById(R.id.cbAutoSync).setEnabled(true);
        findViewById(R.id.status).setEnabled(true);
        findViewById(R.id.btnSync).setEnabled(true);

        refreshSyncStatus();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private void addNewAccount(String accountType, String authTokenType) {
		final AccountManagerFuture<Bundle> future = mAccountManager.addAccount(
				accountType, authTokenType, null, null, this,
				new AccountManagerCallback<Bundle>() {
					@Override
					public void run(AccountManagerFuture<Bundle> future) {
						try 
						{
							Bundle bnd = future.getResult();
							// showMessage("Account was created");
							Log.d("runnerdevice", "AddNewAccount Bundle is "
									+ bnd);
							Account account = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
						
						} 
						catch (Exception e) {
							e.printStackTrace();
							// showMessage(e.getMessage());
						}
					}
				}, null);
	}
}
