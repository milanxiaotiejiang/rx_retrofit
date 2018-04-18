package com.seabreeze.robot.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.seabreeze.robot.base.mvpbase.BasePresenter;
import com.seabreeze.robot.base.mvpbase.BaseView;
import com.seabreeze.robot.di.component.ActivityComponent;
import com.seabreeze.robot.di.component.AppComponent;
import com.seabreeze.robot.di.component.DaggerActivityComponent;
import com.seabreeze.robot.di.module.ActivityModule;

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    protected ActivityComponent mActivityComponent;
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
        mPresenter = initInjector();
        mPresenter.attachView(this);
        initData();
    }

    protected void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(((App) getApplication()).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    protected abstract T initInjector();

}
