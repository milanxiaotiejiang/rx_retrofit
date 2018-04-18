package com.seabreeze.robot.mvp.presenter.impl;

import com.seabreeze.robot.api.delegate.IGetDataDelegate;
import com.seabreeze.robot.base.BaseActivity;
import com.seabreeze.robot.base.mvpbase.BasePresenterImpl;
import com.seabreeze.robot.bean.RecommendBean;
import com.seabreeze.robot.mvp.interactor.RecommendFragmentInteractor;
import com.seabreeze.robot.mvp.presenter.RecommendFragmentPresenter;
import com.seabreeze.robot.mvp.view.view.RecommendFragmentView;

import javax.inject.Inject;

public class RecommendFragmentPresenterImpl extends BasePresenterImpl<RecommendFragmentView> implements RecommendFragmentPresenter {

    @Inject
    RecommendFragmentInteractor interactor;

    @Inject
    public RecommendFragmentPresenterImpl() {

    }

    @Override
    public void getRecommendData(BaseActivity activity) {
        interactor.loadRecommendData(activity, new IGetDataDelegate<RecommendBean>() {
            @Override
            public void getDataSuccess(RecommendBean recommendBean) {
                mPresenterView.onRecommendDataSuccess(recommendBean);
            }

            @Override
            public void getDataError(String errmsg) {
                mPresenterView.onRecommendDataError(errmsg);
            }
        });
    }

    @Override
    public void getRecommendDataMore(BaseActivity activity) {
        interactor.loadRecommendData(activity, new IGetDataDelegate<RecommendBean>() {
            @Override
            public void getDataSuccess(RecommendBean recommendBean) {
                mPresenterView.onRecommendDataSuccess(recommendBean);
            }

            @Override
            public void getDataError(String errmsg) {
                mPresenterView.onRecommendDataError(errmsg);
            }
        });
    }
}
