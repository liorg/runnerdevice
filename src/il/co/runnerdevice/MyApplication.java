package il.co.runnerdevice;

import org.acra.*;
import org.acra.annotation.*;
import org.acra.config.ACRAConfiguration;
import org.acra.config.ConfigurationBuilder;

import android.app.Application;

@ReportsCrashes(
	
    formUri = "http://testkipo.kipodeal.co.il/logs/MobileLog.ashx",
    connectionTimeout=2222,
    mode = ReportingInteractionMode.TOAST,
    reportType = org.acra.sender.HttpSender.Type.JSON,
    httpMethod = org.acra.sender.HttpSender.Method.POST,
    forceCloseDialogAfterToast = false, // optional, default false
    resToastText = R.string.crash_toast_text
   )
public class MyApplication extends Application {
    @Override
    public void onCreate() {
       super.onCreate();

        ACRA.init(this);
              //  final ACRAConfiguration config = new ConfigurationBuilder(this)
              //      .setFormUri("http://5.100.251.87:4545/logs/MobileLog.ashx")
              //      .build();
        
        // The following line triggers the initialization of ACRA
        //ACRA.init(this,config);
       // final ACRAConfiguration config = ACRA.getNewDefaultConfig(this);
      //  config.setFormUri("http://5.100.251.87:4545/logs/MobileLog.ashx");
    }    
}