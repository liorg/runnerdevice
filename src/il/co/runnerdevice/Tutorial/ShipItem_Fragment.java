package il.co.runnerdevice.Tutorial;

//import android.app.Fragment;
//import android.app.FragmentManager;
import il.co.runnerdevice.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ShipItem_Fragment  extends Fragment {
	ShipItemView shipItem;
	SessionManager session;
	   View rootView;
	   private FragmentTabHost mTabHost;

	public ShipItem_Fragment(ShipItemView shipItemView){
		shipItem=shipItemView;
	
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		 rootView = inflater.inflate(R.layout.shipitem_fragment, container, false);

		session = new SessionManager(getActivity().getApplicationContext());
		//Toast.makeText(getActivity().getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
		//TextView txtPassword = (TextView) rootView.findViewById(R.id.statusShip); 
	
	    session.checkLogin();
	    
	    String status= shipItem.getStatus();
		//txtPassword.setText("<b>"+status+"</b>")
	    mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
	    mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        
	    Bundle arguments = new Bundle();
        arguments.putString("status", status);
        
        mTabHost.addTab(mTabHost.newTabSpec("detail").setIndicator("פרטים"),
                DetailFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("status").setIndicator("עדכון סטאטוס"),
        		StatusFragment.class,arguments);
        
        mTabHost.addTab(mTabHost.newTabSpec("chat").setIndicator("הערות"),
        		ChatFragment.class,arguments);
	    mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

	        @Override
	        public void onTabChanged(String tabId) {
	            // TODO your actions go here
	        	//Tab1Fragment fragment2 = new Tab1Fragment();
	    	    //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
	    	    //FragmentTransaction fragmentTransaction =        fragmentManager.beginTransaction();
	    	    //fragmentTransaction.replace(R.id.realtabcontent, fragment2);
	    	    //fragmentTransaction.addToBackStack(null);
	    	    //fragmentTransaction.commit();		
	        }
	    });   	 
      //  Tab2Fragment tab2= new Tab2Fragment(shipItem,session);
        
	    

	    
	    return rootView;
	}
	 
	
}
