package com.seabreeze.robot.api.delegate;

/**
 * 网络请求回调
 */

public interface IGetDataDelegate<T> {
    void getDataSuccess(T t);

    void getDataError(String errmsg);
}
