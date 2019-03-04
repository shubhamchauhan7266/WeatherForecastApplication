package com.weatherforecastapplication.utills;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

/**
 * Class is used to check given runtime permission.
 */
public class AndroidPermissionUtils {

    /**
     * Method is used to check all given runtime permission.
     *
     * @param context        context
     * @param permissionList A List of Android runtime permission
     * @return true if all permission is granted , otherwise false
     */
    public static boolean checkPermission(Context context, String... permissionList) {

        for (String permission : permissionList) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method is used to request for all given runtime permission.
     *
     * @param context        context
     * @param requestCode    requestCode
     * @param permissionList A list of all given runtime permission
     */
    public static void requestForPermission(Activity context, int requestCode, String... permissionList) {

        ActivityCompat.requestPermissions(context, permissionList, requestCode);
    }
}
