package com.seabreeze.robot.api;

import com.robot.seabreeze.rxretrofit.api.BaseResultApi;
import com.robot.seabreeze.rxretrofit.listener.HttpOnNextListener;
import com.seabreeze.robot.api.service.HttpGetService;
import com.seabreeze.robot.bean.RecommendBean;
import com.seabreeze.robot.utils.JsonParseUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class RecommendApi extends BaseResultApi<RecommendBean> {

    public RecommendApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);

        setBaseUrl("http://112.124.22.238:8081/appstore/");
        setMothed("AppStore/recommend");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpGetService httpGetService = retrofit.create(HttpGetService.class);
        return httpGetService.getRecommendData();
    }

    @Override
    public RecommendBean apply(ResponseBody responseBody) throws Exception {
        String string = "";
        try {
            string = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JsonParseUtils.parseRecommendBean(string);
    }
}
