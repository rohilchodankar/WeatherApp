package com.kuliza.weatherapp.ui.home

import android.arch.lifecycle.MutableLiveData
import android.location.Geocoder
import com.kuliza.weatherapp.BuildConfig
import com.kuliza.weatherapp.data.model.WeatherReport
import com.kuliza.weatherapp.data.services.WeatherService
import com.kuliza.weatherapp.injection.scope.ActivityScope
import com.kuliza.weatherapp.ui.base.BaseViewModel
import com.kuliza.weatherapp.utils.IRxSchedulers
import com.kuliza.weatherapp.utils.LocationUtils
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject


@ActivityScope
class HomeViewModel @Inject constructor() : BaseViewModel() {

  @Inject lateinit var service: WeatherService
  @Inject lateinit var schedulers: IRxSchedulers
  @Inject
  lateinit var locationUtil : LocationUtils

  var weatherReport : MutableLiveData<WeatherReport> = MutableLiveData()
  var viewType : MutableLiveData<Int> = MutableLiveData()
  var city : MutableLiveData<String> = MutableLiveData()


  fun loadData() {
    viewType.postValue(0)
    locationUtil.requestPermission().onErrorReturn({ error ->  viewType.postValue(2)
       false }).subscribe { Allowed ->
      run {
        if (Allowed) {
          val geocoder = Geocoder(locationUtil.activity.baseContext, Locale.getDefault())
          locationUtil.getLocation().subscribe { location, error ->
            run {
              val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
              val cityName = addresses[0].locality
              city.postValue(cityName)
              addDisposable(service.getForecastWeather(BuildConfig.API_KEY,cityName,7)
                  .subscribeOn(schedulers.io())
                  .observeOn(schedulers.main())
                  .subscribe({ response ->
                    viewType.postValue(1)
                    weatherReport.postValue(response)
                  }, {
                    viewType.postValue(2)
                    Timber.e(it) }))
            }
          }
        }
      }
    }
  }
}