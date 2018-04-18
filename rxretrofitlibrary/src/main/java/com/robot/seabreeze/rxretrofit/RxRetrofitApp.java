package com.robot.seabreeze.rxretrofit;

import android.app.Application;

import com.robot.seabreeze.log.Log;
import com.robot.seabreeze.log.inner.ConsoleTree;
import com.robot.seabreeze.log.inner.FileTree;
import com.robot.seabreeze.log.inner.LogcatTree;
import com.robot.seabreeze.rxretrofit.db.base.BaseManager;
import com.robot.seabreeze.rxretrofit.utils.FileUtil;

import java.io.File;

public class RxRetrofitApp {

    private static Application application;
    private static boolean debug;

    public static void init(Application app, boolean debug) {

        initLog(app, debug);

        BaseManager.initOpenHelper(app);

        setApplication(app);
    }

    public static Application getApplication() {
        return application;
    }

    private static void setApplication(Application application) {
        RxRetrofitApp.application = application;
    }

    public static void initLog(Application app, boolean debug) {
        RxRetrofitApp.debug = debug;
        if (debug) {
            Log.getLogConfig().configAllowLog(true).configShowBorders(false);
            Log.plant(new FileTree(app, FileUtil.getCacheDir(app) + File.separator + "log"));
            Log.plant(new ConsoleTree());
            Log.plant(new LogcatTree());
        }
    }

    public static boolean isDebug() {
        return debug;
    }

}
