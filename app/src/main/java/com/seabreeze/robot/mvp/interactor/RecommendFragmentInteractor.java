package com.seabreeze.robot.mvp.interactor;

import com.robot.seabreeze.rxretrofit.http.HttpManager;
import com.robot.seabreeze.rxretrofit.listener.HttpOnNextListener;
import com.seabreeze.robot.api.RecommendApi;
import com.seabreeze.robot.api.delegate.IGetDataDelegate;
import com.seabreeze.robot.base.BaseActivity;
import com.seabreeze.robot.bean.RecommendBean;
import com.seabreeze.robot.utils.JsonParseUtils;

import javax.inject.Inject;

public class RecommendFragmentInteractor {

    @Inject
    public RecommendFragmentInteractor() {

    }

    private IGetDataDelegate<RecommendBean> mDelegate;

    public void loadRecommendData(BaseActivity activity, IGetDataDelegate<RecommendBean> delegate) {
        this.mDelegate = delegate;

        RecommendApi recommendApi = new RecommendApi(httpListener, activity);
        HttpManager httpManager = HttpManager.getInstance();
        httpManager.doHttpDeal(recommendApi);
    }

    HttpOnNextListener httpListener = new HttpOnNextListener<RecommendBean>() {
        @Override
        public void onNext(RecommendBean recommendBean) {
            mDelegate.getDataSuccess(recommendBean);
        }

        @Override
        public void onCacheNext(String string) {
            super.onCacheNext(string);
            RecommendBean recommendBean = JsonParseUtils.parseRecommendBean(string);
            mDelegate.getDataSuccess(recommendBean);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mDelegate.getDataError(e.getMessage());
        }
    };
}
