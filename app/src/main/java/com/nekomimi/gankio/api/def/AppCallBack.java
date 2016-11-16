package com.nekomimi.gankio.api.def;

/**
 * Created by Administrator on 2016/8/24.
 */
public interface AppCallBack<T> {
    void onSuccess(int result, T data);

    void onFailed(int result, String errorMsg);
}
