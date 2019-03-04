package com.weatherforecastapplication.utills;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

/**
 * Class is used to provide method for date parsing.
 *
 * @author Shubham Chauhan
 */
public class DateUtills {

    public static String getParsedDate(long time, String format) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(time * 1000);
        return DateFormat.format(format, cal).toString();
    }

    public static long getNextDayTimeStamp(){
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTimeInMillis();
    }

    public static long getCurrentTimeStamp(){
        Calendar calendar = Calendar.getInstance(Locale.US);
        return calendar.getTimeInMillis();
    }
}
