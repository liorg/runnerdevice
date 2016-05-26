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
import il.co.runnerdevice.Utils.UtilConvertor;

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
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

//http://www.androidwarriors.com/2015/12/retrofit-20-android-example-web.html
public class MainActivity extends FragmentActivity {
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

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_whoami);
		mAccountManager = AccountManager.get(this);
		mUserService = new UserService(this);

		Account[] accounts = mAccountManager
				.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
		if (accounts.length == 0) {
			Toast.makeText(getApplicationContext(), "no has account", 3);
			addNewAccount(AccountGeneral.ACCOUNT_TYPE,
					AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
		} else {
			session = new SessionService(getApplicationContext());

			txt_pressure = (TextView) findViewById(R.id.txt_press);

			txt_time = (TextView) findViewById(R.id.txt_time);
			txt_firstName = (EditText) findViewById(R.id.editFirstName);
			txt_lastName = (EditText) findViewById(R.id.editLastName);

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			txt_time.setText("begin time  : " + cal.getTime());
			// getReport();
			Account account = mAccountManager
					.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
			getWhoAmi2(account);
		}

		findViewById(R.id.btnsqllite).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Account account = mAccountManager
								.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
						String userName = account.name;
						String userId = mAccountManager.getUserData(account,
								AccountGeneral.PARAM_USER_ID);

						String firstName = txt_firstName.getText().toString();
						String lastName = txt_lastName.getText().toString();

						WhoAmI who = mUserService.IfGetMeIsEmpty(userId,
								firstName, lastName, userName);
						txt_firstName.setText(who.getFirstName());
						txt_lastName.setText(who.getLastName());
						String fullname = who.getFirstName() + " "
								+ who.getLastName();
						txt_pressure.setText("sql  data  :  " + fullname);
					}
				});

		findViewById(R.id.btnupdatesqllite).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Account account = mAccountManager
								.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
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
						}
					}
				});

		findViewById(R.id.btnwhoami).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						txt_pressure.setText("user  : ... ");
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy/MM/dd HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						txt_time.setText("time  : " + cal.getTime());
						Account[] accounts = mAccountManager
								.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
						if (accounts == null) {
							Log.d("runnerdevice",
									"no has any accounts account ");
						}
						if (accounts.length == 0) {
							// Toast.makeText(getApplicationContext(),
							// "no has account");
							Log.d("runnerdevice", "no has account ");
							addNewAccount(AccountGeneral.ACCOUNT_TYPE,
									AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
						} else {
							Log.d("runnerdevice", "account getWhoAmi2 ");

							getWhoAmi2(accounts[0]);
						}
					}
				});

		findViewById(R.id.btnsendasyncwhoami).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						txt_pressure.setText("user  : ... ");
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy/MM/dd HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						txt_time.setText("time  : " + cal.getTime());
						Account[] accounts = mAccountManager
								.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
						if (accounts == null) {
							Log.d("runnerdevice",
									"no has any accounts account ");
						}
						if (accounts.length == 0) {
							// Toast.makeText(getApplicationContext(),
							// "no has account");
							Log.d("runnerdevice", "no has account ");
							addNewAccount(AccountGeneral.ACCOUNT_TYPE,
									AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
						} else {
							Log.d("runnerdevice", "account getWhoAmi2 ");

							saveAync(accounts[0]);
						}
					}
				});

		findViewById(R.id.btnsendsyncwhoami).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						txt_pressure.setText("user  : ... ");
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy/MM/dd HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						txt_time.setText("time  : " + cal.getTime());
						Account[] accounts = mAccountManager
								.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
						if (accounts == null) {
							Log.d("runnerdevice",
									"no has any accounts account ");
						}
						if (accounts.length == 0) {
							// Toast.makeText(getApplicationContext(),
							// "no has account");
							Log.d("runnerdevice", "no has account ");
							addNewAccount(AccountGeneral.ACCOUNT_TYPE,
									AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
						} else {
							Log.d("runnerdevice", "account getWhoAmi2 ");

							saveSync(accounts[0]);
						}
					}
				});

		findViewById(R.id.btnUpdateContentProvider).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						txt_pressure.setText("get content provider  : ... ");
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy/MM/dd HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						txt_time.setText("time  : " + cal.getTime());
						Account account = mAccountManager
								.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
						String userName = account.name;
						String userId = mAccountManager.getUserData(account,
								AccountGeneral.PARAM_USER_ID);

						String firstName = txt_firstName.getText().toString();
						String lastName = txt_lastName.getText().toString();

						// Add a new student record
						ContentValues values = new ContentValues();

						values.put(DatabaseHelper.KEY_FIRSTNAME, firstName);
						values.put(DatabaseHelper.KEY_LASTNAME, lastName);
						String url = "content://" + ShippingContract.AUTHORITY
								+ "/" + ShippingContentProvider.PATH_USER + "/"
								+ userId;
						Uri currentUser = Uri.parse(url);

						getContentResolver().update(currentUser, values, null,null);
						
						txt_firstName.setText(firstName);
						txt_lastName.setText(lastName);
						String fullname = firstName + " " + lastName;
						txt_pressure
								.setText(" content provider XX update XX  :  "
										+ fullname);

					}
				});

		findViewById(R.id.btnContentProvider).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						txt_pressure.setText("update content provider  : ... ");
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy/MM/dd HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						txt_time.setText("time  : " + cal.getTime());
						Account[] accounts = mAccountManager
								.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
						if (accounts == null) {
							Log.d("runnerdevice",
									"no has any accounts account ");
						}
						Account account = mAccountManager
								.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
						String userName = account.name;
						String userId = mAccountManager.getUserData(account,
								AccountGeneral.PARAM_USER_ID);

						String url = "content://" + ShippingContract.AUTHORITY
								+ "/" + ShippingContentProvider.PATH_USER + "/"
								+ userId;

						Uri currentUser = Uri.parse(url);
						Cursor cursor = managedQuery(currentUser, null, null,
								null, "name");

						if (cursor != null && cursor.getCount() > 0) {

							cursor.moveToFirst();
							String firstName = cursor.getString(cursor
									.getColumnIndex(DatabaseHelper.KEY_FIRSTNAME));
							String lastName = cursor.getString(cursor
									.getColumnIndex(DatabaseHelper.KEY_LASTNAME));

							txt_firstName.setText(firstName);
							txt_lastName.setText(lastName);
							String fullname = firstName + " __ " + lastName;
							txt_pressure.setText(" content provider data  :  "
									+ fullname);
						}

					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	void saveAync(Account account) {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
				.addConverterFactory(GsonConverterFactory.create()).build();

		WhoAmI who = new WhoAmI();
		who.setUserId(mAccountManager.getUserData(account,
				AccountGeneral.PARAM_USER_ID));
		who.setUserName(account.name);
		who.setFirstName(txt_firstName.getText().toString());
		who.setLastName(txt_lastName.getText().toString());
		who.setFullName("");

		ItemSyncGeneric<WhoAmI> itemSync = new ItemSyncGeneric<WhoAmI>();
		itemSync.setClientId(AccountGeneral.CLIENT_ID);
		itemSync.setDeviceId("");
		String dateToStr=UtilConvertor.ConvertDateToISO(new java.util.Date(2011, 4, 1, 16, 02));
		
		itemSync.setLastUpdateRecord(dateToStr);
		itemSync.setSyncStateRecord(ObjectTableCode.USER);
		itemSync.setSyncStatus(SyncStatus.SyncFromClient);
		itemSync.setSyncStateRecord(SyncStateRecord.Change);
		itemSync.setSyncObject(who);

		ShipApi loginService = ServiceGenerator.createService(ShipApi.class,
				mAccountManager, account);

		Call<ResponseItem<ItemSyncGeneric<WhoAmI>>> call = loginService
				.UpdateWhoAmISync(itemSync);

		call.enqueue(new Callback<ResponseItem<ItemSyncGeneric<WhoAmI>>>() {
			@Override
			public void onFailure(
					Call<ResponseItem<ItemSyncGeneric<WhoAmI>>> arg0,
					Throwable arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onResponse(
					Call<ResponseItem<ItemSyncGeneric<WhoAmI>>> arg0,
					Response<ResponseItem<ItemSyncGeneric<WhoAmI>>> arg1) {
				// TODO Auto-generated method stub
				try {
					if (!arg1.body().isIsAuthenticated()) {
						txt_pressure.setText("user  : NO Authenticated ");
						addNewAccount(AccountGeneral.ACCOUNT_TYPE,
								AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
						// _session.RedirctToLogin();
					} else {
						String pressure = arg1.body().getModel()
								.getSyncObject().getFullName()
								+ "("
								+ arg1.body().getModel().getSyncObject()
										.getFirstName()
								+ " "
								+ arg1.body().getModel().getSyncObject()
										.getLastName()
								+ ") sync update:"
								+ arg1.body().getModel().getLastUpdateRecord();
						String first = arg1.body().getModel().getSyncObject()
								.getFirstName();
						String last = arg1.body().getModel().getSyncObject()
								.getLastName();

						txt_pressure.setText("user  :  " + pressure);
						txt_firstName.setText(first);
						txt_lastName.setText(last);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	void saveSync(Account account) {

		Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
				.addConverterFactory(GsonConverterFactory.create()).build();
		WhoAmI who = new WhoAmI();
		who.setUserId(mAccountManager.getUserData(account,
				AccountGeneral.PARAM_USER_ID));
		who.setUserName(account.name);
		who.setFirstName(txt_firstName.getText().toString());
		who.setLastName(txt_lastName.getText().toString());
		who.setFullName("");

		ShipApi loginService = ServiceGenerator.createService(ShipApi.class,
				mAccountManager, account);

		Call<ResponseItem<WhoAmI>> call = loginService.UpdateWhoAmI(who);

		call.enqueue(new Callback<ResponseItem<WhoAmI>>() {
			@Override
			public void onFailure(Call<ResponseItem<WhoAmI>> arg0,
					Throwable arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onResponse(Call<ResponseItem<WhoAmI>> arg0,
					Response<ResponseItem<WhoAmI>> arg1) {
				// TODO Auto-generated method stub
				try {
					if (!arg1.body().isIsAuthenticated()) {
						txt_pressure.setText("user  : NO Authenticated ");
						addNewAccount(AccountGeneral.ACCOUNT_TYPE,
								AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
						// _session.RedirctToLogin();
					} else {
						String pressure = arg1.body().getModel().getFullName()
								+ "(" + arg1.body().getModel().getFirstName()
								+ " " + arg1.body().getModel().getLastName()
								+ ")";
						String first = arg1.body().getModel().getFirstName();
						String last = arg1.body().getModel().getLastName();
						m_Who = arg1.body().getModel();

						txt_pressure.setText("user  :  " + pressure);
						txt_firstName.setText(first);
						txt_lastName.setText(last);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	void getWhoAmi2(Account account) {

		Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
				.addConverterFactory(GsonConverterFactory.create()).build();

		ShipApi loginService = ServiceGenerator.createService(ShipApi.class,
				mAccountManager, account);

		Call<ResponseItem<WhoAmI>> call = loginService.WhoAmi();

		call.enqueue(new Callback<ResponseItem<WhoAmI>>() {
			@Override
			public void onFailure(Call<ResponseItem<WhoAmI>> arg0,
					Throwable arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onResponse(Call<ResponseItem<WhoAmI>> arg0,
					Response<ResponseItem<WhoAmI>> arg1) {
				// TODO Auto-generated method stub
				try {
					if (!arg1.body().isIsAuthenticated()) {
						txt_pressure.setText("user  : NO Authenticated ");
						addNewAccount(AccountGeneral.ACCOUNT_TYPE,
								AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
						// _session.RedirctToLogin();
					} else {
						String pressure = arg1.body().getModel().getFullName()
								+ "(" + arg1.body().getModel().getFirstName()
								+ ")";
						String first = arg1.body().getModel().getFirstName();
						String last = arg1.body().getModel().getLastName();

						txt_pressure.setText("user  :  " + pressure);
						txt_firstName.setText(first);
						txt_lastName.setText(last);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	private void addNewAccount(String accountType, String authTokenType) {
		final AccountManagerFuture<Bundle> future = mAccountManager.addAccount(
				accountType, authTokenType, null, null, this,
				new AccountManagerCallback<Bundle>() {
					@Override
					public void run(AccountManagerFuture<Bundle> future) {
						try {
							Bundle bnd = future.getResult();
							// showMessage("Account was created");
							Log.d("runnerdevice", "AddNewAccount Bundle is "
									+ bnd);
							Account account = mAccountManager
									.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
							getWhoAmi2(account);

						} catch (Exception e) {
							e.printStackTrace();
							// showMessage(e.getMessage());
						}
					}
				}, null);
	}

}
