package com.seabreeze.robot.di.component;

import android.app.Activity;
import android.content.Context;

import com.seabreeze.robot.di.module.ActivityModule;
import com.seabreeze.robot.di.scope.ContextLife;
import com.seabreeze.robot.di.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    @ContextLife("Application")
    Context getApplicationContext();

    @ContextLife("Activity")
    Context getActivityContext();

    Activity getActivity();

//    void inject(MainActivity activity);
}
