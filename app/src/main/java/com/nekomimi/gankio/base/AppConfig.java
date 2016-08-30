package com.nekomimi.gankio.base;

import android.content.Context;
import android.content.SharedPreferences;

import com.nekomimi.gankio.NekoApplication;

/**
 * Created by hongchi on 2016-1-27.
 * File description :
 */
public class AppConfig
{
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences mCookiesSP;
    private static AppConfig mAppConfig;

    public static AppConfig getInstance()
    {
        if(mAppConfig == null)
        {
            mAppConfig = new AppConfig();
            mAppConfig.mContext = NekoApplication.getInstance();
            mAppConfig.mSharedPreferences = mAppConfig.mContext.getSharedPreferences("App_Config",Context.MODE_PRIVATE);
            mAppConfig.mCookiesSP = mAppConfig.mContext.getSharedPreferences("App_Cookies",Context.MODE_PRIVATE);
        }
        return mAppConfig;
    }
}
