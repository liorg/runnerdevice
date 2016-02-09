package il.co.runnerdevice;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import android.app.Activity;

import android.text.Html;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Token_Fragment extends Fragment {
	// Session Manager Class
	SessionManager session;

	// Button Logout
	Button btnLogout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.token_fragment, container, false);
	
		session = new SessionManager(getActivity().getApplicationContext());
		session.getUserDetails();
		
		
		  TextView lblName = (TextView) rootView.findViewById(R.id.lblName);
		  //TextView lblRefresh = (TextView) rootView.findViewById(R.id.lblRefresh);
		 // TextView lblCdate = (TextView) rootView.findViewById(R.id.lblCDate);
		 // TextView lblEdate = (TextView) rootView.findViewById(R.id.lblEDate);
		 // TextView lblRoles = (TextView) rootView.findViewById(R.id.lblRoles);
	      //  TextView lblEmail = (TextView) rootView.findViewById(R.id.lblEmail);
	        
	        // Button logout
	        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
	        
	        Toast.makeText(getActivity().getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
	        
	        /**
	         * Call this function whenever you want to check user login
	         * This will redirect user to LoginActivity is he is not
	         * logged in
	         * */
	        session.checkLogin();
	        
	        // get user data from session
	        HashMap<String, String> user = session.getUserDetails();
	        
	        // name
	        String name = user.get(SessionManager.KEY_NAME);
	        String cdate = user.get(SessionManager.KEY_CurrentDate);
	        String edate = user.get(SessionManager.KEY_ExpiredDate);
	        String roles = user.get(SessionManager.KEY_Roles);
	        String refresh = user.get(SessionManager.KEY_Refresh);
	        // displaying user data
	        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
	       // lblRefresh.setText(Html.fromHtml("Email: <b>" + refresh + "</b>"));
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
					session.logoutUser();
				}
			});
		return rootView;
	}
}