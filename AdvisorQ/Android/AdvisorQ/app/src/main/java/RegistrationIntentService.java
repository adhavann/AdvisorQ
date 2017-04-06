//import android.app.IntentService;
//import android.content.Intent;
//
//import com.example.chinthanbhat.advisorq.R;
//import com.google.android.gms.gcm.GoogleCloudMessaging;
//import com.google.android.gms.iid.InstanceID;
//
//import java.io.IOException;
//
///**
// * Created by Chinthan Bhat on 12/3/2016.
// */
//public class RegistrationIntentService extends IntentService {
//    // ...
//
//    public RegistrationIntentService(){
//        super("Reg service");
//    }
//
//    @Override
//    public void onHandleIntent(Intent intent) {
//        // ...
//        InstanceID instanceID = InstanceID.getInstance(this);
//        try {
//            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//            System.out.println(token);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        // ...
//    }
//    //@Override
//    public void onTokenRefresh() {
//        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
//        Intent intent = new Intent(this, RegistrationIntentService.class);
//        startService(intent);
//    }
//
//
//    // ...
//}