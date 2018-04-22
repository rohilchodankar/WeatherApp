package com.kuliza.weatherapp.data.services

import com.kuliza.weatherapp.data.model.WeatherReport
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
  @GET("forecast.json")
  fun getForecastWeather(
      @Query("key")  apikey :String,
      @Query("q")    country : String,
      @Query("days") days : Int): Single<WeatherReport>
}