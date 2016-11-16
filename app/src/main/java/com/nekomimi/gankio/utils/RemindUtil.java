package com.nekomimi.gankio.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/26.
 *
 */
public class RemindUtil {
    private static final String TAG = "RemindUtil";

    private static Toast mToast;
    private static Snackbar mSnackBar;

    public static void toast(Context context, String msg, int duration){
        if (mToast == null){
            mToast = Toast.makeText(context,msg,duration);
        }else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public static void snackBar(View view, String msg, String action, int duration, View.OnClickListener listener){
        if(mSnackBar == null || mSnackBar.isShown()){
            Log.d(TAG, "snackBar: if");
            mSnackBar = Snackbar.make(view, msg, duration);
            mSnackBar.setAction(action, listener);
        }else {
            Log.d(TAG, "snackBar: else");
            mSnackBar.setText(msg);
            mSnackBar.setAction(action, listener);
        }
        mSnackBar.show();
    }

}
