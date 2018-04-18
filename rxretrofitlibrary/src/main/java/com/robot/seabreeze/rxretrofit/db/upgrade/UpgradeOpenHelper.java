package com.robot.seabreeze.rxretrofit.db.upgrade;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.robot.seabreeze.rxretrofit.download.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class UpgradeOpenHelper extends DaoMaster.OpenHelper {

    public UpgradeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

}
