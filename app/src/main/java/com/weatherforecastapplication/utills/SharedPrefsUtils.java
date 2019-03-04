/*
 *
 *  Proprietary and confidential. Property of Kellton Tech Solutions Ltd. Do not disclose or distribute.
 *  You must have written permission from Kellton Tech Solutions Ltd. to use this code.
 *
 */

package com.weatherforecastapplication.utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Utility class to store/retrieve values in SharedPreferences.
 *
 * @author Shubham Chauhan
 */
public class SharedPrefsUtils {

    /**
     * @param pContext pContext
     * @param pFileName pFileName
     * @param pKey pKey
     * @param pDefault pDefault
     * @return value
     */
    public static int getSharedPrefInt(Context pContext, String pFileName, String pKey, int pDefault) {
        SharedPreferences _sharedPref = pContext.getSharedPreferences(pFileName, Context.MODE_PRIVATE);
        return _sharedPref.getInt(pKey, pDefault);
    }

    /**
     * @param pContext pContext
     * @param pFileName pFileName
     * @param pKey pKey
     * @param pValue pValue
     */
    public static void setSharedPrefInt(Context pContext, String pFileName, String pKey, int pValue) {
        SharedPreferences _sharedPref = pContext.getSharedPreferences(pFileName, Context.MODE_PRIVATE);
        Editor _editor = _sharedPref.edit();
        _editor.putInt(pKey, pValue);
        _editor.apply();
    }
}
