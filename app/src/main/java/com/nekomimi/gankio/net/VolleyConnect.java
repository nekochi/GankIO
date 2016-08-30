package com.nekomimi.gankio.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nekomimi.gankio.NekoApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by hongchi on 2016-1-28.
 * File description :
 */
public class VolleyConnect {

    private VolleyConnect() {
    }

    private static VolleyConnect mInstance = null;
    private RequestQueue mRequestQueue;

    public static VolleyConnect getInstance() {
        if (mInstance == null) {
            mInstance = new VolleyConnect();
            mInstance.mRequestQueue = Volley.newRequestQueue(NekoApplication.getInstance());
        }
        return mInstance;
    }

    public void add(Request request)
    {
        if(mInstance != null)
        {
            mInstance.mRequestQueue.add(request);
        }
    }

    public static String makeHtml(String url,Map<String,String> params,String encode) {
        if (params == null)
        {
            return url;
        }
        StringBuilder urlResult = new StringBuilder();
        try {
            urlResult.append(url).append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlResult.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), encode)).append("&");
            }
            urlResult.deleteCharAt(urlResult.length() - 1);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return urlResult.toString();
    }
}