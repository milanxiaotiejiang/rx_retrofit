package com.robot.seabreeze.rxretrofit.http.cookie;

import com.robot.seabreeze.rxretrofit.db.CookieDbManager;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * gson持久化截取保存数据
 */
public class CookieInterceptor implements Interceptor {

    private CookieDbManager mCookieDbManager;

    //是否缓存标识
    private boolean cache;

    //url
    private String url;

    public CookieInterceptor(boolean cache, String url) {
        mCookieDbManager = CookieDbManager.getInstance();
        this.url = url;
        this.cache = cache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (cache) {
            ResponseBody body = response.body();
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String bodyString = buffer.clone().readString(charset);
            CookieResulte resulte = mCookieDbManager.queryCookieBy(url);
            long time = System.currentTimeMillis();
            /*保存和更新本地数据*/
            if (resulte == null) {
                resulte = new CookieResulte(url, bodyString, time);
                mCookieDbManager.saveCookie(resulte);
            } else {
                resulte.setResulte(bodyString);
                resulte.setTime(time);
                mCookieDbManager.updateCookie(resulte);
            }
        }
        return response;
    }
}
