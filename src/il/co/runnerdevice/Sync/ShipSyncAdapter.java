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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

//import com.udinic.sync_adapter_example.authentication.AccountGeneral;
//import com.udinic.sync_adapter_example_app.db.TvShowsContract;
//import com.udinic.sync_adapter_example_app.db.dao.TvShow;

import il.co.runnerdevice.Api.ServiceGenerator;
import il.co.runnerdevice.Api.ShipApi;
import il.co.runnerdevice.Authentication.AccountGeneral;
import il.co.runnerdevice.Dblocal.DatabaseHelper;
import il.co.runnerdevice.Dblocal.ShippingContentProvider;
import il.co.runnerdevice.Dblocal.ShippingContract;
import il.co.runnerdevice.Dblocal.Dao.UserDao;
import il.co.runnerdevice.Pojo.ItemSyncGeneric;
import il.co.runnerdevice.Pojo.ResponseItem;
import il.co.runnerdevice.Pojo.WhoAmI;
import il.co.runnerdevice.Utils.CommonUtilities;
import il.co.runnerdevice.Utils.SyncStateRecord;
import il.co.runnerdevice.Utils.UtilConvertor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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

	@SuppressWarnings("deprecation")
	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		// Building a print of the extras we got
		StringBuilder sb = new StringBuilder();
		if (extras != null) {
			for (String key : extras.keySet()) {
				sb.append(key + "[" + extras.get(key) + "] ");
			}
		}
		Log.d(CommonUtilities.APP_NAME, TAG + "> onPerformSync for account["
				+ account.name + "]. Extras: " + sb.toString());
		// Call<ResponseItem<ItemSyncGeneric<WhoAmI>>> call = getUser.

		boolean isSyncIsForced = (extras != null)
				&& (extras.getBoolean("force", false));
		Log.d(CommonUtilities.APP_NAME, TAG + "isSyncIsForced="
				+ isSyncIsForced);

		Retrofit retrofit = new Retrofit.Builder().baseUrl(CommonUtilities.URL)
				.addConverterFactory(GsonConverterFactory.create()).build();

		ShipApi userApiService = ServiceGenerator.createService(ShipApi.class,
				mAccountManager, account);

		String userId = mAccountManager.getUserData(account,
				AccountGeneral.PARAM_USER_ID);
		String deviceId = "";
		String clientId = AccountGeneral.CLIENT_ID;

		try {

			Log.d(CommonUtilities.APP_NAME, TAG + "> Get local");
			// todo
			// Get shows from the local storage
			 Uri userUri = Uri.parse("content://"+ShippingContract.AUTHORITY+"/"+ShippingContentProvider.PATH_USER_CHANGED);

			ArrayList localUsers = new ArrayList();
			Cursor curUser = provider.query(userUri, null, null, null, null);
			
			curUser.moveToFirst();
			WhoAmI whoami=UserDao.fromCursor(curUser);
			
			curUser.close();
			ItemSyncGeneric<WhoAmI> bodyUpdate=new ItemSyncGeneric<WhoAmI>();
			
			
			String dateToStr=UtilConvertor.ConvertDateToISO(new Date());
			
			bodyUpdate.setLastUpdateRecord(dateToStr);
			bodyUpdate.setSyncObject(whoami);
			
			Response<ResponseItem<ItemSyncGeneric<WhoAmI>>> updateOnRemote = userApiService.UpdateWhoAmISync(bodyUpdate).execute();
			Log.d(CommonUtilities.APP_NAME, TAG + "> Get is updateOnRemote.isSuccessful()=" +updateOnRemote.isSuccessful());
			
			Log.d(CommonUtilities.APP_NAME, TAG + "> Get remote ");
			Call<ResponseItem<ItemSyncGeneric<WhoAmI>>> getMyChangeDetail = userApiService
					.GetWhoAmISync(userId, deviceId, clientId);
			Response<ResponseItem<ItemSyncGeneric<WhoAmI>>> response = getMyChangeDetail
					.execute();
			if (response.isSuccessful() && response.body() != null
					&& response.body().isIsAuthenticated()) {
				ResponseItem<ItemSyncGeneric<WhoAmI>> body = response.body();
				if (!body.isIsError()) {
					ItemSyncGeneric<WhoAmI> model = body.getModel();
					if (model.getSyncObject() != null
							&& model.getSyncStateRecord() == SyncStateRecord.Change
							|| model.getSyncStateRecord() == SyncStateRecord.Add) {
						WhoAmI whoamiChanged = model.getSyncObject();

						// update record
						ContentValues values = new ContentValues();

						values.put(DatabaseHelper.KEY_FIRSTNAME,
								whoamiChanged.getFirstName());
						values.put(DatabaseHelper.KEY_LASTNAME,
								whoamiChanged.getLastName());
						String url = "content://" + ShippingContract.AUTHORITY
								+ "/" + ShippingContentProvider.PATH_USER + "/"
								+ userId;
						Uri currentUser = Uri.parse(url);
						Log.d(CommonUtilities.APP_NAME, TAG + "> before update");
						provider.update(currentUser, values, null, null);
						Log.d(CommonUtilities.APP_NAME, TAG + "> after update");
						// has change
						// provider.update(url, values, selection,
						// selectionArgs)
					}
				} else {
					// todo
					Log.d(CommonUtilities.APP_NAME, TAG + "> logic error");
					syncResult.stats.numIoExceptions++;
				}

			} else {
				// todo
				Log.d(CommonUtilities.APP_NAME, TAG + "> autontication error");
				syncResult.stats.numAuthExceptions = syncResult.stats.numAuthExceptions + 1;
				syncResult.databaseError = true;
				Log.d(CommonUtilities.APP_NAME, TAG + ">numAuthExceptions= "
						+ syncResult.stats.numAuthExceptions);

				Log.d(CommonUtilities.APP_NAME, TAG + ">delayUntil= "
						+ syncResult.delayUntil);

			}

			Log.d(CommonUtilities.APP_NAME, TAG + "> Finished.");
//
		}

		catch (Exception e) {
			e.printStackTrace();
			syncResult.stats.numIoExceptions++;
			Log.e(CommonUtilities.APP_NAME, e.getMessage());
		}
	}
}
