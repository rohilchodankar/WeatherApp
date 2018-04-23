package com.kuliza.weatherapp.ui.home

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kuliza.weatherapp.R
import com.kuliza.weatherapp.data.model.Forecastday
import com.kuliza.weatherapp.databinding.LayoutItemForecastBinding
import com.kuliza.weatherapp.injection.scope.ActivityScope
import javax.inject.Inject

/**
 * Created by rohilchodankar on 4/22/18.
 */
@ActivityScope
class ForecastAdapter @Inject constructor() : RecyclerView.Adapter<ForecastViewHolder>(){

  var forecastdayList : MutableList<Forecastday> = ArrayList()


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
    val binding = DataBindingUtil.inflate<LayoutItemForecastBinding>(
        LayoutInflater.from(parent?.context),
        R.layout.layout_item_forecast, parent, false)

    return ForecastViewHolder(binding)
  }

  override fun getItemCount(): Int = forecastdayList.size

  override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
    holder.bindData(forecastdayList.get(position))
  }
}