package com.kuliza.weatherapp.ui.home

import android.content.Context
import com.kuliza.weatherapp.injection.module.BaseActivityModule
import com.kuliza.weatherapp.injection.qualifiers.ActivityContext
import com.kuliza.weatherapp.injection.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerAppCompatActivity

@Module(includes = arrayOf(BaseActivityModule::class))
abstract class HomeActivityViewModule {

  @Binds
  @ActivityContext abstract fun provideActivityContext(activity: HomeActivity): Context

  @Binds
  @ActivityScope
  abstract fun provideActivity(homeActivity: HomeActivity): DaggerAppCompatActivity
}


