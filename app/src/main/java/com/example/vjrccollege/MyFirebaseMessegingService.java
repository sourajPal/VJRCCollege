package com.example.vjrccollege;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;


public class MyFirebaseMessegingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseIdService";
    private static final String TOPIC_GLOBAL = "global";
    private static final String TITLE = "title";
    private static final String EMPTY = "";
    private static final String MESSAGE = "message";
    private static final String IMAGE = "image";
    private static final String ACTION = "action";
    private static final String DATA = "data";
    private static final String ACTION_DESTINATION = "action_destination";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //sendNotification(remoteMessage.getNotification().getBody());
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            //sendNotification(remoteMessage.getNotification().getBody());
            Map<String, String> data = remoteMessage.getData();
            handleData(data);
        }

        // Check if message contains a notification payload.
        else if (remoteMessage.getNotification() != null) {
            //sendNotification(remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification());
        }
    }

        private void handleNotification(RemoteMessage.Notification RemoteMsgNotification) {
            String message = RemoteMsgNotification.getBody();
            String title = RemoteMsgNotification.getTitle();
            NotificationVO notificationVO = new NotificationVO();
            notificationVO.setTitle(title);
            notificationVO.setMessage(message);
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.displayNotification(notificationVO, resultIntent);
            notificationUtils.playNotificationSound();
        }

        private void handleData(Map<String, String> data) {
            String title = data.get(TITLE);
            String message = data.get(MESSAGE);
            String iconUrl = data.get(IMAGE);
            String action = data.get(ACTION);
            String actionDestination = data.get(ACTION_DESTINATION);
            NotificationVO notificationVO = new NotificationVO();
            notificationVO.setTitle(title);
            notificationVO.setMessage(message);
            notificationVO.setIconUrl(iconUrl);
            notificationVO.setAction(action);
            notificationVO.setActionDestination(actionDestination);
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.displayNotification(notificationVO, resultIntent);
            notificationUtils.playNotificationSound();

        }



    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_GLOBAL);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }



    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
