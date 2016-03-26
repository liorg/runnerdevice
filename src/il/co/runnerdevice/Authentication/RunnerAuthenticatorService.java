package il.co.runnerdevice.Authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 19/03/13
 * Time: 19:10
 */
public class RunnerAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        RunnerAuthenticatorAdaptor  authenticator = new RunnerAuthenticatorAdaptor(this);
        return authenticator.getIBinder();
    }
}