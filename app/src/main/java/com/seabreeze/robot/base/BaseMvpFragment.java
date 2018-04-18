package com.seabreeze.robot.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.seabreeze.robot.base.mvpbase.BasePresenter;
import com.seabreeze.robot.base.mvpbase.BaseView;
import com.seabreeze.robot.di.component.DaggerActivityComponent;
import com.seabreeze.robot.di.component.DaggerFragmentComponent;
import com.seabreeze.robot.di.component.FragmentComponent;
import com.seabreeze.robot.di.module.FragmentModule;

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    protected T mPresenter;
    protected FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentComponent();
        mPresenter = initInjector();
        mPresenter.attachView(this);
    }

    private void initFragmentComponent() {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .appComponent(((App) getActivity().getApplication()).getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    protected abstract T initInjector();
}
