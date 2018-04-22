package com.kuliza.weatherapp.ui.home

import android.os.Bundle
import com.kuliza.weatherapp.R
import com.kuliza.weatherapp.databinding.ActivityHomeBinding
import com.kuliza.weatherapp.ui.base.BaseActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import javax.inject.Inject

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

  @Inject lateinit var rxPermission: RxPermissions


  override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

  override fun layoutId(): Int {
    return R.layout.activity_home
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.loadData()
  }

}
