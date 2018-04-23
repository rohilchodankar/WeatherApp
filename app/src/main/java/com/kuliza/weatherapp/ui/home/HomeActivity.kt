package com.kuliza.weatherapp.ui.home

import android.animation.ValueAnimator
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.View
import com.kuliza.weatherapp.R
import com.kuliza.weatherapp.databinding.ActivityHomeBinding
import com.kuliza.weatherapp.ui.base.BaseActivity
import com.kuliza.weatherapp.utils.addDegrees
import javax.inject.Inject








class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {


  @Inject
  lateinit var forecastAdapter: ForecastAdapter



  val displayMetrics = DisplayMetrics()


  override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

  override fun layoutId(): Int {
    return R.layout.activity_home
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    viewModel.loadData()
    viewModel.weatherReport.observe(this, Observer { weatherReport ->
      if (weatherReport != null) {
         binding.mainContent.tvDegreeCelsius .text =  weatherReport.current.temp_c.addDegrees()
        forecastAdapter.forecastdayList = weatherReport.forecast.forecastday.toMutableList()
        forecastAdapter.notifyDataSetChanged()
        startBottomAnimation()
      }
    })

    viewModel.viewType.observe(this, Observer {
        it?.let {
          binding.viewType = it
        }
    })

    viewModel.city.observe(this, Observer {
      it?.let {
        binding.city = it
      }
    })

    binding.rcvForecast.let {
      it.layoutManager = LinearLayoutManager(this)
      it.addItemDecoration(DividerItemDecoration(this, it.layoutManager.layoutDirection))
      it.adapter = forecastAdapter

    }
  }

  private fun startBottomAnimation(){
    val anim = ValueAnimator.ofInt(binding.flvBottomContainer.measuredHeight,  displayMetrics.heightPixels/2)
    anim.addUpdateListener { valueAnimator ->
      val value = valueAnimator.animatedValue as Int
      val layoutParams = binding.flvBottomContainer.getLayoutParams()
      layoutParams.height = value
      binding.flvBottomContainer.setLayoutParams(layoutParams)
      binding.flvBottomContainer.visibility = View.VISIBLE
    }
    anim.duration = 500
    anim.start()
  }



}
