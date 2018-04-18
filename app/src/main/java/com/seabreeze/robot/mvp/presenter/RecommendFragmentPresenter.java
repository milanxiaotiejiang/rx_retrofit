package com.seabreeze.robot.mvp.presenter;

import com.seabreeze.robot.base.BaseActivity;
import com.seabreeze.robot.base.mvpbase.BasePresenter;
import com.seabreeze.robot.mvp.view.view.RecommendFragmentView;

public interface RecommendFragmentPresenter extends BasePresenter<RecommendFragmentView> {

    void getRecommendData(BaseActivity activity);

    void getRecommendDataMore(BaseActivity activity);
}
