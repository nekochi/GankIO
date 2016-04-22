package com.nekomimi.gankio.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hongchi on 2016-4-21.
 * File description :
 */
public class NetUtil {
    public static final int NoNetwork = 0;
    public static final int WifiState = 1;
    public static final int MobileState = 2;
    public static final int ErrorState = -999999;
    static NetworkInfo.State mWifiState = null;
    static NetworkInfo.State mMobileState = null;

    public static int getNetworkState(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mMobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        mWifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        if(mMobileState!=null && mWifiState!=null && NetworkInfo.State.CONNECTED!=mWifiState && NetworkInfo.State.CONNECTED == mMobileState)
        {
            return MobileState;
        }
        else if (mMobileState!=null && mWifiState!=null && NetworkInfo.State.CONNECTED==mWifiState && NetworkInfo.State.CONNECTED != mMobileState)
        {
            return WifiState;
        }else if (mMobileState!=null && mWifiState!=null && NetworkInfo.State.CONNECTED!=mWifiState && NetworkInfo.State.CONNECTED != mMobileState)
        {
            return NoNetwork;
        }
        return ErrorState;
    }
}
