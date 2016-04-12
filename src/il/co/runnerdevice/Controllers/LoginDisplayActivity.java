package il.co.runnerdevice.Controllers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import il.co.runnerdevice.Tutorial.AlertDialogManager;

import il.co.runnerdevice.Controllers.*;

import il.co.runnerdevice.R;

import il.co.runnerdevice.Api.ShipApi;
import il.co.runnerdevice.Pojo.AccessToken;
import il.co.runnerdevice.Services.SessionService;
import il.co.runnerdevice.Utils.CommonUtilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//TODO: https://github.com/rdrobinson3/LoginAndSignupTutorial
public class LoginDisplayActivity extends Activity {
	// Email, password edittext
	EditText txtUsername, txtPassword;
	// login button
	Button btnLogin;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	// Session Manager Class
	SessionService session;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Session Manager
		session = new SessionService(getApplicationContext());
		if (session.isLoggedIn() && session.IsValidToken()) {
			Intent i = new Intent(this, MainActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// Staring Login Activity
			this.startActivity(i);
			return;
		}
		// Email, Password input text
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword);

		Toast.makeText(getApplicationContext(),
				"User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG)
				.show();

		// Login button
		btnLogin = (Button) findViewById(R.id.btnLogin);

		// Login button click event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String username = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();

				if (username.trim().length() > 0
						&& password.trim().length() > 0) {

					// new
					// LoginRunner(username.trim(),password.trim(),session).execute();
					setLogin(session, username.trim(), password.trim());
				} else {
					// user didn't entered username or password
					// Show alert asking him to enter the details
					alert.showAlertDialog(LoginDisplayActivity.this,
							"Login failed..",
							"Please enter username and password", false);
				}
			}
		});
	}

	void setLogin(SessionService session, String username, String password) {
		// String username="r";
		// String password="1";
		final ProgressDialog _dialog;
		final SessionService _session = session;

		String client_id = "ngAutoApp";
		String grant_type = "password";

		Retrofit retrofit = new Retrofit.Builder().baseUrl(CommonUtilities.URL)
				.addConverterFactory(GsonConverterFactory.create()).build();
		_dialog = ProgressDialog.show(LoginDisplayActivity.this, "נא המתן",
				"נא המתן שיתבצע התהליך", true, false);

		ShipApi service = retrofit.create(ShipApi.class);
		Call<AccessToken> call = service.Login(username, password, client_id,
				grant_type);

		call.enqueue(new Callback<AccessToken>() {
			@Override
			public void onFailure(Call<AccessToken> arg0, Throwable arg1) {
				// TODO Auto-generated method stub
				_dialog.dismiss();
				alert.showAlertDialog(LoginDisplayActivity.this,
						"Login failed..", "Error Ocuur", false);

			}

			@Override
			public void onResponse(Call<AccessToken> arg0,
					Response<AccessToken> arg1) {
				_dialog.dismiss();
				// TODO Auto-generated method stub
				try {
					// String pressure = arg1.body().getMUserId();
					String token = arg1.body().getAccessToken(); // _access_token.getString("access_token");
					String refresh_token = arg1.body().getRefreshToken();// _access_token.getString("refresh_token");
					String userName = arg1.body().getUserName();// _access_token.getString("userName");
					String currentTime = arg1.body().getMCurrentTime(); // _access_token.getString("m:currentTime");
					String userId = arg1.body().getMUserId(); // _access_token.getString("m:userId");
					String exTime = arg1.body().getMExpiredOn();// _access_token.getString("m:expiredOn");
					String roles = arg1.body().getRoles();// _access_token.getString("roles");

					_session.createLoginSession(userName, refresh_token,
							currentTime, exTime, token, roles, userId);
					Toast.makeText(getApplicationContext(), "המשתמש אומת",
							Toast.LENGTH_LONG).show();

					// alert.showAlertDialog(LoginActivity.this, "Ok", "Ok",
					// false);
					// Staring MainActivity
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(i);
					finish();

					// txt_pressure.setText("pressure  :  " + pressure);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}