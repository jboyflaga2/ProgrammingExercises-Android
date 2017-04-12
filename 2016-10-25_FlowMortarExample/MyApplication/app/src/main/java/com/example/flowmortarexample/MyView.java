package com.example.flowmortarexample;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.flowmortarexample.wheelview.DateTimeWheelView;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private DateTimeWheelView.OnDateTimeSelectedChangedListener dateTimeSelectedChangedListener
            = new DateTimeWheelView.OnDateTimeSelectedChangedListener() {
        @Override
        public void onSelectedChanged(Calendar calendar) {
            SimpleDateFormat PRETTY_DATETIME = new SimpleDateFormat("MMMM dd, yyyy 'at' h:mm a", Locale.US);
            resultText.setText(PRETTY_DATETIME.format(calendar.getTime()));
        }
    };

    private WheelView.WheelViewStyle getWheelViewStyle() {
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#0177B2");
        style.textColor = Color.parseColor("#F5F5F5");
        style.selectedTextColor = Color.parseColor("#F5F5F5");
        style.holoBorderColor = Color.parseColor("#F5F5F5");
        style.textSize = 12;
        style.selectedTextSize = 20;
        return style;
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

    public void showDateTimeWheelView() {
        List<Date> dates = new ArrayList<>();
        for (int i = -15; i <= 15; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, i);
            Date currDate = cal.getTime();

            dates.add(currDate);
        }

        DateTimeWheelView dateTimeWheelView = (DateTimeWheelView) findViewById(R.id.datetime_wheelview);
        dateTimeWheelView.setDateTimeSelectedChangedListener(dateTimeSelectedChangedListener);
        dateTimeWheelView.updateDateWheelData(dates, 15);
    }
}