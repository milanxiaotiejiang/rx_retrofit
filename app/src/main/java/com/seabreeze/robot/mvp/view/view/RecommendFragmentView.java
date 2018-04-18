package com.seabreeze.robot.mvp.view.view;

import com.seabreeze.robot.base.mvpbase.BaseView;
import com.seabreeze.robot.bean.RecommendBean;

public interface RecommendFragmentView extends BaseView {

    void onRecommendDataSuccess(RecommendBean recommendBean) ;
    void onRecommendDataMoreSuccess(RecommendBean recommendBean) ;
    void onRecommendDataError(String msg);

}
