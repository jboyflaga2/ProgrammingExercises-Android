package com.example.flowmortarexample.wheelview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flowmortarexample.R;
import com.wx.wheelview.adapter.BaseWheelAdapter;

/**
 * Created by MyndDev on 2/17/2017.
 */

// copied from the sample provided by the author of WheelView:
// \venshine\WheelView\sample\src\main\java\com\wx\wheelview\demo\MyWheelAdapter.java
public class DateWheelAdapter extends BaseWheelAdapter<DateWheelData> {

    private final Context mContext;
    private final int gravity;

    public DateWheelAdapter(Context context, int gravity) {
        this.mContext = context;
        this.gravity = gravity;
    }

    @Override
    protected View bindView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.date_wheel_view_item, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.date_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position).getDisplayDateString());
        viewHolder.textView.setGravity(gravity);
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
