package com.nekomimi.gankio.api.def;

import com.nekomimi.gankio.bean.GankDate;
import com.nekomimi.gankio.bean.GankItem;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/8/31.
 */
public interface AppGet {
    void today(final AppCallBack<GankDate> callBack);
    void day(Calendar calendar, final AppCallBack<GankDate> callBack);
    void day(String year, String month, String day, final AppCallBack<GankDate> callBack);
    void data(String type, String num, String page, final AppCallBack<GankItem> callBack);
}
