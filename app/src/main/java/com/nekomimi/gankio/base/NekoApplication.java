package com.nekomimi.gankio.base;

import android.app.Application;

/**
 * Created by hongchi on 2016-1-27.
 * File description :
 */
public class NekoApplication extends Application
{
    private static NekoApplication mNekoApplication = null;

    public static NekoApplication getInstance()
    {
        return mNekoApplication;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mNekoApplication = this;
    }
}

