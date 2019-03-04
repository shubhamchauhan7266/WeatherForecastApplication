package com.weatherforecastapplication.utills;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.util.Log;

import com.weatherforecastapplication.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class is used to provide method for alert dialog and other functionality.
 *
 * @author Shubham Chauhan
 */
public class OtherUtils {

    private static final String TAG = OtherUtils.class.getSimpleName();

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

    /**
     * Method to show Alert Dialog with only positive button with callback
     *
     * @param message            message for alert
     * @param positiveButtonText ok button text
     */
    public static void showAlertDialog(String message, String positiveButtonText, Context context, DialogInterface.OnClickListener clickListener) {
        Log.v(TAG, "showAlertDialog");
        if (context == null || ((Activity) context).isDestroyed() || ((Activity) context).isFinishing()) {
            return;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(positiveButtonText, clickListener);
        builder.setMessage(Html.fromHtml(message));
        builder.setCancelable(false);
        builder.setTitle(context.getResources().getString(R.string.app_name));
        if (!((Activity) context).isDestroyed() && !((Activity) context).isFinishing()) {
            builder.show();
        }
    }
}
