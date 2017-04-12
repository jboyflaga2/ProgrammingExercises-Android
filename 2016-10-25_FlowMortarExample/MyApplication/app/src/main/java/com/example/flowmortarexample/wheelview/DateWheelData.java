package com.example.flowmortarexample.wheelview;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateWheelData {
    private Date date;

    public DateWheelData(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getDisplayDateString() {
        String formattedDate = (String) DateFormat.format("MMM d, yyyy", this.date);
        if (isCurrentDate(this.date)) {
            return "Today, " + formattedDate;
        } else {
            return formattedDate;
        }
    }

    public String getDisplayDateTimeString() {
        String formattedDate = (String) DateFormat.format("MMM d, yyyy   h:mm a", this.date);
        if (isCurrentDate(this.date)) {
            return "Today, " + formattedDate;
        } else {
            return formattedDate;
        }
    }

    public static boolean isCurrentDate(Date date) {
        return date != null && getDifferenceInDays(date, new Date()) == 0;
    }

    public static int getDifferenceInDays(Date from, Date to) {
        Calendar fromCalendar = new GregorianCalendar();
        Calendar toCalendar = new GregorianCalendar();

        fromCalendar.setTime(from);
        toCalendar.setTime(to);

        boolean negate = false;
        if (!fromCalendar.before(toCalendar)) {
            negate = true;
            fromCalendar.setTime(to);
            toCalendar.setTime(from);
        }

        int fromDayOfYear = fromCalendar.get(Calendar.DAY_OF_YEAR);
        int toDayOfYear = toCalendar.get(Calendar.DAY_OF_YEAR);
        int dayDifference, yearMultiplier;

        dayDifference = -fromDayOfYear;
        yearMultiplier = toCalendar.get(Calendar.YEAR) - fromCalendar.get(Calendar.YEAR);

        while (yearMultiplier > 0) {
            dayDifference += fromCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            fromCalendar.add(Calendar.YEAR, 1);
            yearMultiplier--;
        }
        dayDifference += toDayOfYear;

        if (dayDifference > 0 && negate) {
            return -dayDifference;
        } else {
            return dayDifference;
        }
    }

}
