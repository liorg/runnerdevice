package il.co.runnerdevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.android.gcm.GCMRegistrar;

import static il.co.runnerdevice.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static il.co.runnerdevice.CommonUtilities.EXTRA_MESSAGE;
import static il.co.runnerdevice.CommonUtilities.SENDER_ID;
import static il.co.runnerdevice.CommonUtilities.SERVER_URL;

public class MainActivity extends FragmentActivity {

	String[] menutitles;
	TypedArray menuIcons;

	// nav drawer title
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private List<RowItem> rowItems;
	private CustomAdapter adapter;
	
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
			
   // Session Manager Class
	SessionManager session;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		checkNotNull(SERVER_URL, "SERVER_URL");
	    checkNotNull(SENDER_ID, "SENDER_ID");
	    // Make sure the device has the proper dependencies.
	    GCMRegistrar.checkDevice(this);
	    // Make sure the manifest was properly set - comment out this line
	    // while developing the app, then uncomment it when it's ready.
	    GCMRegistrar.checkManifest(this);
	    
	    registerReceiver(mHandleMessageReceiver,  new IntentFilter(DISPLAY_MESSAGE_ACTION));    
		  // Session class instance
        session = new SessionManager(getApplicationContext());
        
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        
        session.checkLogin();
        
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
		mTitle = mDrawerTitle = getTitle();

		menutitles = getResources().getStringArray(R.array.titles);
		menuIcons = getResources().obtainTypedArray(R.array.icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.slider_list);

		rowItems = new ArrayList<RowItem>();

		for (int i = 0; i < menutitles.length; i++) {
			RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(
					i, -1));
			rowItems.add(items);
		}

		menuIcons.recycle();

		adapter = new CustomAdapter(getApplicationContext(), rowItems);

		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new SlideitemListener());

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			updateDisplay(0);
		}
	}

	class SlideitemListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			updateDisplay(position);
		}

	}
	MyShip_Fragment fragmentShip=null;
	private void updateDisplay(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			//if (fragment ==null || fragment.getClass().isInstance(MyShip_Fragment.class) ) {
				//fragment = new MyShip_Fragment();
				//}
			if(fragmentShip==null){
				fragmentShip=new MyShip_Fragment();
			//	fragmentShip.RetryRefreshData(session);
			}
			fragment=(MyShip_Fragment)fragmentShip;
			
			break;
		case 1:
			fragment = new About_Fragment();
			break;
		case 2:
			fragment = new Token_Fragment();
			break;
		default:
			break;
		}

		if (fragment != null) {
			
			switchContent(fragment);
			//android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
			//fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			setTitle(menutitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	 private void checkNotNull(Object reference, String name) {
	        if (reference == null) {
	            throw new NullPointerException(
	                    getString(R.string.error_config, name));
	        }
	 }
	 
	 private final BroadcastReceiver mHandleMessageReceiver =
	            new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
	            //throw new NullPointerException("return value is null at method AAA");
	          
	            MyShip_Fragment fragment = (MyShip_Fragment)getSupportFragmentManager().findFragmentByTag(MyShip_Fragment.class.getSimpleName());
	            if(fragment==null) // if the fragment  is not showing, show it
	            {
	            	fragment=new MyShip_Fragment();
	            }
	            fragment.RefreshData(session);
	            switchContent(fragment);
	           
	           
	         	
	        }
	    };
	    
	  //Switches views
	    public void switchContent(final Fragment fragment) {
	       getSupportFragmentManager()
	       .beginTransaction()
	       .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
	       .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
	       .addToBackStack(fragment.getClass().getSimpleName())
	       .commitAllowingStateLoss();
	    }

}