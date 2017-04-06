import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.chinthanbhat.advisorq.MainActivity;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Chinthan Bhat on 12/3/2016.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";


    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.e(TAG, "From: " + from);
        Log.e(TAG, "Message: " + message);
        sendNotification(message);

    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                //.setSmallIcon(R.drawable.logo)
//                .setContentTitle("Sorry!!")
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //notificationManager.notify(0, notificationBuilder.build());
    }
}