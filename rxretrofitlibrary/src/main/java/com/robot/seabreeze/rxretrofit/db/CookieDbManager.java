package com.robot.seabreeze.rxretrofit.db;

import com.robot.seabreeze.rxretrofit.db.base.BaseManager;
import com.robot.seabreeze.rxretrofit.download.DaoMaster;
import com.robot.seabreeze.rxretrofit.http.cookie.CookieResulte;
import com.robot.seabreeze.rxretrofit.http.cookie.CookieResulteDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class CookieDbManager extends BaseManager<CookieResulte, Long> {

    private static CookieDbManager mManager;

    /**
     * 获取单例
     *
     * @return
     */
    public static CookieDbManager getInstance() {
        if (mManager == null) {
            synchronized (CookieDbManager.class) {
                if (mManager == null) {
                    mManager = new CookieDbManager();
                }
            }
        }
        return mManager;
    }

    private CookieDbManager() {
    }

    @Override
    public AbstractDao<CookieResulte, Long> getAbstractDao() {
        return daoSession.getCookieResulteDao();
    }

    public void saveCookie(CookieResulte info) {
        insert(info);
    }

    public void updateCookie(CookieResulte info) {
        update(info);
    }

    public void deleteCookie(CookieResulte info) {
        delete(info);
    }

    public CookieResulte queryCookieBy(String url) {
        Query<CookieResulte> build = null;
        try {
            build = getAbstractDao().queryBuilder()
                    .where(CookieResulteDao.Properties.Url.eq(url))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CookieResulte> cookieResultes = build.list();
        if (cookieResultes.isEmpty()) {
            return null;
        } else {
            return cookieResultes.get(0);
        }
    }

    public List<CookieResulte> queryCookieAll() {
        return loadAll();
    }
}
