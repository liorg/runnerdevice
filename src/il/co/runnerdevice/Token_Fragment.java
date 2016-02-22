package il.co.runnerdevice;
import static il.co.runnerdevice.CommonUtilities.SENDER_ID;
import android.annotation.SuppressLint;
import android.content.Context;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import com.google.android.gcm.GCMRegistrar;

import android.text.Html;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gcm.GCMRegistrar;

import static il.co.runnerdevice.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static il.co.runnerdevice.CommonUtilities.EXTRA_MESSAGE;
import static il.co.runnerdevice.CommonUtilities.SENDER_ID;
import static il.co.runnerdevice.CommonUtilities.SERVER_URL;

public class Token_Fragment extends Fragment {
	// Session Manager Class
	SessionManager m_session;
	TextView mDisplay;
	AsyncTask<Void, Void, Void> mRegisterTask;
	// Button Logout
	Button btnLogout;
	Button btnRegister;
	Button btnUnRegister;
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 rootView = inflater
				.inflate(R.layout.token_fragment, container, false);
	
		m_session = new SessionManager(getActivity().getApplicationContext());
		
		 Toast.makeText(getActivity().getApplicationContext(), "User Login Status: " + m_session.isLoggedIn(), Toast.LENGTH_LONG).show();
	    
		 m_session.checkLogin();
	   
		mDisplay = (TextView) rootView.findViewById(R.id.display);
		  TextView lblName = (TextView) rootView.findViewById(R.id.lblName);
		  TextView lblRefresh = (TextView) rootView.findViewById(R.id.lblRefreshToken);
		 // TextView lblCdate = (TextView) rootView.findViewById(R.id.lblCDate);
		 // TextView lblEdate = (TextView) rootView.findViewById(R.id.lblEDate);
		 // TextView lblRoles = (TextView) rootView.findViewById(R.id.lblRoles);
	      //  TextView lblEmail = (TextView) rootView.findViewById(R.id.lblEmail);
	        
	    
	        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
	        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
	        btnUnRegister = (Button) rootView.findViewById(R.id.btnUnRegister);
	        
	        
	        // get user data from session
	        HashMap<String, String> user = m_session.getUserDetails();
	        
	        // name
	        String name = user.get(SessionManager.KEY_NAME);
	        String cdate = user.get(SessionManager.KEY_CurrentDate);
	        String edate = user.get(SessionManager.KEY_ExpiredDate);
	        String roles = user.get(SessionManager.KEY_Roles);
	        String refresh = user.get(SessionManager.KEY_Refresh);
	        // displaying user data
	        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
	        lblRefresh.setText(Html.fromHtml("Refresh Token: <b>" + refresh + "</b>"));
	      //  lblCdate.setText(Html.fromHtml("Current Date: <b>" + cdate + "</b>"));
	       // lblCdate.setText(Html.fromHtml("Expired Date: <b>" + edate + "</b>"));
	      //  lblRoles.setText(Html.fromHtml("Roles: <b>" + roles + "</b>"));
	      //  lblEdate.setText(Html.fromHtml("Roles: <b>" + edate + "</b>"));
	        /**
	         * Logout button click event
	         * */
	        btnLogout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// Clear the session data
					// This will clear all session data and 
					// redirect user to LoginActivity
					m_session.logoutUser();
				}
			});
	        
	        btnUnRegister.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (GCMRegistrar.isRegisteredOnServer(rootView.getContext())) {
			                // Skips registration.
			              
			                // Try to register again, but not in the UI thread.
			                // It's also necessary to cancel the thread onDestroy(),
			                // hence the use of AsyncTask instead of a raw thread.
			                final Context context = rootView.getContext();
			                mRegisterTask = new AsyncTask<Void, Void, Void>() {

			                    @Override
			                    protected Void doInBackground(Void... params) {
			                     boolean unregistered =true;
			                      if (unregistered) {
			                            GCMRegistrar.unregister(context);
			                        }
			                        return null;
			                    }

			                    @Override
			                    protected void onPostExecute(Void result) {
			                        mRegisterTask = null;
			                    }

			                };
			                mRegisterTask.execute(null, null, null);
			            }
			        }
				
			});
	        
	       btnRegister.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					final String regId = GCMRegistrar.getRegistrationId(rootView.getContext());
			        if (regId.equals("")) {
			            // Automatically registers application on startup.
			            GCMRegistrar.register(rootView.getContext(), SENDER_ID);
			        } else {
			            // Device is already registered on GCM, check server.
			            if (GCMRegistrar.isRegisteredOnServer(rootView.getContext())) {
			                // Skips registration.
			                mDisplay.append(getString(R.string.already_registered) + "\n");
			            } else 
			            {
			                // Try to register again, but not in the UI thread.
			                // It's also necessary to cancel the thread onDestroy(),
			                // hence the use of AsyncTask instead of a raw thread.
			                final Context context = rootView.getContext();
			                mRegisterTask = new AsyncTask<Void, Void, Void>() {

			                    @Override
			                    protected Void doInBackground(Void... params) {
			                    	String userid=m_session.GetUserId();
			                        boolean registered =
			                                ServerUtilities.register(context, regId,userid);
			                        // At this point all attempts to register with the app
			                        // server failed, so we need to unregister the device
			                        // from GCM - the app will try to register again when
			                        // it is restarted. Note that GCM will send an
			                        // unregistered callback upon completion, but
			                        // GCMIntentService.onUnregistered() will ignore it.
			                        if (!registered) {
			                            GCMRegistrar.unregister(context);
			                        }
			                        return null;
			                    }

			                    @Override
			                    protected void onPostExecute(Void result) {
			                        mRegisterTask = null;
			                    }

			                };
			                mRegisterTask.execute(null, null, null);
			            }
			        }
				}
			});
		return rootView;
	}

    public void setDisplay(String newMessage){
    	 mDisplay.append(newMessage + "\n");
    }
}