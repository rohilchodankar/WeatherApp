package com.kuliza.weatherapp

import com.kuliza.weatherapp.data.services.WeatherService
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class WeatherAPIUnitTest  {


  var service: WeatherService? = null


  @Before
  @Throws(Exception::class)
  fun setup() {
    service = Retrofit.Builder().baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build().create(WeatherService::class.java)

  }

  @Test
  @Throws(Exception::class)
  fun loadWeatherData() {
    service?.let {
      it.getForecastWeather(BuildConfig.API_KEY,"Bangalore",7)
          .subscribeOn(Schedulers.newThread())
          .subscribe({ response ->
            System.out.print(response)
            assertNotNull(response)
            assertNotNull(response.forecast)
          }, {
              throw Exception()
          })
    }
  }


}
