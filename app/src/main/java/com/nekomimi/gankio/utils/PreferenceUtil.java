package com.nekomimi.gankio.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/8/26.
 * Preference工具类
 */
public class PreferenceUtil {

    public static final String DEFAULT = "default";
    public static final String SHOULDSHOWTIP = "should_show_tip";

    public static boolean getShouldShowTip(Context context){
        SharedPreferences preferences = getDefaultPreferences(context);
        return preferences.getBoolean(SHOULDSHOWTIP, true);
    }

    public static void setShouldshowtip(Context context, boolean flag){
        SharedPreferences preferences = getDefaultPreferences(context);
        preferences.edit().putBoolean(SHOULDSHOWTIP, flag).apply();
    }

    private static SharedPreferences getDefaultPreferences(Context context){
        return context.getSharedPreferences(DEFAULT, Context.MODE_PRIVATE);
    }
}
