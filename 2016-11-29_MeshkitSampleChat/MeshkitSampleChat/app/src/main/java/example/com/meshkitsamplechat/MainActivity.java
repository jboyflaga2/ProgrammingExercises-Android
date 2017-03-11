package example.com.meshkitsamplechat;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.opengarden.meshkitlibrary.MeshKitManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private MeshkitBroadcastReceiver receiver = new MeshkitBroadcastReceiver();

    @InjectView(R.id.channel_edittext)
    EditText channelEditText;

    @InjectView(R.id.message_edittext)
    EditText messageEditText;

    @InjectView(R.id.current_result_textview)
    TextView currentResultTextView;

    @InjectView(R.id.my_id_textview)
    TextView myIdTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        if (MeshKitManager.getMyId() != null) {
            initUI();
        } else if (MeshKitManager.getLastError() != null) {
            Toast.makeText(this, MeshKitManager.getLastError().errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Application.MESHKIT_INIT_FAILED);
        filter.addAction(Application.MESHKIT_INIT_SUCCESS);
        filter.addAction(Application.MESHKIT_PUBLIC_MESSAGE_RECEIVED);
        filter.addAction(Application.MESHKIT_PRIVATE_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void initUI() {
        myIdTextView.setText("My ID: " + MeshKitManager.getMyId());
        currentResultTextView.setText("Initialization success. endpointId is: " + MeshKitManager.getMyId());
    }

    @OnClick(R.id.subscribe_button)
    protected void onSubscribeClick() {
        try {
            MeshKitManager.subscribe(channelEditText.getText().toString());
        } catch (MeshKitManager.InvalidChannelIdException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Throwable e) {
            Log.e(this.getClass().getName(), e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.publish_button)
    protected void onPublishMessageClick() {
        try {
            MeshKitManager.publishMessage(channelEditText.getText().toString(), messageEditText.getText().toString());
        } catch (MeshKitManager.InvalidMessageException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (MeshKitManager.InvalidChannelIdException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Throwable e) {
            Log.e(this.getClass().getName(), e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    class MeshkitBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
            String action = intent.getAction();
            switch (action) {
                case Application.MESHKIT_INIT_SUCCESS:
                    initUI();
                    break;
                case Application.MESHKIT_INIT_FAILED:
                    //showErrorActivity(intent.getStringExtra("errorMessage"));

                    showMessage(intent.getStringExtra("errorMessage"));

                    break;
                case Application.MESHKIT_PRIVATE_MESSAGE_RECEIVED:
                    String senderId = intent.getStringExtra("senderId");
                    String message = intent.getStringExtra("message");
                    //log.add(0, new ConsoleEntry(String.format("%s: %s", senderId, message), ConsoleEntry.TYPE_MESSAGE_RECEIVED));

                    showMessage(String.format("%s: %s", senderId, message));
                    //adapter.notifyDataSetChanged();
                    break;
                case Application.MESHKIT_PUBLIC_MESSAGE_RECEIVED:
                    senderId = intent.getStringExtra("senderId");
                    message = intent.getStringExtra("message");
                    String channelId = intent.getStringExtra("channelId");
                   // log.add(0, new ConsoleEntry(String.format("%s/ %s: %s", channelId, senderId, message), ConsoleEntry.TYPE_MESSAGE_RECEIVED));

                    showMessage(String.format("%s/ %s: %s", channelId, senderId, message));

                    //adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private void showMessage(String message) {
        Log.e(this.getClass().getName(), message);
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        currentResultTextView.setText(message);
    }

}
