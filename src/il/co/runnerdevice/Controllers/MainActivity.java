package il.co.runnerdevice.Controllers;

import il.co.runnerdevice.R;

import il.co.runnerdevice.Api.ShipApi;
import il.co.runnerdevice.Pojo.WhoAmI;
import il.co.runnerdevice.Pojo.WhoAmIResponse;

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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.android.gcm.GCMRegistrar;


//http://www.androidwarriors.com/2015/12/retrofit-20-android-example-web.html
public class MainActivity  extends FragmentActivity {
	// String url = "http://api.openweathermap.org";
	  String url = "http://testkipo.kipodeal.co.il";
	    TextView  txt_pressure;
	    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_retrofit);
		txt_pressure = (TextView) findViewById(R.id.txt_press);
		  getReport();
	}
	
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	
	void getReport() {
	
		 Retrofit retrofit = new Retrofit.Builder()
         .baseUrl(url)
         .addConverterFactory(GsonConverterFactory.create())
         .build();

		 ShipApi service = retrofit.create(ShipApi.class);

	      Call<WhoAmIResponse> call = service.WhoAmi();


	      call.enqueue(new Callback<WhoAmIResponse>() {
	        	 
				@Override
				public void onFailure(Call<WhoAmIResponse> arg0, Throwable arg1) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onResponse(Call<WhoAmIResponse> arg0,
						Response<WhoAmIResponse> arg1) {
					// TODO Auto-generated method stub
					 try {
		                    String pressure = arg1.body().getModel().getFullName();
		                    
		                    txt_pressure.setText("pressure  :  " + pressure);
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
					
				}
	        	 });
    }
}
