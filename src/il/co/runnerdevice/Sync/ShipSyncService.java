package il.co.runnerdevice.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


//https://github.com/Udinic/SyncAdapter/blob/master/SyncAdapterExample/src/com/udinic/sync_adapter_example_app/syncadapter/TvShowsSyncService.java
/**
 * Service to handle Account sync. This is invoked with an intent with action
 * ACTION_AUTHENTICATOR_INTENT. It instantiates the com.udinic.sync_adapter_example_app.syncadapter and returns its
 * IBinder.
 */
public class ShipSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static ShipSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
                sSyncAdapter = new ShipSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}