package com.kuliza.weatherapp.injection.component

import com.kuliza.weatherapp.BaseApplication
import com.kuliza.weatherapp.injection.module.ActivityBindingModule
import com.kuliza.weatherapp.injection.module.AppModule
import com.kuliza.weatherapp.injection.module.NetworkModule
import com.kuliza.weatherapp.injection.module.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ViewModelFactoryModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class,
    NetworkModule::class))
interface AppComponent : AndroidInjector<BaseApplication> {

  @Component.Builder abstract class Builder : AndroidInjector.Builder<BaseApplication>()
}
