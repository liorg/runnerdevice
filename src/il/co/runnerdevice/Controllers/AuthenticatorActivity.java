package il.co.runnerdevice.Controllers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import il.co.runnerdevice.*;
import il.co.runnerdevice.Api.ShipApi;
import il.co.runnerdevice.Authentication.AccountGeneral;
import il.co.runnerdevice.Pojo.AccessToken;
import il.co.runnerdevice.Utils.CommonUtilities;
import il.co.runnerdevice.Tutorial.AlertDialogManager;

/**
 * The Authenticator activity.
 * 
 * Called by the Authenticator and in charge of identifing the user.
 * 
 * It sends back to the Authenticator the result.
 * https://github.com/Udinic/AccountAuthenticator
 * /blob/master/src/com/udinic/accounts_authenticator_example
 * /authentication/AuthenticatorActivity.java
 * https://github.com/Udinic/AccountAuthenticator
 */
public class AuthenticatorActivity extends AccountAuthenticatorActivity {

	public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
	public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
	public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
	public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
	public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
	public final static String PARAM_USER_PASS = "USER_PASS";
	private final int REQ_SIGNUP = 1;
	private final String TAG = this.getClass().getSimpleName();

	private AccountManager mAccountManager;
	private String mAuthTokenType;

	AlertDialogManager alert = new AlertDialogManager();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		mAccountManager = AccountManager.get(getBaseContext());

		String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
		mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
		if (mAuthTokenType == null)
			mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;

		if (accountName != null) {
			((TextView) findViewById(R.id.accountName)).setText(accountName);
		}

