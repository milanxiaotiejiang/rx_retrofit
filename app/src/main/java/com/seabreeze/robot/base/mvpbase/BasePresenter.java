package com.seabreeze.robot.base.mvpbase;

public interface BasePresenter<T extends BaseView> {

    void attachView(T t);
    void detachView();
}
