package il.co.runnerdevice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Delivery_Fragment  extends Fragment {
	ShipItemView shipItem;
	SessionManager session;
	   View rootView;
	   
	public Delivery_Fragment(ShipItemView shipItemView){
		shipItem=shipItemView;
	
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		 rootView = inflater.inflate(R.layout.delivery_fragment, container, false);

		session = new SessionManager(getActivity().getApplicationContext());
		//Toast.makeText(getActivity().getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
		TextView txtPassword = (TextView) rootView.findViewById(R.id.statusShip); 
	
	    session.checkLogin();
	    
	    String status= shipItem.getStatus();
		txtPassword.setText("<b>"+status+"</b>");
	    
	    return rootView;
	}
}
