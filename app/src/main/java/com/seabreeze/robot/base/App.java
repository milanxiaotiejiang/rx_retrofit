package com.seabreeze.robot.base;

import android.app.Application;
import android.os.Handler;

import com.robot.seabreeze.rxretrofit.RxRetrofitApp;
import com.seabreeze.robot.BuildConfig;
import com.seabreeze.robot.di.component.AppComponent;
import com.seabreeze.robot.di.component.DaggerAppComponent;
import com.seabreeze.robot.di.module.AppModule;


public class App extends Application {

    private static App context;

    private static int mMainThreadId;
    private static Handler mHandler;

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        mHandler = new Handler();

        initApplicationComponent();

        RxRetrofitApp.init(this, BuildConfig.DEBUG);
    }

    private void initApplicationComponent() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent(){
        return mAppComponent ;
    }

    public static App getContext() {
        return context;
    }

    /**
     * 返回主线程的pid
     *
     * @return
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 返回Handler
     *
     * @return
     */
    public static Handler getHandler() {
        return mHandler;
    }
}
