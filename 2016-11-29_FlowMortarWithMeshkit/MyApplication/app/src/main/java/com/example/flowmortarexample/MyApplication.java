package com.example.flowmortarexample;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;

import mortar.Mortar;
import mortar.MortarScope;

import com.opengarden.meshkitlibrary.MeshKitManager;

/**
 * Created by MyndDev on 10/26/2016.
 */

public class MyApplication extends Application {
    private MortarScope applicationScope;

    public static final String MESHKIT_PRIVATE_MESSAGE_RECEIVED = "MESHKIT_PRIVATE_MESSAGE_RECEIVED";
    public static final String MESHKIT_PUBLIC_MESSAGE_RECEIVED = "MESHKIT_PUBLIC_MESSAGE_RECEIVED";
    public static final String MESHKIT_INIT_SUCCESS = "MESHKIT_INIT_SUCCESS";
    public static final String MESHKIT_INIT_FAILED = "MESHKIT_INIT_FAILED";

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    int numberOfNotifications = 0;
    private int notificationId = 100;

    @Override public void onCreate() {
        super.onCreate();
        // Eagerly validate development builds (too slow for production).
        applicationScope = Mortar.createRootScope(BuildConfig.DEBUG);

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder =
                new NotificationCompat.Builder(this)
                        //.setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle("Meshkit Message received")
                        .setOnlyAlertOnce(true)
                        .setOngoing(true)
                        .setContentIntent(resultPendingIntent);

        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



        MeshKitManager.setInitializationCallback(new MeshKitManager.InitializationCallback() {
            @Override
            public void onSuccess(String endpointId) {
                Intent intent = new Intent();
                intent.setAction(MESHKIT_INIT_SUCCESS);
                intent.putExtra("endpointId", endpointId);
                LocalBroadcastManager.getInstance(MyApplication.this).sendBroadcast(intent);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                Intent intent = new Intent();
                intent.setAction(MESHKIT_INIT_FAILED);
                intent.putExtra("errorCode", errorCode);
                intent.putExtra("errorMessage", errorMessage);
                LocalBroadcastManager.getInstance(MyApplication.this).sendBroadcast(intent);
            }
        });


        MeshKitManager.setMessageCallback(new MeshKitManager.MessageCallback() {
            @Override
            public void onPrivateMessageReceived(String message, String senderId) {
                Intent intent = new Intent();
                intent.setAction(MESHKIT_PRIVATE_MESSAGE_RECEIVED);
                intent.putExtra("senderId", senderId);
                intent.putExtra("message", message);
                LocalBroadcastManager.getInstance(MyApplication.this).sendBroadcast(intent);
                showNotification(senderId, message);
            }

            @Override
            public void onPublicMessageReceived(String message, String senderId, String channelId) {
                Intent intent = new Intent();
                intent.setAction(MESHKIT_PUBLIC_MESSAGE_RECEIVED);
                intent.putExtra("senderId", senderId);
                intent.putExtra("message", message);
                intent.putExtra("channelId", channelId);
                LocalBroadcastManager.getInstance(MyApplication.this).sendBroadcast(intent);
                showNotification(channelId, message);
            }
        });

        MeshKitManager.init(this);
    }

    @Override public Object getSystemService(String name) {
        if (Mortar.isScopeSystemService(name)) {
            return applicationScope;
        }
        return super.getSystemService(name);
    }

    public MortarScope getRootScope() {
        return applicationScope;
    }



    private void showNotification(String channelId, String message) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        if (settings.getBoolean("show_notification", true)) {
            mBuilder.setContentText(channelId + ": " + message).setNumber(numberOfNotifications++);
            mNotificationManager.notify(notificationId, mBuilder.build());
        }
    }

}