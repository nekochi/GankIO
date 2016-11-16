package com.nekomimi.gankio;

import android.app.Application;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.common.UmLog;


/**
 * Created by hongchi on 2016-1-27.
 * File description :
 */
public class NekoApplication extends Application
{
    private static final String TAG = "NekoApplication";
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
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                UmLog.i(TAG, "device token: " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                UmLog.i(TAG, "register failed: " + s + " " +s1);
            }
        });
    }


}

