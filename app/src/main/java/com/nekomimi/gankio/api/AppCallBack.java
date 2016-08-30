package com.nekomimi.gankio.api;

/**
 * Created by Administrator on 2016/8/24.
 */
public interface AppCallBack<T> {
    void onSuccess(int result, T data);

    void onFailed(int result, String errorMsg);
}
