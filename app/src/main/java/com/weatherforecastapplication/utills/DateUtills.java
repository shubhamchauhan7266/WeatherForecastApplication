package com.weatherforecastapplication.utills;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtills {

    public static String parseDate(String format, Calendar calendar) {

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        return sdf.format(calendar.getTime());
    }

    @NonNull
    public static Date getParsedDate(String date, String format) {
        try {
            return new SimpleDateFormat(format, Locale.US).parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

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

    /**
     * Method to compare two dates on time bases
     *
     * @param date1 date one
     * @param date2 date two
     * @return 0 if both equal , 1 if date one time is lesser and -1 if date two time is lesser
     */
    public static int compareTime(Date date1, Date date2) {
        Calendar calTemp = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        calTemp.setTime(date1);
        cal1.set(calTemp.get(Calendar.YEAR), calTemp.get(Calendar.MONTH), calTemp.get(Calendar.DAY_OF_MONTH), calTemp.get(Calendar.HOUR_OF_DAY), calTemp.get(Calendar.MINUTE));
        calTemp.setTime(date2);
        cal2.set(calTemp.get(Calendar.YEAR), calTemp.get(Calendar.MONTH), calTemp.get(Calendar.DAY_OF_MONTH), calTemp.get(Calendar.HOUR_OF_DAY), calTemp.get(Calendar.MINUTE));

        if (cal1.before(cal2)) {
            return 1;
        } else if (cal2.before(cal1)) {
            return -1;
        } else {
            return 0;
        }
    }
}
