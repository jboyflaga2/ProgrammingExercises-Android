package com.example.flowmortarexample.wheelview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.flowmortarexample.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.adapter.BaseWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mortar.Mortar;

/**
 * Created by MyndDev on 3/24/2017.
 */

public class DateTimeWheelView  extends LinearLayout {

    private Calendar currentDateTime;

    public interface OnDateTimeSelectedChangedListener {
        void onSelectedChanged(Calendar calendar);
    }

    private OnDateTimeSelectedChangedListener selectedChangedListener;

    @InjectView(R.id.sleep_date_wheelview)
    WheelView sleepDateWheelView;

    @InjectView(R.id.sleep_hour_wheelview)
    WheelView sleepHourWheelView;

    @InjectView(R.id.sleep_minutes_wheelview)
    WheelView sleepMinutesWheelView;

    @InjectView(R.id.sleep_period_wheelview)
    WheelView sleepPeriodWheelView;

    public DateTimeWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentDateTime = Calendar.getInstance();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.date_time_wheel_view, this);
        ButterKnife.inject(this, layout);

        DateWheelAdapter adapter1 = new DateWheelAdapter(getContext(), Gravity.RIGHT);
        ArrayWheelAdapter adapter2 = new ArrayWheelAdapter(getContext());
        ArrayWheelAdapter adapter3 = new ArrayWheelAdapter(getContext());
        ArrayWheelAdapter adapter4 = new ArrayWheelAdapter(getContext());

        initWheelView(sleepDateWheelView, adapter1, false, dateSelectedListener);
        initWheelView(sleepHourWheelView, adapter2, true, hourSelectedListener);
        initWheelView(sleepMinutesWheelView, adapter3, true, minutesSelectedListener);
        initWheelView(sleepPeriodWheelView, adapter4, false, periodSelectedListener);

        List<DateWheelData> dates = new ArrayList<>();
        dates.add(new DateWheelData(new Date()));

        List<String> hourList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            String hour = String.valueOf(i);
            hourList.add(hour);
        }

        List<String> minutesList = new ArrayList<>();
        for (int i = 0; i <= 59; i++) {
            minutesList.add(String.format("%02d", i));
        }

        List<String> periodList = new ArrayList<>();
        periodList.add("AM");
        periodList.add("PM");

        sleepDateWheelView.setWheelData(dates);
        sleepHourWheelView.setWheelData(hourList);
        sleepMinutesWheelView.setWheelData(minutesList);
        sleepPeriodWheelView.setWheelData(periodList);
    }

    public void updateDateWheelData(List<Date> dates, int indexToDisplayFirst) {
        List<DateWheelData> sleepDateWheelData = new ArrayList<>();
        for (Date date : dates) {
            DateWheelData dateWheelData = new DateWheelData(date);
            sleepDateWheelData.add(dateWheelData);
        }

        sleepDateWheelView.setWheelData(sleepDateWheelData);
        sleepDateWheelView.setSelection(indexToDisplayFirst);

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(dates.get(indexToDisplayFirst));
        currentDateTime.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DATE));
        if (this.selectedChangedListener != null) {
            this.selectedChangedListener.onSelectedChanged(currentDateTime);
        }
    }

    public void setDateTimeSelectedChangedListener(OnDateTimeSelectedChangedListener listener) {
        this.selectedChangedListener = listener;
    }

    private void initWheelView(WheelView wheelView,
                               BaseWheelAdapter wheelAdapter,
                               boolean setLoop,
                               WheelView.OnWheelItemSelectedListener itemSelectedListener) {
        wheelView.setWheelAdapter(wheelAdapter);
        wheelView.setSkin(WheelView.Skin.Holo);
        wheelView.setWheelSize(5);
        wheelView.setLoop(setLoop);
        wheelView.setStyle(getWheelViewStyle());
        wheelView.setOnWheelItemSelectedListener(itemSelectedListener);
    }

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

    private WheelView.OnWheelItemSelectedListener dateSelectedListener = new WheelView.OnWheelItemSelectedListener() {
        @Override
        public void onItemSelected(int position, Object o) {
            DateWheelData datewheelData = (DateWheelData) o;
            if (selectedChangedListener != null) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.setTime(datewheelData.getDate());
                currentDateTime.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DATE));
                selectedChangedListener.onSelectedChanged(currentDateTime);
            }
        }
    };
    private WheelView.OnWheelItemSelectedListener hourSelectedListener = new WheelView.OnWheelItemSelectedListener() {
        @Override
        public void onItemSelected(int position, Object o) {
            String strHour = (String) o;
            if (selectedChangedListener != null) {
                int hour = Integer.parseInt(strHour);
                currentDateTime.set(Calendar.HOUR, hour);
                selectedChangedListener.onSelectedChanged(currentDateTime);
            }
        }
    };
    private WheelView.OnWheelItemSelectedListener minutesSelectedListener = new WheelView.OnWheelItemSelectedListener() {
        @Override
        public void onItemSelected(int position, Object o) {
            String strMinute = (String) o;
            if (selectedChangedListener != null) {
                int minute = Integer.parseInt(strMinute);
                currentDateTime.set(Calendar.MINUTE, minute);
                selectedChangedListener.onSelectedChanged(currentDateTime);
            }
        }
    };
    private WheelView.OnWheelItemSelectedListener periodSelectedListener = new WheelView.OnWheelItemSelectedListener() {
        @Override
        public void onItemSelected(int position, Object o) {
            String period = (String) o;
            if (selectedChangedListener != null) {
                if (period.toLowerCase().equals("am")) {
                    currentDateTime.set(Calendar.AM_PM, Calendar.AM);
                } else {
                    currentDateTime.set(Calendar.AM_PM, Calendar.PM);
                }

                selectedChangedListener.onSelectedChanged(currentDateTime);
            }
        }
    };
}
