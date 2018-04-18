package com.robot.seabreeze.rxretrofit.api;

import com.robot.seabreeze.rxretrofit.exception.HttpTimeException;
import com.robot.seabreeze.rxretrofit.listener.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * 请求统一封装
 *
 * @param <T>
 */
public abstract class BaseResultApi<T> implements Function<ResponseBody/*BaseResultEntity<T>*/, T> {

    //rx生命周期管理
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;

    //回调
    private SoftReference<HttpOnNextListener> mListener;

    //是否能取消加载框
    private boolean cancel;

    //是否显示加载框
    private boolean showProgress;

    //是否需要缓存处理
    private boolean cache;

    //baseUrl
    private String baseUrl;

    //方法-如果需要缓存必须设置这个参数；不需要不用設置
    private String mothed;

    //超时时间-默认6秒
    private int connectionTime = 6;

    //有网情况下的本地缓存时间默认60秒
    private int cookieNetWorkTime = 60;

    //无网络的情况下本地缓存时间默认30天
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;

    //失败后retry次数
    private int retryCount = 1;

    //失败后retry延迟
    private long retryDelay = 100;

    //失败后retry叠加延迟
    private long retryIncreaseDelay = 10;

    public BaseResultApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {

        setListener(listener);

        setRxAppCompatActivity(rxAppCompatActivity);

        setShowProgress(false);

        setCache(false);

        setCancel(true);

        setCookieNetWorkTime(60);

        setCookieNoNetWorkTime(24 * 60 * 60);
    }

    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference(rxAppCompatActivity);
    }

    public SoftReference<HttpOnNextListener> getListener() {
        return mListener;
    }

    public void setListener(HttpOnNextListener listener) {
        this.mListener = new SoftReference(listener);
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMothed() {
        return mothed;
    }

    public void setMothed(String mothed) {
        this.mothed = mothed;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
    }

    /**
     * url
     *
     * @return
     */
    public String getUrl() {
        return baseUrl + mothed;
    }

    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
    public abstract Observable getObservable(Retrofit retrofit);


//    @Override
//    public T apply(BaseResultEntity<T> httpResult) {
//        if (httpResult.getRet() == 0) {
//            throw new HttpTimeException(httpResult.getMsg());
//        }
//        return httpResult.getData();
//    }
}
