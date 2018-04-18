package com.seabreeze.robot.di.component;

import android.app.Activity;
import android.content.Context;

import com.seabreeze.robot.di.module.AppModule;
import com.seabreeze.robot.di.module.FragmentModule;
import com.seabreeze.robot.di.scope.ContextLife;
import com.seabreeze.robot.di.scope.PerFragment;
import com.seabreeze.robot.mvp.view.fragment.RecommendFragment;

import dagger.Component;

@PerFragment
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();


    void inject(RecommendFragment fragment) ;
}
