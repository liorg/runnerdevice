package il.co.runnerdevice;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

//import android.app.Fragment;
//import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
// exmaple on
//http://www.tutorialsbuzz.com/2014/03/watsapp-custom-listview-imageview-textview-baseadapter.html
public class MyShip_Fragment extends Fragment implements OnItemClickListener {
	SessionManager session;
	AlertDialogManager alert = new AlertDialogManager();
	View rootView;
	ArrayList<ShipItemView> rowItems;
	ListView mylistview;
    ShipListAdaptor adapter ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 rootView = inflater.inflate(R.layout.myship_fragment, container, false);

		session = new SessionManager(getActivity().getApplicationContext());
		
	    
	    session.checkLogin();
	    if(rowItems==null || rowItems.isEmpty())
	    	rowItems=new ArrayList<ShipItemView>();
	    
	    RetryRefreshData(session);
	 
	    mylistview = (ListView)rootView.findViewById(R.id.listvw);
	    adapter = new ShipListAdaptor(this.getActivity(), rowItems);
	    
	    mylistview.setAdapter(adapter);
	    //// Set the emptyView to the ListView
	    TextView emptyField=(TextView)rootView.findViewById(R.id.emptyElement);
	    if(rowItems==null || rowItems.isEmpty()){
	    emptyField.setText("טוען נתונים...");
	    mylistview.setEmptyView(rootView.findViewById(R.id.emptyElement));
	    }
	    mylistview.setOnItemClickListener(this);
	   
	    return rootView;
	   }
       
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
	     long id) 
	   {
		ShipItemView ship = rowItems.get(position);
	    String member_name = ship.getMember_name();
	    ShipItem_Fragment fragment = new ShipItem_Fragment(ship);
	    
	    Toast.makeText(getActivity().getApplicationContext(), "" + member_name, Toast.LENGTH_SHORT).show();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
	   }
       
	public void FillGrid(JSONObject  data){
		   TextView emptyField=(TextView)rootView.findViewById(R.id.emptyElement);
		  
		    rowItems=new ArrayList<ShipItemView>();
		    try{
		    JSONArray jsonarray = data.getJSONArray("Model");
		    if(jsonarray==null || jsonarray.length()==0){
		    	 emptyField.setText("א");
		    }
		    for(int i=0; i<jsonarray.length(); i++){
		        JSONObject obj = jsonarray.getJSONObject(i);

		        String name =obj.getString("Name");
		        String number = obj.getString("TitleDesc");
		        String status =obj.getString("Status");
		        
		        ShipItemView item = new ShipItemView(name, R.drawable.ic_account , number, status);
		        rowItems.add(item);
		    }   
	   } 
	    catch (JSONException eej) 
	    {
	        eej.printStackTrace();
	        //Log.e(eej);
	    }
		 adapter.updateAdapter(rowItems);
	   }

	public void RefreshData(SessionManager session){
		   new GetShips(session,this).execute();
	   }
	
	public void RetryRefreshData(SessionManager session){
		   if(rowItems==null || rowItems.isEmpty()){
			   RefreshData(session);
		   }
		   
	   }
	  
     public class GetShips extends AsyncTask <Void, Void, Void> {
			
	    	SessionManager _session;
	        ProgressDialog _dialog;
	        String _refresh_token;
	        String _token;
	        String _currentDate;
	        String _expiredDate;
	        JSONObject  _data;
	        boolean  _invalidGrant;
	        MyShip_Fragment _activity;
	         @Override
	         protected void onPreExecute()
	         {
	        	 Toast.makeText(getActivity().getApplicationContext(), "טוען נתונים ...", Toast.LENGTH_LONG).show();
	             super.onPreExecute();
	            // _dialog = ProgressDialog.show(getActivity(), "נא המתן", "נא המתן שיתבצע התהליך", true, false);
	         }
	         
		    public GetShips(SessionManager session,MyShip_Fragment activity){
				super();
				_session=session;
				_activity=activity;
		    }
		
			@Override
			protected Void doInBackground(Void... params) {
				String body = null;
				JSONObject  access_token;
				HttpResponse response=null;
				//boolean isErr;
				_refresh_token=_session.GetRefreshKeyToken();
				_token=_session.GetToken();
				_currentDate=_session.GetCurrentDate();
				_expiredDate=_session.GetExpiredDate();
				
				 List<NameValuePair> nameValuePairs=CustomHttpClient.GetRefreshToken(_session);
				 
				 HttpClient httpClient = new DefaultHttpClient();
				 HttpContext localContext = new BasicHttpContext();
		      
		         if(!_session.IsValidToken())
		         {
		         HttpPost httpRefrshToken=new HttpPost("http://5.100.251.87:4545/token");
		         httpRefrshToken.setHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=UTF-8");
				 try 
				 {
					 httpRefrshToken.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
				 } 
				 catch (UnsupportedEncodingException e) 
				 {
				        e.printStackTrace();
				 }
				// Execute HTTP Post Request
				    try 
				    {
				        response = httpClient.execute(httpRefrshToken);
				        Log.d("Response RefreshToken:" , response.toString());
				        body = CustomHttpClient.GetASCIIContentFromEntity(response.getEntity());
				        access_token = new JSONObject(body);
				        
				        if(access_token.has("error")){
				        	//isErr=true;
				        	_invalidGrant=true;
				        	 return null;
				        }
				        
				        _refresh_token=access_token.getString("refresh_token");
				        _token=access_token.getString("access_token");
				        _currentDate=access_token.getString("m:currentTime");
				        _expiredDate=access_token.getString("m:expiredOn");
				        _session.setRefreshToken(_refresh_token,_currentDate ,_expiredDate,_token);
				    } 
				    catch (JSONException eej) {
				        eej.printStackTrace();
				    }
				    catch (IOException e) {
				        e.printStackTrace();
				    }
		         }
		        
		         HttpGet httpGet = new HttpGet("http://5.100.251.87:4545/api/Ship/GetMyShips");
		         CustomHttpClient.SetHeaderToken(httpGet,_session);
		       
		         try {
		                response = httpClient.execute(httpGet, localContext);
		                HttpEntity entity = response.getEntity();
		                body = CustomHttpClient.GetASCIIContentFromEntity(entity);
		                _data = new JSONObject(body);
		         } 
		         catch (Exception e) {
		        	  e.getLocalizedMessage();
		         }
	             return null;
			}	 
			 
			protected void onPostExecute(Void result) {
			//	_dialog.dismiss();
				Boolean err;
				String errDesc="";
				if(_invalidGrant==true)
				{
					_session.RedirctToLogin();
					return;
				}
				
				if(_data==null)
				{
					alert.showAlertDialog(getActivity(), " failed..", "Error Ocuur", false);
					return;
				}
				try {
					
					if(_data.has("IsError"))
					{
						err = _data.getBoolean("IsError");
						if(err)
						{
							errDesc = _data.has("error_description")? _data.getString("error_description"):"error occur!!!";
							alert.showAlertDialog(getActivity(), "Login failed..", errDesc, false);
					       return;
						}
					}
					_activity.FillGrid(_data);
					// alert.showAlertDialog(getActivity(), "Ok", "Ok", false);
				
				} 
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
	    }
		
	}

