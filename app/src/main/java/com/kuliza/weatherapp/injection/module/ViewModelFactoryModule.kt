package com.kuliza.weatherapp.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kuliza.weatherapp.injection.scope.ViewModelScope
import com.kuliza.weatherapp.ui.home.HomeViewModel
import com.kuliza.weatherapp.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

  @Binds
  @IntoMap
  @ViewModelScope(HomeViewModel::class)
  abstract fun bindHomeViewModel(homeViewModule: HomeViewModel): ViewModel


  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
