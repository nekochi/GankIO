package com.nekomimi.gankio.base;

import android.os.Handler;
import android.os.Message;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nekomimi.gankio.bean.GankDate;
import com.nekomimi.gankio.net.GsonGetRequest;
import com.nekomimi.gankio.net.VolleyConnect;

import java.util.Calendar;

/**
 * Created by hongchi on 2016-3-2.
 * File description :
 */
public class AppAction
{
    private static AppAction mAppAction = null;
    private static final String HOST = "http://gank.io/api";

    public static AppAction getInstance()
    {
        if(mAppAction == null)
        {
            mAppAction = new AppAction();
        }
        return mAppAction;
    }

    private AppAction() {}

    public void today(final Handler handler)
    {
        Calendar calendar = Calendar.getInstance();
        day(calendar.get(Calendar.YEAR)+"",calendar.get(Calendar.MONTH)+1+"",calendar.get(Calendar.DAY_OF_MONTH)+"",handler);
    }

    public void day(Calendar calendar, final Handler handler)
    {
        day(calendar.get(Calendar.YEAR) + "", calendar.get(Calendar.MONTH) + 1 + "", calendar.get(Calendar.DAY_OF_MONTH) + "", handler);
    }

    public void day(String year, String month, String day, final Handler handler)
    {

        GsonGetRequest<GankDate> request = new GsonGetRequest<>(
                makeUrl(HOST, "day", year, month, day),
              GankDate.class,null, new Response.Listener<GankDate>() {
            @Override
            public void onResponse(GankDate response) {
                Message message = new Message();
                if(  (!response.isError()) && response.getResults().getAll().size() != 0)
                {
                    message.what = 0;
                }else
                {
                    message.what = 1;
                }
                message.obj = response;
                handler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyConnect.getInstance().add(request);
    }

    public String makeUrl(String orgUrl, String ...s)
    {
        StringBuilder sb = new StringBuilder(orgUrl);
        for(String str : s)
        {
            sb.append("/").append(str);
        }
        return sb.toString();
    }
}
