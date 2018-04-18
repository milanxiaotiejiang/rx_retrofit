package com.robot.seabreeze.rxretrofit.db;

import com.robot.seabreeze.rxretrofit.db.base.BaseManager;
import com.robot.seabreeze.rxretrofit.download.DownInfo;
import com.robot.seabreeze.rxretrofit.download.DownInfoDao;
import com.robot.seabreeze.rxretrofit.http.cookie.CookieResulte;
import com.robot.seabreeze.rxretrofit.http.cookie.CookieResulteDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;

import java.util.List;

public class DownInfoDbManager extends BaseManager<DownInfo, Long> {

    private static DownInfoDbManager mManager;

    /**
     * 获取单例
     *
     * @return
     */
    public static DownInfoDbManager getInstance() {
        if (mManager == null) {
            synchronized (DownInfoDbManager.class) {
                if (mManager == null) {
                    mManager = new DownInfoDbManager();
                }
            }
        }
        return mManager;
    }

    private DownInfoDbManager() {
    }

    @Override
    public AbstractDao<DownInfo, Long> getAbstractDao() {
        return daoSession.getDownInfoDao();
    }


    public void saveDowninfo(DownInfo info) {
        insert(info);
    }

    public void updateDowninfo(DownInfo info) {
        update(info);
    }

    public void deleteDowninfo(DownInfo info) {
        delete(info);
    }

    public DownInfo queryDownBy(long Id) {
        Query<DownInfo> build = null;
        try {
            build = getAbstractDao().queryBuilder()
                    .where(DownInfoDao.Properties.Id.eq(Id))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<DownInfo> downInfos = build.list();
        if (downInfos.isEmpty()) {
            return null;
        } else {
            return downInfos.get(0);
        }
    }

    public List<DownInfo> queryDownAll() {
        return loadAll();
    }
}
