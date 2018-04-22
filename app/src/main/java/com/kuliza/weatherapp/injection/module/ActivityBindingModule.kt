package com.kuliza.weatherapp.injection.module

import com.kuliza.weatherapp.injection.scope.ActivityScope
import com.kuliza.weatherapp.ui.home.HomeActivity
import com.kuliza.weatherapp.ui.home.HomeActivityViewModule
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerAppCompatActivity


@Module
abstract class ActivityBindingModule {

  @ActivityScope
  @ContributesAndroidInjector(
      modules = arrayOf(HomeActivityViewModule::class))
  internal abstract fun bindHomeActivity(): HomeActivity


}


@Module(includes = arrayOf(BaseActivityModule::class))
abstract class ActivityModule<in T : DaggerAppCompatActivity> {
  @Binds
  @ActivityScope
  internal abstract fun bindActivity(activity: T): DaggerAppCompatActivity
}

/**
 * Activity specific common dependencies should be placed here
 */
@Module
open class BaseActivityModule {
  @ActivityScope
  @Provides internal fun provideRxPermissions(activity: DaggerAppCompatActivity) = RxPermissions(
      activity)
}