package com.seabreeze.robot.api.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface HttpGetService {


    @GET("recommend")
    Observable<ResponseBody> getRecommendData();

}
