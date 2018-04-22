package com.kuliza.weatherapp.ui.home

import android.arch.lifecycle.MutableLiveData
import com.kuliza.weatherapp.BuildConfig
import com.kuliza.weatherapp.data.services.ApiService
import com.kuliza.weatherapp.injection.scope.ActivityScope
import com.kuliza.weatherapp.ui.base.BaseViewModel
import com.kuliza.weatherapp.utils.IRxSchedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class HomeViewModel @Inject constructor() : BaseViewModel() {

  @Inject lateinit var service: ApiService
  @Inject lateinit var schedulers: IRxSchedulers

  var joke: MutableLiveData<String> = MutableLiveData()

  fun loadData() {
    addDisposable(service.getForecastWeather(BuildConfig.API_KEY,"India",7)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.main())
        .subscribe({ response ->
          Timber.d(response.toString())
        }, { Timber.e(it) }))
  }
}