package com.robot.seabreeze.rxretrofit.subscribers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.robot.seabreeze.rxretrofit.RxRetrofitApp;
import com.robot.seabreeze.rxretrofit.api.BaseResultApi;
import com.robot.seabreeze.rxretrofit.db.CookieDbManager;
import com.robot.seabreeze.rxretrofit.exception.HttpTimeException;
import com.robot.seabreeze.rxretrofit.http.cookie.CookieResulte;
import com.robot.seabreeze.rxretrofit.listener.HttpOnNextListener;
import com.robot.seabreeze.rxretrofit.utils.AppUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> extends DisposableObserver<T> {

    /*是否弹框*/
    private boolean showPorgress = true;

    /* 软引用回调接口*/
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;

    /*软引用反正内存泄露*/
    private SoftReference<RxAppCompatActivity> mActivity;

    /*加载框可自己定义*/
    private ProgressDialog pd;

    /*请求数据*/
    private BaseResultApi api;

    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseResultApi api) {
        this.api = api;
        this.mSubscriberOnNextListener = api.getListener();
        this.mActivity = new SoftReference<>(api.getRxAppCompatActivity());
        setShowPorgress(api.isShowProgress());
        if (api.isShowProgress()) {
            initProgressDialog(api.isCancel());
        }
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (mSubscriberOnNextListener.get() != null) {
                            mSubscriberOnNextListener.get().onCancel();
                        }
                        onCancelProgress();
                    }
                });
            }
        }
    }


    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowPorgress())
            return;
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing()) {
            pd.show();
        }
    }

    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (!isShowPorgress())
            return;
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }


    public boolean isShowPorgress() {
        return showPorgress;
    }

    /**
     * 是否需要弹框设置
     *
     * @param showPorgress
     */
    public void setShowPorgress(boolean showPorgress) {
        this.showPorgress = showPorgress;
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    protected void onStart() {
        showProgressDialog();
        /*缓存并且有网*/
        if (api.isCache() && AppUtil.isNetworkAvailable(RxRetrofitApp.getApplication())) {
            /*获取缓存数据*/
            CookieResulte cookieResulte = CookieDbManager.getInstance().queryCookieBy(api.getUrl());
            if (cookieResulte != null) {
                long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                if (time < api.getCookieNetWorkTime()) {
                    if (mSubscriberOnNextListener.get() != null) {
                        mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte());
                    }
                    onComplete();
                    dispose();
                }
            }
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        /*需要緩存并且本地有缓存才返回*/
        if (api.isCache()) {
            Observable.just(api.getUrl()).subscribe(new DisposableObserver<String>() {
                @Override
                public void onNext(String s) {
                    /*获取缓存数据*/
                    CookieResulte cookieResulte = CookieDbManager.getInstance().queryCookieBy(s);
                    if (cookieResulte == null) {
                        throw new HttpTimeException("网络错误");
                    }
                    long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                    if (time < api.getCookieNoNetWorkTime()) {
                        if (mSubscriberOnNextListener.get() != null) {
                            mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte());
                        }
                    } else {
                        CookieDbManager.getInstance().deleteCookie(cookieResulte);
                        throw new HttpTimeException("网络错误");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    errorDo(e);
                }

                @Override
                public void onComplete() {

                }
            });
        } else {
            errorDo(e);
        }
    }

    /*错误统一处理*/
    private void errorDo(Throwable e) {
        Context context = mActivity.get();
        if (context == null) return;
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(e);
        }
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        dismissProgressDialog();
    }
}