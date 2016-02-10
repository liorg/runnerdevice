package il.co.runnerdevice;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

@SuppressLint("NewApi")
public class TB_Fragment extends Fragment {
	SessionManager session;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.tb_fragment, container, false);

		session = new SessionManager(getActivity().getApplicationContext());
		
		 Toast.makeText(getActivity().getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
	        
	        /**
	         * Call this function whenever you want to check user login
	         * This will redirect user to LoginActivity is he is not
	         * logged in
	         * */
	        session.checkLogin();
	        
		return rootView;
	}
}