		findViewById(R.id.submit).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						submit();
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// The sign up activity returned that the user has successfully created
		// an account
		if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
			finishLogin(data);
		} else
			super.onActivityResult(requestCode, resultCode, data);
	}

	public void submit() {
		final String userName = ((TextView) findViewById(R.id.accountName))
				.getText().toString();
		final String userPass = ((TextView) findViewById(R.id.accountPassword))
				.getText().toString();

		final ProgressDialog _dialog;
		Retrofit retrofit = new Retrofit.Builder().baseUrl(CommonUtilities.URL)
				.addConverterFactory(GsonConverterFactory.create()).build();
		_dialog = ProgressDialog.show(AuthenticatorActivity.this, "נא המתן",
				"נא המתן שיתבצע התהליך", true, false);

		final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);

		ShipApi service = retrofit.create(ShipApi.class);
		Call<AccessToken> call = service.Login(userName, userPass,
				AccountGeneral.CLIENT_ID, AccountGeneral.GRANT_TYPE_PWS);

		call.enqueue(new Callback<AccessToken>() {
			final Intent res = new Intent();

			@Override
			public void onFailure(Call<AccessToken> arg0, Throwable arg1) {
				Bundle data = new Bundle();
				data.putString(KEY_ERROR_MESSAGE, "ERROR");
				res.putExtras(data);
				_dialog.dismiss();
				alert.showAlertDialog(AuthenticatorActivity.this,
						"Login failed..", "Error Ocuur", false);
				finishLogin(res);
			}

			@Override
			public void onResponse(Call<AccessToken> arg0,
					Response<AccessToken> arg1) {
				_dialog.dismiss();
				// TODO Auto-generated method stub
				try {
					Bundle data = new Bundle();
					// String pressure = arg1.body().getMUserId();
					String token = arg1.body().getAccessToken(); // _access_token.getString("access_token");
					String refresh_token = arg1.body().getRefreshToken();// _access_token.getString("refresh_token");
					String userName = arg1.body().getUserName();// _access_token.getString("userName");
					String currentTime = arg1.body().getMCurrentTime(); // _access_token.getString("m:currentTime");
					String userId = arg1.body().getMUserId(); // _access_token.getString("m:userId");
					String exTime = arg1.body().getMExpiredOn();// _access_token.getString("m:expiredOn");
					String roles = arg1.body().getRoles();// _access_token.getString("roles");

					data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
					data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
					data.putString(AccountManager.KEY_AUTHTOKEN, token);
					data.putString(PARAM_USER_PASS, refresh_token);
					data.putString(AccountGeneral.PARAM_EXPIRED, exTime);
					data.putString(AccountGeneral.PARAM_USER_ID, userId);
					data.putString(AccountGeneral.PARAM_ROLES, roles);
					res.putExtras(data);

					Toast.makeText(getApplicationContext(), "המשתמש אומת",
							Toast.LENGTH_LONG).show();

					finishLogin(res);

				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
				}
			}
		});
	}

	public void submit2() {

		final String userName = ((TextView) findViewById(R.id.accountName))
				.getText().toString();
		final String userPass = ((TextView) findViewById(R.id.accountPassword))
				.getText().toString();

		final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);

		new AsyncTask<String, Void, Intent>() {

			@Override
			protected Intent doInBackground(String... params) {

				Log.d("udinic", TAG + "> Started authenticating");

				String authtoken = null;
				Bundle data = new Bundle();
				try {
					authtoken = "";// sServerAuthenticate.userSignIn(userName,
									// userPass, mAuthTokenType);

					data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
					data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
					data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
					data.putString(PARAM_USER_PASS, userPass);

				} catch (Exception e) {
					data.putString(KEY_ERROR_MESSAGE, e.getMessage());
				}

				final Intent res = new Intent();
				res.putExtras(data);
				return res;
			}

			@Override
			protected void onPostExecute(Intent intent) {
				if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
					Toast.makeText(getBaseContext(),
							intent.getStringExtra(KEY_ERROR_MESSAGE),
							Toast.LENGTH_SHORT).show();
				} else {
					finishLogin(intent);
				}
			}
		}.execute();
	}

	public void finishLogin(Intent intent) {
		Log.d("RunnerDevice", TAG + "> finishLogin");
		Account[] accounts = mAccountManager
				.getAccountsByType(AccountManager.KEY_ACCOUNT_TYPE);
		if (accounts.length > 0) {
			Toast.makeText(getBaseContext(), "הסרת לקוח/ות קיימ/ים",
					Toast.LENGTH_LONG).show();
			for (Account acc : accounts)
				mAccountManager.removeAccountExplicitly(acc);
		}
		String accountName = intent
				.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
		String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
		final Account account = new Account(accountName,
				intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

		if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
			Log.d("runnerdevice", TAG + "> finishLogin > addAccountExplicitly");
			String authtoken = intent
					.getStringExtra(AccountManager.KEY_AUTHTOKEN);
			String expiredDate = intent
					.getStringExtra(AccountGeneral.PARAM_EXPIRED);
			String userID = intent.getStringExtra(AccountGeneral.PARAM_USER_ID);
			String roleID = intent.getStringExtra(AccountGeneral.PARAM_ROLES);
			String authtokenType = mAuthTokenType;

			// Creating the account on the device and setting the auth token we
			// got
			// (Not setting the auth token will cause another call to the server
			// to authenticate the user)
			mAccountManager
					.addAccountExplicitly(account, accountPassword, null);
			mAccountManager.setAuthToken(account, authtokenType, authtoken);
			mAccountManager.setUserData(account, AccountGeneral.PARAM_EXPIRED,
					expiredDate);
			mAccountManager.setUserData(account, AccountGeneral.PARAM_USER_ID,
					userID);
			mAccountManager.setUserData(account, AccountGeneral.PARAM_ROLES,
					roleID);
			mAccountManager.setUserData(account, AccountGeneral.PARAM_PWS,
					accountPassword);
			// mAccountManager.setUserData(account,AccountGeneral.PARAM_USER_NAME,
			// accountName);
			// Log.d("runnerdevice", TAG + "> finishLogin > setPassword(1)");
			// mAccountManager.setPassword(account, accountPassword);

		} else {
			Log.d("runnerdevice", TAG + "> finishLogin > setPassword(2)");
			mAccountManager.setPassword(account, accountPassword);
		}

		setAccountAuthenticatorResult(intent.getExtras());
		setResult(RESULT_OK, intent);
		finish();
	}

}
