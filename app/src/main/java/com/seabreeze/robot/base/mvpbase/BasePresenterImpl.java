package com.seabreeze.robot.base.mvpbase;

public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T> {

    protected T mPresenterView;

    @Override
    public void attachView(T t) {
        this.mPresenterView = t;
    }

    @Override
    public void detachView() {
        this.mPresenterView = null;
    }
}
