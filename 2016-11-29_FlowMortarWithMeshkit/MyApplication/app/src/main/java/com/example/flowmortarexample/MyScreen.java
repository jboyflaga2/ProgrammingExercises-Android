package com.example.flowmortarexample;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.opengarden.meshkitlibrary.MeshKitManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;

/**
 * Created by MyndDev on 10/26/2016.
 */
@Layout(R.layout.my_view)
public class MyScreen implements Blueprint {

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module(injects = MyView.class)
    public class Module {
    }

    @Singleton
    public static class Presenter extends ViewPresenter<MyView> {

        private MeshkitBroadcastReceiver receiver = new MeshkitBroadcastReceiver();

        @Override
        public void onLoad(Bundle savedInstanceState) {

            if (MeshKitManager.getMyId() != null) {
                getView().initUI();
            } else if (MeshKitManager.getLastError() != null) {
                getView().showToast(MeshKitManager.getLastError().errorMessage);
            }

            IntentFilter filter = new IntentFilter();
            filter.addAction(MyApplication.MESHKIT_INIT_FAILED);
            filter.addAction(MyApplication.MESHKIT_INIT_SUCCESS);
            filter.addAction(MyApplication.MESHKIT_PUBLIC_MESSAGE_RECEIVED);
            filter.addAction(MyApplication.MESHKIT_PRIVATE_MESSAGE_RECEIVED);
            LocalBroadcastManager.getInstance(getView().getContext()).registerReceiver(receiver, filter);
        }

        @Override
        public void onSave(Bundle outState) {

        }

        @Override
        public void dropView(MyView view) {
            LocalBroadcastManager.getInstance(getView().getContext()).unregisterReceiver(receiver);
            super.dropView(view);
        }

        public void subscribeTo(String channel) {
            try {
                MeshKitManager.subscribe(channel);
            } catch (MeshKitManager.InvalidChannelIdException e) {
                e.printStackTrace();
                getView().showToast(e.getMessage());
            } catch (Throwable e) {
                Log.e(this.getClass().getName(), e.getMessage());
                getView().showToast(e.getMessage());
            }
        }

        public void publishMessage(String channel, String message) {
            try {
                MeshKitManager.publishMessage(channel, message);
            } catch (MeshKitManager.InvalidMessageException e) {
                e.printStackTrace();
                getView().showToast(e.getMessage());
            } catch (MeshKitManager.InvalidChannelIdException e) {
                e.printStackTrace();
                getView().showToast(e.getMessage());
            } catch (Throwable e) {
                Log.e(this.getClass().getName(), e.getMessage());
                getView().showToast(e.getMessage());
            }

        }

        class MeshkitBroadcastReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                ((NotificationManager) getView().getContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
                String action = intent.getAction();
                switch (action) {
                    case MyApplication.MESHKIT_INIT_SUCCESS:
                        getView().initUI();
                        break;
                    case MyApplication.MESHKIT_INIT_FAILED:
                        //showErrorActivity(intent.getStringExtra("errorMessage"));

                        getView().showMessage(intent.getStringExtra("errorMessage"));

                        break;
                    case MyApplication.MESHKIT_PRIVATE_MESSAGE_RECEIVED:
                        String senderId = intent.getStringExtra("senderId");
                        String message = intent.getStringExtra("message");
                        //log.add(0, new ConsoleEntry(String.format("%s: %s", senderId, message), ConsoleEntry.TYPE_MESSAGE_RECEIVED));

                        getView().showMessage(String.format("%s: %s", senderId, message));
                        //adapter.notifyDataSetChanged();
                        break;
                    case MyApplication.MESHKIT_PUBLIC_MESSAGE_RECEIVED:
                        senderId = intent.getStringExtra("senderId");
                        message = intent.getStringExtra("message");
                        String channelId = intent.getStringExtra("channelId");
                        // log.add(0, new ConsoleEntry(String.format("%s/ %s: %s", channelId, senderId, message), ConsoleEntry.TYPE_MESSAGE_RECEIVED));

                        getView().showMessage(String.format("%s/ %s: %s", channelId, senderId, message));

                        //adapter.notifyDataSetChanged();
                        break;
                }
            }
        }

    }
}