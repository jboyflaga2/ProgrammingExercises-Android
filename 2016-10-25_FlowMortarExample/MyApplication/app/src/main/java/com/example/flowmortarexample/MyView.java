package com.example.flowmortarexample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import javax.inject.Inject;

import mortar.Mortar;

/**
 * Created by MyndDev on 10/26/2016.
 */

public class MyView extends LinearLayout {
    @Inject
    MyScreen.Presenter presenter;
    private EditText someText;
    private TextView resultText;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Mortar.inject(context, this);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();

        someText = (EditText) findViewById(R.id.some_text);
        resultText = (TextView) findViewById(R.id.result_text);

        findViewById(R.id.some_button).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                presenter.someButtonClicked();
            }
        });

        presenter.takeView(this);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    public String getSomeText() {
        return someText.getText().toString();
    }

    public void showResult(SomeResult result) {
        resultText.setText(result.getText());
    }
}