package com.seabreeze.robot.di.module;

import android.content.Context;

import com.seabreeze.robot.base.App;
import com.seabreeze.robot.di.scope.ContextLife;
import com.seabreeze.robot.di.scope.PerActivity;
import com.seabreeze.robot.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App mApp;

    public AppModule(App app) {
        this.mApp = app;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideAppContext() {
        return mApp.getApplicationContext();
    }
}
