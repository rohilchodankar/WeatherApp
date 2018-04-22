package com.kuliza.weatherapp.data.model;

/**
 * Created by rohilchodankar on 4/22/18.
 */

public class Forecast
{
  private Forecastday[] forecastday;

  public Forecastday[] getForecastday ()
  {
    return forecastday;
  }

  public void setForecastday (Forecastday[] forecastday)
  {
    this.forecastday = forecastday;
  }

  @Override
  public String toString()
  {
    return "WeatherReport [forecastday = "+forecastday+"]";
  }
}