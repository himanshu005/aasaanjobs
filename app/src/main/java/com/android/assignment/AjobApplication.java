package com.android.assignment;

import android.app.Application;

import com.android.assignment.service.persistence.DaoMaster;
import com.android.assignment.service.persistence.DaoSession;


public class AjobApplication extends Application {

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "database.db").getWritableDb()).newSession();

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}

