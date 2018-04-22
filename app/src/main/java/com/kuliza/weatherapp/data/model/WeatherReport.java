package com.kuliza.weatherapp.data.model;


/**
 * Created by rohilchodankar on 4/22/18.
 */

public class WeatherReport {

  private Forecast forecast;

  private Location location;

  private Current current;

  public Forecast getForecast ()
  {
    return forecast;
  }

  public void setForecast (Forecast forecast)
  {
    this.forecast = forecast;
  }

  public Location getLocation ()
  {
    return location;
  }

  public void setLocation (Location location)
  {
    this.location = location;
  }

  public Current getCurrent ()
  {
    return current;
  }

  public void setCurrent (Current current)
  {
    this.current = current;
  }

  @Override
  public String toString()
  {
    return "WeatherReport [forecast = "+forecast+", location = "+location+", current = "+current+"]";
  }

}
