package com.seabreeze.robot.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.robot.seabreeze.log.Log;
import com.seabreeze.robot.R;
import com.seabreeze.robot.base.BaseFragment;
import com.seabreeze.robot.base.BaseMvpActivity;
import com.seabreeze.robot.base.BaseMvpFragment;
import com.seabreeze.robot.bean.RecommendBean;
import com.seabreeze.robot.mvp.presenter.RecommendFragmentPresenter;
import com.seabreeze.robot.mvp.presenter.impl.RecommendFragmentPresenterImpl;
import com.seabreeze.robot.mvp.view.view.RecommendFragmentView;
import com.seabreeze.robot.utils.UIUtils;
import com.seabreeze.robot.view.MultipleStatusView;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class RecommendFragment extends BaseMvpFragment<RecommendFragmentPresenter> implements RecommendFragmentView {


    private RecommendBean mRecommendBean;

    @Inject
    RecommendFragmentPresenterImpl mRecommendFragmentPresenter;


    @Override
    protected RecommendFragmentPresenter initInjector() {
        mFragmentComponent.inject(this);
        return mRecommendFragmentPresenter;
    }

    @Override
    public View createSuccessView() {
        View view = UIUtils.inflate(R.layout.fragment_recommend);
        ButterKnife.bind(this,view) ;
        return view;
    }

    @Override
    public void load() {
        mRecommendFragmentPresenter.getRecommendData(mActivity);
    }

    @Override
    public void initView() {

    }

    @Override
    public void onRecommendDataSuccess(RecommendBean recommendBean) {
        mRecommendBean = recommendBean;
        Log.e(mRecommendBean.getBannerList().size());
        setState(MultipleStatusView.LoadResult.success);
    }

    @Override
    public void onRecommendDataMoreSuccess(RecommendBean recommendBean) {

    }

    @Override
    public void onRecommendDataError(String msg) {
        setState(MultipleStatusView.LoadResult.error);
    }
}
