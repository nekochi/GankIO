package com.nekomimi.gankio.api.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nekomimi.gankio.api.def.AppCallBack;
import com.nekomimi.gankio.api.def.AppGet;
import com.nekomimi.gankio.bean.GankDate;
import com.nekomimi.gankio.bean.GankItem;
import com.nekomimi.gankio.net.GsonGetRequest;
import com.nekomimi.gankio.net.VolleyConnect;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

/**
 * Created by hongchi on 2016-3-2.
 * File description :
 */
public class AppGetAction implements AppGet
{
    private static AppGetAction mAppGetAction = null;
    private static final String HOST = "http://gank.io/api";

    public static final int DAY_REQUEST = 100001;
    public static final int DATA_REQUEST = 100002;

    public static AppGetAction getInstance()
    {
        if(mAppGetAction == null)
        {
            mAppGetAction = new AppGetAction();
        }
        return mAppGetAction;
    }

    private AppGetAction() {}

    public String makeUrl(String orgUrl, String ...s)
    {
        StringBuilder sb = new StringBuilder(orgUrl);
        try {
            for(String str : s)
            {
                sb.append("/").append(URLEncoder.encode(str,"UTF-8"));
            }
        }catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public void today(final AppCallBack<GankDate> callBack)
    {
        Calendar calendar = Calendar.getInstance();
        day(calendar.get(Calendar.YEAR)+"",calendar.get(Calendar.MONTH)+1+"",calendar.get(Calendar.DAY_OF_MONTH)+"", callBack);
    }

    @Override
    public void day(Calendar calendar, final AppCallBack<GankDate> callBack)
    {
        day(calendar.get(Calendar.YEAR) + "", calendar.get(Calendar.MONTH) + 1 + "", calendar.get(Calendar.DAY_OF_MONTH) + "", callBack);
    }

    @Override
    public void day(String year, String month, String day, final AppCallBack<GankDate> callBack)
    {

        GsonGetRequest<GankDate> request = new GsonGetRequest<>(
                makeUrl(HOST, "day", year, month, day),
                GankDate.class,null, new Response.Listener<GankDate>() {
            @Override
            public void onResponse(GankDate response) {
                int result = 1;
                if ((!response.isError()) && response.getResults().getAll().size() != 0) {
                    result = 0;
                } else {
                    result = 1;
                }
                callBack.onSuccess(result, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailed(1, "volleyNetError");
            }
        });
        VolleyConnect.getInstance().add(request);
    }

    @Override
    public void data(String type, String num, String page, final AppCallBack<GankItem> callBack)
    {
        GsonGetRequest<GankItem> request = new GsonGetRequest<>(
                makeUrl(HOST, "data", type, num, page),
                GankItem.class,null, new Response.Listener<GankItem>() {
            @Override
            public void onResponse(GankItem response) {
                int result = 1;
                if((!response.isError()) && response.getResults().size() != 0)
                {
                   result = 0;
                }else
                {
                    result = 1;
                }
                callBack.onSuccess(result, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailed(1, "volleyNetError");
            }
        });
        VolleyConnect.getInstance().add(request);
    }
}
