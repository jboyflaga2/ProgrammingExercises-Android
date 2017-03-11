package com.example.flowmortarexample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opengarden.meshkitlibrary.MeshKitManager;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mortar.Mortar;

/**
 * Created by MyndDev on 10/26/2016.
 */

public class MyView extends LinearLayout {
    @Inject
    MyScreen.Presenter presenter;

    @InjectView(R.id.channel_edittext)
    EditText channelEditText;

    @InjectView(R.id.message_edittext)
    EditText messageEditText;

    @InjectView(R.id.current_result_textview)
    TextView currentResultTextView;

    @InjectView(R.id.my_id_textview)
    TextView myIdTextView;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Mortar.inject(context, this);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.inject(this);

        presenter.takeView(this);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    @OnClick(R.id.subscribe_button)
    protected void onSubscribeClick() {
        presenter.subscribeTo(channelEditText.getText().toString());
    }

    @OnClick(R.id.publish_button)
    protected void onPublishMessageClick() {
        presenter.publishMessage(channelEditText.getText().toString(), messageEditText.getText().toString());
    }

    public void initUI() {
        myIdTextView.setText("My ID: " + MeshKitManager.getMyId());
        currentResultTextView.setText("Initialization success. endpointId is: " + MeshKitManager.getMyId());
    }
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showMessage(String message) {
        Log.e(this.getClass().getName(), message);
        showToast(message);
        currentResultTextView.setText(message);
    }
}