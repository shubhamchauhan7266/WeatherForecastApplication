package com.weatherforecastapplication.utills;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;

import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.R;

import java.io.IOException;
import java.io.InputStream;

public class OtherUtils {

    /**
     * Method to show Alert Dialog with only positive button with not callback
     *
     * @param message            message for alert
     * @param positiveButtonText ok button text
     */
    public static void showAlertDialog(String message, String positiveButtonText, Context context) {
        if (context == null || ((Activity) context).isDestroyed() || ((Activity) context).isFinishing()) {
            return;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(positiveButtonText, null);
        builder.setMessage(Html.fromHtml(message));
        builder.setTitle(context.getResources().getString(R.string.app_name));
        if (!((BaseActivity) context).isDestroyed() && !((BaseActivity) context).isFinishing()) {
            try {
                builder.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method is used to load json data from json file in Asset.
     *
     * @param context context
     * @return json data in the form of object.
     */
    public static String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("city_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
