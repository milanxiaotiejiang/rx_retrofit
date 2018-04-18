package com.seabreeze.robot.di.component;

import android.content.Context;

import com.seabreeze.robot.di.module.AppModule;
import com.seabreeze.robot.di.scope.ContextLife;
import com.seabreeze.robot.di.scope.PerApp;

import dagger.Component;

@PerApp
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    Context getApplication();
}
