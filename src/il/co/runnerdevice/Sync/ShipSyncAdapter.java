package il.co.runnerdevice.Sync;

//https://github.com/Udinic/SyncAdapter/blob/master/SyncAdapterExample/src/com/udinic/sync_adapter_example_app/syncadapter/TvShowsSyncAdapter.java

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

//import com.udinic.sync_adapter_example.authentication.AccountGeneral;
//import com.udinic.sync_adapter_example_app.db.TvShowsContract;
//import com.udinic.sync_adapter_example_app.db.dao.TvShow;

import il.co.runnerdevice.Api.ServiceGenerator;
import il.co.runnerdevice.Api.ShipApi;
import il.co.runnerdevice.Authentication.AccountGeneral;
import il.co.runnerdevice.Pojo.ItemSyncGeneric;
import il.co.runnerdevice.Pojo.ResponseItem;
import il.co.runnerdevice.Pojo.WhoAmI;
import il.co.runnerdevice.Utils.CommonUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import retrofit2.Call;
public class ShipSyncAdapter extends AbstractThreadedSyncAdapter {

	private static final String TAG = "ShipSyncAdapter";

    private final AccountManager mAccountManager;

    
    public ShipSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
    }
    
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,ContentProviderClient provider, SyncResult syncResult) {
        // Building a print of the extras we got
        StringBuilder sb = new StringBuilder();
        if (extras != null) {
            for (String key : extras.keySet()) {
                sb.append(key + "[" + extras.get(key) + "] ");
            }
        }
        Log.d(CommonUtilities.APP_NAME, TAG + "> onPerformSync for account[" + account.name + "]. Extras: "+sb.toString());
     //   Call<ResponseItem<ItemSyncGeneric<WhoAmI>>> call = getUser.
        Retrofit retrofit = new Retrofit.Builder().baseUrl(CommonUtilities.URL)
				.addConverterFactory(GsonConverterFactory.create()).build();
        
        ShipApi userApiService = ServiceGenerator.createService(ShipApi.class,
				mAccountManager, account);

        String userId=mAccountManager.getUserData(account,AccountGeneral.PARAM_USER_ID);
        String deviceId="";
        String clientId=AccountGeneral.CLIENT_ID;
        
       
        try {
        	Call<ResponseItem<ItemSyncGeneric<WhoAmI>>> getMyChangeDetail =userApiService.GetWhoAmISync(userId, deviceId, clientId);
             
            Log.d(CommonUtilities.APP_NAME, TAG + "> Get remote TV Shows");
            // Get shows from remote
            Log.d(CommonUtilities.APP_NAME, TAG + "> Get local TV Shows");
            Log.d(CommonUtilities.APP_NAME, TAG + "> Finished.");

        }
     
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
